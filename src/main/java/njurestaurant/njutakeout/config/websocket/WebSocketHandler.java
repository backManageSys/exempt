package njurestaurant.njutakeout.config.websocket;

import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import com.amazonaws.util.json.JSONObject;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.app.DeviceUpdateResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Service
public class WebSocketHandler extends TextWebSocketHandler {
    @Value("${CROWD_CAST_USER_TOKEN_KEY}")
    private String CROWD_CAST_USER_TOKEN_KEY;

    private final DeviceDataService deviceDataService;
    private final AlipayDataService alipayDataService;
    private final AlipayOrderDataService alipayOrderDataService;
    private final PlatformOrderDataService platformOrderDataService;
    private final MerchantDataService merchantDataService;
    private final UserDataService userDataService;
    private final AgentDataService agentDataService;
    private final PersonalCardDataService personalCardDataService;
    private final ChangeOrderDataService changeOrderDataService;
    private final SupplierDataService supplierDataService;
    private final PayRateListDataService payRateListDataService;

    @Autowired
    public WebSocketHandler(DeviceDataService deviceDataService, AlipayDataService alipayDataService,
                            AlipayOrderDataService alipayOrderDataService, PlatformOrderDataService platformOrderDataService,
                            MerchantDataService merchantDataService, UserDataService userDataService, AgentDataService agentDataService, PersonalCardDataService personalCardDataService, ChangeOrderDataService changeOrderDataService, SupplierDataService supplierDataService, PayRateListDataService payRateListDataService) {
        this.deviceDataService = deviceDataService;
        this.alipayDataService = alipayDataService;
        this.alipayOrderDataService = alipayOrderDataService;
        this.platformOrderDataService = platformOrderDataService;
        this.merchantDataService = merchantDataService;
        this.userDataService = userDataService;
        this.agentDataService = agentDataService;
        this.personalCardDataService = personalCardDataService;
        this.changeOrderDataService = changeOrderDataService;
        this.supplierDataService = supplierDataService;
        this.payRateListDataService = payRateListDataService;
    }

    public static Map<String, Thread> mapThread = new HashMap<>();

    public static Map<String, TextMessage> msgMap = new HashMap<>();

    /**
     * 用Map存储已建立连接的用户
     */
    public static final Map<String, WebSocketSession> socketSessionMap = new HashMap<>();

