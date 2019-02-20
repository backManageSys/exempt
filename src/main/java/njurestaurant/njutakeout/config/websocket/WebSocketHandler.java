package njurestaurant.njutakeout.config.websocket;

import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.entity.order.QRcodeChangeOrder;
import njurestaurant.njutakeout.exception.AlipayNotExistException;
import njurestaurant.njutakeout.exception.PersonalCardDoesNotExistException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
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
import njurestaurant.njutakeout.publicdatas.app.CodeType;
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
import java.util.*;

import static njurestaurant.njutakeout.bl.order.PlatformOrderBlServiceImpl.getRate;

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

    @Autowired
    public WebSocketHandler(DeviceDataService deviceDataService, AlipayDataService alipayDataService,
                            AlipayOrderDataService alipayOrderDataService, PlatformOrderDataService platformOrderDataService,
                            MerchantDataService merchantDataService, UserDataService userDataService, AgentDataService agentDataService, PersonalCardDataService personalCardDataService, ChangeOrderDataService changeOrderDataService, SupplierDataService supplierDataService) {
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
//		System.out.println("123");
        //session.sendMessage(new TextMessage(message.getPayload()));
        String string = message.getPayload();
        System.out.println(string);
        JSONObject jsonObject = new JSONObject(string);
        String cmd = jsonObject.getString("cmd");
        String type = jsonObject.getString("type");
        String imei = jsonObject.getString("imei");
        //回复客户端的心跳检测
        if (cmd.equals("HeartBeat") && type.equals("HeartBeat")){
            session.sendMessage(new TextMessage(jsonObject.toString()));
            System.out.println(new TextMessage(jsonObject.toString()));
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
                        Alipay a = alipayDataService.saveAlipay(alipay1);
                        device.setAlipayId(a.getId());
                    }
                } else if (!alipay.getUserId().equals(userid)) { // 支付宝信息对不上
                    Alipay alipay2 = alipayDataService.findByUserId(userid);
                    if (alipay2 != null)
                        device.setAlipayId(alipay2.getId());
                    else {
                        alipay2 = new Alipay(loginid, userid, null, null, null, imei, name, 0.0);
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
            if (alipay == null)
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkk");
            else
                System.out.println("qqqqqqqqqqqqqqqqqqqqqqqq");
            String wealth = (String) jsonObject.get("wealth");
            System.out.println(wealth);
            if (!StringUtils.isBlank(wealth)) {
                System.out.println(Double.parseDouble(wealth));
                alipay.setWealth(Double.parseDouble(wealth));
                System.out.println("33333333333");
            } else {
                alipay.setWealth(alipay.getWealth());
                System.out.println("222222222222222");
            }
            alipayDataService.saveAlipay(alipay);
        }
        // 收到订单信息
        // 客户端消息(订单信息):{"cmd":"order","type":"alipay","imei":"设备唯一标识","orderId":"订单号","money":"订单金额","memo":"备注","time":"订单时间"}
        if (cmd.equals("order") && type.equals("alipay")) {
            System.out.println("ooo:" + jsonObject);
            String orderId = jsonObject.getString("orderId");
            Double money = Double.parseDouble(jsonObject.getString("money"));
            String memo = jsonObject.getString("memo");
            String time = jsonObject.getString("time");

            Device device = deviceDataService.findByImei(imei);
            Supplier supplier = device.getSupplier();
            System.out.println(jsonObject.toString());
            if ((platformOrderDataService.findByImeiAndCodeType(imei, CodeType.RPASSQR) != null && platformOrderDataService.findByImeiAndCodeType(imei, CodeType.RPASSQR).size() > 0)
                    || (platformOrderDataService.findByImeiAndCodeType(imei, CodeType.RPASSOFF) != null && platformOrderDataService.findByImeiAndCodeType(imei, CodeType.RPASSOFF).size() > 0)) { // 供码用户提供收款码
                System.out.println("1:" + supplier.getCodeType() + supplier.getId() + " " + imei);
                // 提取imei，根据imei查询未付款的订单号，根据订单号把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
                PlatformOrder platformOrders = platformOrderDataService.findByImeiAndStateAndCodeTypeAndMoney(imei, OrderState.WAITING_FOR_PAYING, CodeType.RPASSQR, Double.parseDouble(jsonObject.getString("money")));
                PlatformOrder platformOrders1 = platformOrderDataService.findByImeiAndStateAndCodeTypeAndMoney(imei, OrderState.WAITING_FOR_PAYING, CodeType.RPASSOFF, Double.parseDouble(jsonObject.getString("money")));
                if (platformOrders != null || platformOrders1 != null) {
                    if (platformOrders1 != null)
                        platformOrders = platformOrders1;
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
                            merchant.setBalance(merchant.getBalance() + money * (1 - getRate(platformOrders.getCodetype(),merchant)  / 100));
                            merchantDataService.saveMerchant(merchant);
                        }
                        // 操作上级,商户的代理商
                        if (suser != null) {
                            if (suser.getRole() == 2) {
                                Agent agent = agentDataService.findAgentById(suser.getTableId());
                                agent.setBalance(agent.getBalance() + money * agent.getAlipay() / 100);
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
                                    AgentDailyFlow.flow.put(agent.getId(), AgentDailyFlow.flow.get(agent.getId()) + money * (1 - getRate(platformOrders.getCodetype(),merchant)  / 100));
                                } else {
                                    AgentDailyFlow.flow.put(agent.getId(), money * (1 - getRate(platformOrders.getCodetype(),merchant)  / 100));
                                }
                                // 计算代理的每日佣金
                                if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                    AgentDailyFlow.commission.put(agent.getId(), AgentDailyFlow.commission.get(agent.getId()) + money * agent.getAlipay() / 100);
                                } else {
                                    AgentDailyFlow.commission.put(agent.getId(), money * agent.getAlipay() / 100);
                                }
                            }
                        }
                    }

                }
            } else if ((platformOrderDataService.findByImeiAndCodeType(imei, CodeType.TPASS) != null && platformOrderDataService.findByImeiAndCodeType(imei, CodeType.TPASS).size() > 0)
                    || (platformOrderDataService.findByImeiAndCodeType(imei, CodeType.TSOLID) != null && platformOrderDataService.findByImeiAndCodeType(imei, CodeType.TSOLID).size() > 0)) { // 供码用户提供转账码
                // 提取memo备注里的值（99.9%是订单号）。查询订单表中是否存在一个与memo的值匹配的订单号，如果存在，则把订单状态更新成已成功付款，保留订单金额，新插入实收金额。
                System.out.println("2:" + supplier.getCodeType());
                System.out.println(memo);
                PlatformOrder platformOrder = platformOrderDataService.findByNumber(memo);
                if (platformOrder != null) {
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
                            merchant.setBalance(merchant.getBalance() + money * (1 - getRate(platformOrder.getCodetype(),merchant) / 100));
                            merchantDataService.saveMerchant(merchant);
                        }
                        // 操作上级,商户的代理商
                        if (suser != null) {
                            if (suser.getRole() == 2) {
                                Agent agent = agentDataService.findAgentById(suser.getTableId());
                                agent.setBalance(agent.getBalance() + money * agent.getAlipay() / 100);
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
                                    AgentDailyFlow.flow.put(agent.getId(), AgentDailyFlow.flow.get(agent.getId()) + money * (1 - getRate(platformOrder.getCodetype(),merchant)  / 100));
                                } else {
                                    AgentDailyFlow.flow.put(agent.getId(), money * (1 - getRate(platformOrder.getCodetype(),merchant)  / 100));
                                }
                                // 计算代理的每日佣金
                                if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                    AgentDailyFlow.commission.put(agent.getId(), AgentDailyFlow.commission.get(agent.getId()) + money * agent.getAlipay() / 100);
                                } else {
                                    AgentDailyFlow.commission.put(agent.getId(), money * agent.getAlipay() / 100);
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("77:");
            }

            // String imei = jsonObject.getString("imei");
            // String orderId = jsonObject.getString("orderId");
            // String money = jsonObject.getString("money");
            // String memo = jsonObject.getString("memo");
            // String time = jsonObject.getString("time");
            // System.out.println("收到用户 " + imei + "的消息：" +
            // message.getPayload().toString());

            // 如果订单成功入库，则回复一条信息
            // if(true){
            // JSONObject jsonObject2 = new JSONObject();
            // jsonObject2.put("cmd", cmd);
            // jsonObject2.put("status", "success");
            // jsonObject2.put("orderId", orderId);
            // jsonObject2.put("imei", imei);
            // session.sendMessage(new TextMessage(jsonObject2.toString()));
            // }
            // //如果订单入库失败，则回复一条信息
            // else {
            // JSONObject jsonObject3 = new JSONObject();
            // jsonObject3.put("cmd", cmd);
            // jsonObject3.put("status", "failed");
            // jsonObject3.put("err", "错误信息");
            // jsonObject3.put("orderId", orderId);
            // jsonObject3.put("imei", imei);
            // session.sendMessage(new TextMessage(jsonObject3.toString()));
            // }
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
            System.out.println("**************************************************1");
            System.out.println(alipay);
            System.out.println("**************************************************1.2");
            System.out.println(alipay.getLoginId());

            if (alipay != null) {
                System.out.println("**************************************************2");
                QRcodeChangeOrder qRcodeChangeOrder = null;
                if (jsonObject.getString("status").equals("提现到账成功")) {
                    System.out.println("**************************************************3");
                    qRcodeChangeOrder = changeOrderDataService.findByLoginId(alipay.getLoginId());
                    System.out.println("**************************************************4");
                    if (qRcodeChangeOrder != null) {
                        qRcodeChangeOrder.setRealMoney(Double.parseDouble(jsonObject.getString("money")));
                        System.out.println("**************************************************5");
                        qRcodeChangeOrder.setCardBalance(qRcodeChangeOrder.getCardBalance() + qRcodeChangeOrder.getRealMoney());
                        System.out.println("**************************************************6");
                        qRcodeChangeOrder.setState(jsonObject.getString("status"));
                        System.out.println("**************************************************7");
                        changeOrderDataService.saveQRcodeChangeOrder(qRcodeChangeOrder);
                        System.out.println("**************************************************8");
                    }
                } else if (jsonObject.getString("status").equals("提现申请提交")) {
                    double pre_balance = alipay.getWealth();
//				if (alipay.getWealth() - Double.parseDouble((String) jsonObject.get("money")) < 0)
//					throw new WrongInputException();
                    alipay.setWealth(alipay.getWealth() - Double.parseDouble((String) jsonObject.get("money"))); //先把钱给它扣掉，如果后面审批不成功，再给他加回来。
                    System.out.println("**************************************************9");
                    alipayDataService.saveAlipay(alipay);
                    System.out.println("**************************************************10");
                    System.out.println(jsonObject.get("txdao") + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~11");
                    String cardNumber = (String) jsonObject.get("txdao");
                    System.out.println("**************************************************12");
                    // PersonalCard personalCard = personalCardDataService.findPersonalCardByCardNumber(cardNumber);
//            if (personalCard == null)
//                throw new PersonalCardDoesNotExistException();
                    changeOrderDataService.saveQRcodeChangeOrder(new QRcodeChangeOrder(
                            alipay.getLoginId(), Double.parseDouble((String) jsonObject.get("money")), 0, pre_balance, cardNumber,
                            0, jsonObject.getString("status"), new Date(), deviceDataService.findByAlipayId(alipay.getId()).getSupplier().getUser().getUsername()));
                    System.out.println("**************************************************13");
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
        System.out.println(imei);
        Device device = deviceDataService.findByImei(imei);
        if (device != null) {
            device.setOnline(1);
            deviceDataService.saveDevice(device);
        }
        socketSessionMap.put(imei, session);
        System.out.println("用户 " + imei + " 已建立连接");
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
        System.out.println("用户 " + imei + " 已关闭连接。 当前状态：" + status);

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
        System.out.println("用户 " + session.getAttributes().get("imei") + " 已关闭连接");

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
        for (String key : keySet) {
            System.out.println("sendMessageToUser:" + key);
        }
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
}