    /**
     * 处理前端发送的文本信息 js调用websocket.send时候，会调用该方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //session.sendMessage(new TextMessage(message.getPayload()));
        String string = message.getPayload();
        JSONObject jsonObject = new JSONObject(string);
        String cmd = jsonObject.getString("cmd");
        String type = jsonObject.getString("type");
        String imei = jsonObject.getString("imei");
        //回复客户端的心跳检测
        if (cmd.equals("HeartBeat") && type.equals("HeartBeat")) {
            session.sendMessage(new TextMessage(jsonObject.toString()));
        }
        // 收到设备更新信息
        // 客户端消息:{"cmd":"validation","type":"alipay","userid":"支付宝userid","loginid":"支付宝loginid","imei":"设备唯一标识","name":"支付宝账号昵称/姓名(暂时未定)"}
        if (cmd.equals("validation") && type.equals("alipay")) {
            String userid = jsonObject.getString("userid");
            String loginid = jsonObject.getString("loginid");
            String name = jsonObject.getString("name");
            Device device = deviceDataService.findByImei(imei);
            if (device != null) {
                Alipay alipay = alipayDataService.findById(device.getAlipayId());
                if (alipay == null) { // 没有支付宝信息
                    Alipay alipay1 = alipayDataService.findByUserId(userid);
                    if (alipay1 != null)
                        device.setAlipayId(alipay1.getId());
                    else {
                        alipay1 = new Alipay(loginid, userid, null, null, null, imei, name, 0.0);
                        alipay1.setCardBalance(0);
                        Alipay a = alipayDataService.saveAlipay(alipay1);
                        device.setAlipayId(a.getId());
                    }
                } else if (!alipay.getUserId().equals(userid)) { // 支付宝信息对不上
                    Alipay alipay2 = alipayDataService.findByUserId(userid);
                    if (alipay2 != null)
                        device.setAlipayId(alipay2.getId());
                    else {
                        alipay2 = new Alipay(loginid, userid, null, null, null, imei, name, 0.0);
                        alipay2.setCardBalance(0);
                        Alipay a = alipayDataService.saveAlipay(alipay2);
                        device.setAlipayId(a.getId());
                    }
                }
                device.setOnline(1); // 支付宝已登录
                deviceDataService.saveDevice(device);
                // 入库成功
                session.sendMessage(new TextMessage(new DeviceUpdateResponse("", imei).toString()));
            }
        }
        //获取余额接口
        //服务端消息:{"cmd":"getwealth","type":"alipay","imei":"设备唯一标识","userid":"支付宝userid"}
        //客户端消息:{"cmd":"wealth","type":"alipay","imei":"设备唯一标识","wealth":"余额","userid":"支付宝userid"}
        if (cmd.equals("wealth") && type.equals("alipay")) {
            String userId = (String) jsonObject.get("userid");
            Alipay alipay = alipayDataService.findByUserId(userId);
            String wealth = (String) jsonObject.get("wealth");
            if (!StringUtils.isBlank(wealth)) {
                alipay.setWealth(Double.parseDouble(wealth));
            } else {
                alipay.setWealth(alipay.getWealth());
            }
            alipayDataService.saveAlipay(alipay);
        }
        // 收到订单信息
        // 客户端消息(订单信息):{"cmd":"order","type":"alipay","imei":"设备唯一标识","orderId":"订单号","money":"订单金额","memo":"备注","time":"订单时间"}
        if (cmd.equals("order") && type.equals("alipay")) {
            String orderId = jsonObject.getString("orderId");
            Double money = Double.parseDouble(jsonObject.getString("money"));
            String memo = jsonObject.getString("memo");
            String time = jsonObject.getString("time");
            Device device = deviceDataService.findByImei(imei);
            Supplier supplier = device.getSupplier();
            if ((platformOrderDataService.findByImeiAndPayTypeId(imei, 2) != null && platformOrderDataService.findByImeiAndPayTypeId(imei, 2).size() > 0)
                    || (platformOrderDataService.findByImeiAndPayTypeId(imei, 1) != null && platformOrderDataService.findByImeiAndPayTypeId(imei, 1).size() > 0)) { // 供码用户提供收款码
                // 提取imei，根据imei查询未付款的订单号，根据订单号把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
                PlatformOrder platformOrders = platformOrderDataService.findByImeiAndStateAndPayTypeIdAndMoney(imei, OrderState.WAITING_FOR_PAYING, 2, Double.parseDouble(jsonObject.getString("money")));
                PlatformOrder platformOrders1 = platformOrderDataService.findByImeiAndStateAndPayTypeIdAndMoney(imei, OrderState.WAITING_FOR_PAYING, 1, Double.parseDouble(jsonObject.getString("money")));
                if (platformOrders != null || platformOrders1 != null) {
                    if (platformOrders1 != null)
                        platformOrders = platformOrders1;
                    if (new BigDecimal(platformOrders.getMoney()).compareTo(new BigDecimal(money)) == 0) {
                        platformOrders.setState(OrderState.PAID);
                        platformOrders.setPayMoney(money);
                        Date payTime = FormatDateTime.ThirdTimestampToDate(Long.parseLong(time));
                        platformOrders.setPayTime(payTime);
                        platformOrderDataService.savePlatformOrder(platformOrders);
                        AlipayOrder alipayOrder = new AlipayOrder(imei, orderId, money, memo, payTime);
                        alipayOrderDataService.saveAlipayOrder(alipayOrder);
                        User user = userDataService.getUserById(platformOrders.getUid());
                        if (user != null) {
                            Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                            User suser = userDataService.getUserById(merchant.getApplyId());
                            if (merchant != null) {
                                merchant.setBalance(GetThreeBitsPoint(merchant.getBalance() + money * (1 - platformOrders.getMerchantRate() / 100)));
                                merchantDataService.saveMerchant(merchant);
                            }
                            // 操作上级,商户的代理商
                            if (suser != null) {
                                if (suser.getRole() == 2) {
                                    Agent agent = agentDataService.findAgentById(suser.getTableId());
                                    agent.setBalance(GetThreeBitsPoint(agent.getBalance() + money * (platformOrders.getMerchantRate() - platformOrders.getAgentRate()) / 100));
                                    agentDataService.saveAgent(agent);
                                    if (AgentDailyFlow.date == null)
                                        AgentDailyFlow.date = new Date();
                                    if (!DateUtils.isSameDay(AgentDailyFlow.date, new Date())) {
                                        AgentDailyFlow.commission.clear();
                                        AgentDailyFlow.flow.clear();
                                        AgentDailyFlow.date = new Date();
                                    }
                                    // 计算代理的每日流量
                                    if (AgentDailyFlow.flow.containsKey(agent.getId())) {
                                        AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.flow.get(agent.getId()) + money * (1 - platformOrders.getMerchantRate() / 100)));
                                    } else {
                                        AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(money * (1 - platformOrders.getMerchantRate() / 100)));
                                    }
                                    // 计算代理的每日佣金
                                    if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                        AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.commission.get(agent.getId()) + money * (platformOrders.getMerchantRate() - platformOrders.getAgentRate()) / 100));
                                    } else {
                                        AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(money * (platformOrders.getMerchantRate() - platformOrders.getAgentRate()) / 100));
                                    }
                                }
                            }
                        }
                    }

                }
            } else if ((platformOrderDataService.findByImeiAndPayTypeId(imei, 4) != null && platformOrderDataService.findByImeiAndPayTypeId(imei, 4).size() > 0)
                    || (platformOrderDataService.findByImeiAndPayTypeId(imei, 3) != null && platformOrderDataService.findByImeiAndPayTypeId(imei, 3).size() > 0)) { // 供码用户提供转账码
                // 提取memo备注里的值（99.9%是订单号）。查询订单表中是否存在一个与memo的值匹配的订单号，如果存在，则把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
                PlatformOrder platformOrder = platformOrderDataService.findByNumber(memo);
                if (platformOrder != null) {
                    if (new BigDecimal(platformOrder.getMoney()).compareTo(new BigDecimal(money)) == 0) {
                        platformOrder.setState(OrderState.PAID);
                        platformOrder.setPayMoney(money);
                        Date payTime = FormatDateTime.ThirdTimestampToDate(Long.parseLong(time));
                        platformOrder.setPayTime(payTime);
                        platformOrderDataService.savePlatformOrder(platformOrder);
                        AlipayOrder alipayOrder = new AlipayOrder(imei, orderId, money, memo, payTime);
                        alipayOrderDataService.saveAlipayOrder(alipayOrder);
                        User user = userDataService.getUserById(platformOrder.getUid());
                        if (user != null) {
                            Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                            User suser = userDataService.getUserById(merchant.getApplyId());
                            if (merchant != null) {
                                merchant.setBalance(GetThreeBitsPoint(merchant.getBalance() + money * (1 - platformOrder.getMerchantRate() / 100)));
                                merchantDataService.saveMerchant(merchant);
                            }
                            // 操作上级,商户的代理商
                            if (suser != null) {
                                if (suser.getRole() == 2) {
                                    Agent agent = agentDataService.findAgentById(suser.getTableId());
                                    agent.setBalance(GetThreeBitsPoint(agent.getBalance() + money * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                                    agentDataService.saveAgent(agent);
                                    if (AgentDailyFlow.date == null)
                                        AgentDailyFlow.date = new Date();
                                    if (!DateUtils.isSameDay(AgentDailyFlow.date, new Date())) {
                                        AgentDailyFlow.commission.clear();
                                        AgentDailyFlow.flow.clear();
                                        AgentDailyFlow.date = new Date();
                                    }
                                    // 计算代理的每日流量
                                    if (AgentDailyFlow.flow.containsKey(agent.getId())) {
                                        AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.flow.get(agent.getId()) + money * (1 - platformOrder.getMerchantRate() / 100)));
                                    } else {
                                        AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(money * (1 - platformOrder.getMerchantRate() / 100)));
                                    }
                                    // 计算代理的每日佣金
                                    if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                        AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.commission.get(agent.getId()) + money * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                                    } else {
                                        AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(money * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
            }

        }
        //收到红包订单消息
        //{"cmd":"rednews","imei":"304517300097652","type":"alipay","id":"2088012315627731","orderId":"201902210206302200000000730038022629",
        // "money":"0.01","memo":"恭喜发财，万事如意！","time":"1550723566007"}
        if (cmd.equals("rednews") && type.equals("alipay")) {
            String orderId = jsonObject.getString("orderId");
            Double money = Double.parseDouble(jsonObject.getString("money"));
            String memo = jsonObject.getString("memo");
            String time = jsonObject.getString("time");

            Device device = deviceDataService.findByImei(imei);
            // 提取memo备注里的值（99.9%是订单号）。查询订单表中是否存在一个与memo的值匹配的订单号，如果存在，则把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
            PlatformOrder platformOrder = platformOrderDataService.findByNumber(memo);
            if (platformOrder != null) {
                if (new BigDecimal(platformOrder.getMoney()).compareTo(new BigDecimal(money)) == 0) {
                    platformOrder.setState(OrderState.PAID);
                    platformOrder.setPayMoney(money);
                    Date payTime = FormatDateTime.ThirdTimestampToDate(Long.parseLong(time));
                    platformOrder.setPayTime(payTime);
                    platformOrderDataService.savePlatformOrder(platformOrder);
                    AlipayOrder alipayOrder = new AlipayOrder(imei, orderId, money, memo, payTime);
                    alipayOrderDataService.saveAlipayOrder(alipayOrder);
                    User user = userDataService.getUserById(platformOrder.getUid());
                    if (user != null) {
                        Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                        User suser = userDataService.getUserById(merchant.getApplyId());
                        if (merchant != null) {
                            merchant.setBalance(GetThreeBitsPoint(merchant.getBalance() + money * (1 - platformOrder.getMerchantRate() / 100)));
                            merchantDataService.saveMerchant(merchant);
                        }
                        // 操作上级,商户的代理商
                        if (suser != null) {
                            if (suser.getRole() == 2) {
                                Agent agent = agentDataService.findAgentById(suser.getTableId());
                                agent.setBalance(GetThreeBitsPoint(agent.getBalance() + money * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                                agentDataService.saveAgent(agent);
                                if (AgentDailyFlow.date == null)
                                    AgentDailyFlow.date = new Date();
                                if (!DateUtils.isSameDay(AgentDailyFlow.date, new Date())) {
                                    AgentDailyFlow.commission.clear();
                                    AgentDailyFlow.flow.clear();
                                    AgentDailyFlow.date = new Date();
                                }
                                // 计算代理的每日流量
                                if (AgentDailyFlow.flow.containsKey(agent.getId())) {
                                    AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.flow.get(agent.getId()) + money * (1 - platformOrder.getMerchantRate() / 100)));
                                } else {
                                    AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(money * (1 - platformOrder.getMerchantRate() / 100)));
                                }
                                // 计算代理的每日佣金
                                if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                    AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.commission.get(agent.getId()) + money * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                                } else {
                                    AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(money * (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));
                                }
                            }
                        }
                    }
                }
            }

        }

        // 收到通码链接
        if (cmd.equals("passcode") && type.equals("alipay")) {
            msgMap.put(imei, message);
            if (mapThread.containsKey(imei)) {
                Thread thread = mapThread.get(imei);
                thread.interrupt();
            }
        }

        // 收到固码链接
        if (cmd.equals("solidcode") && type.equals("alipay")) {
        }

        //客户端提交提现状态
        //客户端消息:{"cmd":"tx","type":"alipay","imei":"设备唯一标识","status":"提现状态","userid":"支付宝userid","money":"提现金额","txdao":"提现到银行卡信息"}
        if (cmd.equals("tx") && type.equals("alipay")) {
            Alipay alipay = alipayDataService.findByUserId((String) jsonObject.get("userid"));
            if (alipay != null) {
                QRcodeChangeOrder qRcodeChangeOrder = null;
                if (jsonObject.getString("status").equals("提现到账成功")) {
                    qRcodeChangeOrder = changeOrderDataService.findByLoginId(alipay.getLoginId());
                    if (qRcodeChangeOrder != null) {
                        qRcodeChangeOrder.setRealMoney(Double.parseDouble(jsonObject.getString("money")));
                        qRcodeChangeOrder.setCardBalance(qRcodeChangeOrder.getCardBalance() + qRcodeChangeOrder.getRealMoney());
                        qRcodeChangeOrder.setState(jsonObject.getString("status"));
                        changeOrderDataService.saveQRcodeChangeOrder(qRcodeChangeOrder);
                    }
                } else if (jsonObject.getString("status").equals("提现申请提交")) {
                    double pre_balance = alipay.getWealth();
//				if (alipay.getWealth() - Double.parseDouble((String) jsonObject.get("money")) < 0)
//					throw new WrongInputException();
                    alipay.setWealth(alipay.getWealth() - Double.parseDouble((String) jsonObject.get("money"))); //先把钱给它扣掉，如果后面审批不成功，再给他加回来。
                    alipayDataService.saveAlipay(alipay);
                    String cardNumber = (String) jsonObject.get("txdao");
                    // PersonalCard personalCard = personalCardDataService.findPersonalCardByCardNumber(cardNumber);
//            if (personalCard == null)
//                throw new PersonalCardDoesNotExistException();
                    changeOrderDataService.saveQRcodeChangeOrder(new QRcodeChangeOrder(
                            alipay.getLoginId(), Double.parseDouble((String) jsonObject.get("money")), 0, pre_balance, cardNumber,
                            0, jsonObject.getString("status"), new Date(), deviceDataService.findByAlipayId(alipay.getId()).getSupplier().getUser().getUsername(), false));
                    //到卡金额会在银行发短信后监控到更新，先写成0
                    //安卓会发支付宝余额，在websocket
                }
            }
        }

    }

    /**
     * 当新连接建立的时候，被调用连接成功时候，会触发页面上onOpen方法
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // socketSessionMap.put(session.getAttributes().get("imei").toString(),
        // session);
        String imei = (String) session.getAttributes().get("imei");
        Device device = deviceDataService.findByImei(imei);
        if (device != null) {
            device.setOnline(1);
            deviceDataService.saveDevice(device);
        }
        socketSessionMap.put(imei, session);
        // session.sendMessage(new TextMessage("@"+Settings.SUCCESS_CODE + ""));
    }

    /**
     * 当连接关闭时被调用
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String imei = (String) session.getAttributes().get("imei");
        // for (Map.Entry<String, WebSocketSession> m : socketSessionMap.entrySet()) {
        // if(m.getValue().toString().equals(session.toString())) {
        // imei = m.getKey();
        // break;
        // }
        // }
        socketSessionMap.remove(imei, session);
        Device device = deviceDataService.findByImei(imei);
        if (device != null) {
            device.setOnline(0);
            deviceDataService.saveDevice(device);
        }

    }

    /**
     * 传输错误时调用
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String imei = (String) session.getAttributes().get("imei");
        if (session.isOpen()) {
            session.close();
            socketSessionMap.remove(imei, session);
        }

    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public static void sendMessageToUsers(TextMessage message) {

        for (WebSocketSession socketSession : socketSessionMap.values()) {
            try {
                if (socketSession.isOpen()) {
                    socketSession.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 给某个用户发送消息
     *
     * @param token
     * @param message
     */
    public static Boolean sendMessageToUser(String token, TextMessage message) {

        Set<String> keySet = socketSessionMap.keySet();
        WebSocketSession socketSession = socketSessionMap.get(token);
        try {
            if (socketSession != null)
                if (socketSession.isOpen()) {
                    socketSession.sendMessage(message);
                    return true;
                } else {
                    socketSession.close();
                    return false;
                }
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double GetThreeBitsPoint(double money) {
        String a = new java.text.DecimalFormat("#.000").format(money);
        return Double.parseDouble(a);
    }
}
