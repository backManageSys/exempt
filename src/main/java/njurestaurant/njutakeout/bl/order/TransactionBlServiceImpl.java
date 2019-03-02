package njurestaurant.njutakeout.bl.order;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import njurestaurant.njutakeout.blservice.order.TransactionBlService;
import njurestaurant.njutakeout.config.websocket.WebSocketHandler;
import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.dataservice.company.PayTypeDataService;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.dataservice.order.WithdrewOrderDataService;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.company.PayType;
import njurestaurant.njutakeout.entity.order.CardChangeOrder;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.app.CheckOnlineParameters;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewDealParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewParameters;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import njurestaurant.njutakeout.response.app.GetReceiptCodeResponse;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import njurestaurant.njutakeout.util.StringParseUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static njurestaurant.njutakeout.config.websocket.WebSocketHandler.GetThreeBitsPoint;

@Service
public class TransactionBlServiceImpl implements TransactionBlService {
    private final SupplierDataService supplierDataService;
    private final PlatformOrderDataService platformOrderDataService;
    private final AlipayDataService alipayDataService;
    private final AlipayOrderDataService alipayOrderDataService;
    private final UserDataService userDataService;
    private final MerchantDataService merchantDataService;
    private final DeviceDataService deviceDataService;
    private final WithdrewOrderDataService withdrewOrderDataService;
    private final AgentDataService agentDataService;
    private final PayTypeDataService payTypeDataService;
    private final PayRateListDataService payRateListDataService;
    private final CompanyCardDataService companyCardDataService;
    private final ChangeOrderDataService changeOrderDataService;
    private static Map<String, String> map = new HashMap<>();
    private static Map<String, Integer> map1 = new HashMap<>();
    private static Map<String, String> map2 = new HashMap<>();
    private static Map<String, String> map3 = new HashMap<>();
    private static Map<String, Integer> map4 = new HashMap<>();
    private static Map<String, String> map5 = new HashMap<>();
    private static Map<String, String> map6 = new HashMap<>();
    private int ip_threshold = 5;//ip阈值，设定超过该次数的ip，将被认为是恶意ip，将被封死，暂设为5次。
    private int id_threshold = 5;//id阈值，设定超过该次数的id，将被认为是刷单id，将被锁定掉30分钟，暂设为5次。
    private double money_threshold = 0.01; //订单筛选，0.01元及其以下订单直接返回金额过小(0.01可修改)
    private long ip_time_interval_risk = 1000;//同一ip两次访问的风险时间间隔，暂设为1000ms
    private long ip_time_interval_safe = 5000;//同一ip两次访问的安全时间间隔，暂设为5000ms
    private long id_time_interval_risk = 1000;//同一id两次访问的风险时间间隔，暂设为1000ms
    private long id_time_interval_safe = 5000;//同一id两次访问的安全时间间隔，暂设为5000ms
    private final String TRANSFERSOLIDURL = "alipays://platformapi/startapp?appId=20000123&actionType=scan&biz_data={\"s\": \"money\",\"u\":\"";
    private final String TRANSFERPASSURL = "alipays://platformapi/startapp?appId=09999988&actionType=toAccount&sourceId=contactAmount&chatLoginId=&chatUserId=";
    public static double time = 3.0;

    public static double getTime() {
        return time;
    }

    public static void setTime(double time) {
        TransactionBlServiceImpl.time = time;
    }

    @Autowired
    public TransactionBlServiceImpl(SupplierDataService supplierDataService, PlatformOrderDataService platformOrderDataService, AlipayDataService alipayDataService, AlipayOrderDataService alipayOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService, DeviceDataService deviceDataService, WithdrewOrderDataService withdrewOrderDataService, AgentDataService agentDataService, PayTypeDataService payTypeDataService, PayRateListDataService payRateListDataService, CompanyCardDataService companyCardDataService, ChangeOrderDataService changeOrderDataService) {
        this.supplierDataService = supplierDataService;
        this.platformOrderDataService = platformOrderDataService;
        this.alipayDataService = alipayDataService;
        this.alipayOrderDataService = alipayOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
        this.deviceDataService = deviceDataService;
        this.withdrewOrderDataService = withdrewOrderDataService;
        this.agentDataService = agentDataService;
        this.payTypeDataService = payTypeDataService;
        this.payRateListDataService = payRateListDataService;
        this.companyCardDataService = companyCardDataService;
        this.changeOrderDataService = changeOrderDataService;
    }

    @Override
    public void addDevice() {
        Supplier supplier = supplierDataService.findSupplierById(10);
        Device device = new Device(supplier);
        deviceDataService.saveDevice(device);
    }

    /**
     * web客户端发起获取二维码请求
     * 成功，则服务器返回：
     * {url:"我们平台的网址(在二维码未失效的情况下可重定向到支付宝付款地址) 重定向的接口",status:"success",orderid:"订单号"}
     * 失败，则服务器返回：
     * {status:"failed",reason:"失败原因（目前有网络异常、未获取到供码设备）"}
     *
     * @param getQrCodeParameters web端发来的消息{IP:"ip地址",id:"充值方编号",money:"待付款金额",商户id:"merchantid",time="10位的时间戳",sign="签名算法(http://kfb.im/index/doc/sign.do)"}
     * @return
     * @throws WrongIdException 供码用户id错误/用户id对应的用户身份不为商户 抛出异常
     */
    @Override
    public GetQrCodeResponse getQrCode(GetQrCodeParameters getQrCodeParameters) throws WrongIdException, BlankInputException, IPRiskControlException, IDRiskControlException, TooLittleMoneyException, OrderNotPayedException, PayTypeStopUsingException {
        if (Double.parseDouble(getQrCodeParameters.getMoney()) <= money_threshold) {
            throw new TooLittleMoneyException();
        }
        // Integer id = StringParseUtil.StringToInt(getQrCodeParameters.getMerchantName());
        if (StringParseUtil.StringToInt(getQrCodeParameters.getTime()) == null || StringParseUtil.StringToDouble(getQrCodeParameters.getMoney()) == null) {
            throw new BlankInputException();
        }
        double money = StringParseUtil.StringToDouble(getQrCodeParameters.getMoney());
        Date date = FormatDateTime.TenTimestampToDate(StringParseUtil.StringToInt(getQrCodeParameters.getTime()));
        map2.put(getQrCodeParameters.getIp(), String.valueOf(date.getTime()));//ip,十三位时间戳 ms，记录每个ip每次访问的时间
        map5.put(getQrCodeParameters.getId(), String.valueOf(date.getTime()));//id, 记录每个id每次访问的时间

        //封死ip
        if (map1.containsKey(getQrCodeParameters.getIp()))
            if (map1.get(getQrCodeParameters.getIp()) > ip_threshold)
                throw new IPRiskControlException();

        //防刷单，此编号如果多次访问需要锁定掉30分钟
        if (map4.containsKey(getQrCodeParameters.getIp()))
            if (map4.get(getQrCodeParameters.getId()) > id_threshold) {
                if (!map6.containsKey(getQrCodeParameters.getId()))
                    map6.put(getQrCodeParameters.getId(), String.valueOf(date.getTime()));
                if ((Long.parseLong(map5.get(getQrCodeParameters.getId())) - Long.parseLong(map6.get(getQrCodeParameters.getId()))) < 1000 * 60 * 30)
                    throw new IDRiskControlException();
                else
                    map3.remove(getQrCodeParameters.getId());
            }

        //ip风控机制，检测同IP恶意访问
        if (!map.containsKey(getQrCodeParameters.getIp())) {
            map.put(getQrCodeParameters.getIp(), String.valueOf(date.getTime()));//记录每个ip首次访问时间
            map1.put(getQrCodeParameters.getIp(), 1);
        } else {
            if ((Long.parseLong(map2.get(getQrCodeParameters.getIp())) - Long.parseLong(map.get(getQrCodeParameters.getIp()))) > ip_time_interval_safe && map1.get(getQrCodeParameters.getIp()) > 0) {
                map.remove(getQrCodeParameters.getIp());//将大于安全时间的ip从map中移除，下次访问会作为新ip重新记录
                map1.put(getQrCodeParameters.getIp(), map1.get(getQrCodeParameters.getIp()) - 1);
            }
            if (map.containsKey(getQrCodeParameters.getIp()))
                if ((Long.parseLong(map2.get(getQrCodeParameters.getIp())) - Long.parseLong(map.get(getQrCodeParameters.getIp()))) < ip_time_interval_risk) {
                    map.put(getQrCodeParameters.getIp(), String.valueOf(date.getTime()));
                    map1.put(getQrCodeParameters.getIp(), map1.get(getQrCodeParameters.getIp()) + 1);//更新每个ip的首次访问时间（将后来的访问时间，作为下一次访问的首次访问时间）
                }

        }

        //防刷单机制，商户方提供充值编号
        if (!map3.containsKey(getQrCodeParameters.getId())) {
            map3.put(getQrCodeParameters.getId(), String.valueOf(date.getTime()));//记录每个id首次访问时间
            map4.put(getQrCodeParameters.getId(), 1);
        } else {
            if ((Long.parseLong(map5.get(getQrCodeParameters.getId())) - Long.parseLong(map3.get(getQrCodeParameters.getId()))) > id_time_interval_safe && map4.get(getQrCodeParameters.getId()) > 0) {
                map3.remove(getQrCodeParameters.getId());//将大于安全时间的id从map中移除，下次访问会作为新id重新记录
                map4.put(getQrCodeParameters.getId(), map4.get(getQrCodeParameters.getId()) - 1);
            }
            if (map3.containsKey(getQrCodeParameters.getId()))
                if ((Long.parseLong(map5.get(getQrCodeParameters.getId())) - Long.parseLong(map3.get(getQrCodeParameters.getId()))) < id_time_interval_risk) {
                    map3.put(getQrCodeParameters.getId(), String.valueOf(date.getTime()));
                    map4.put(getQrCodeParameters.getId(), map4.get(getQrCodeParameters.getId()) + 1);//更新每个id的首次访问时间（将后来的访问时间，作为下一次访问的首次访问时间）
                }

        }

        if (StringUtils.isBlank(getQrCodeParameters.getMerchantName())) {
            throw new BlankInputException();
        } else {
            User user = userDataService.getUserByUsername(getQrCodeParameters.getMerchantName());
            if (user == null || user.getRole() != 3) {
                throw new WrongIdException();
            } else {
                Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                // 数据库中没有该商户信息
                if (merchant == null) {
                    throw new WrongIdException();
                }
                User suser = userDataService.getUserById(merchant.getApplyId());
                PayRateList payRateList = payRateListDataService.findByUidAndPayTypeId(user.getId(), getQrCodeParameters.getPayTypeId());
                PayRateList payRateList1 = payRateListDataService.findByUidAndPayTypeId(suser.getId(), payRateList.getPayTypeId());
                PayType payType = payTypeDataService.findById(payRateList.getPayTypeId());
                switch (suser.getRole()) {
                    case 1:
                        if (payRateList.getStatus().equals("停用") || payType.getStatus().equals("停用"))
                            throw new PayTypeStopUsingException();
                        break;
                    case 2:
                        if (payRateList.getStatus().equals("停用") || payRateList1.getStatus().equals("停用") || payType.getStatus().equals("停用"))
                            throw new PayTypeStopUsingException();
                        break;
                }
                // 开始寻找供码用户
                Random random = new Random();
                List<Supplier> supplierList = supplierDataService.findSuppliersByLevel(merchant.getPriority());
                System.out.println(merchant.getPriority());
                System.out.println(supplierList);
                int len = supplierList.size();
                int randomNumber;
                Supplier chosenSupplier = null;
                Device chosenDevice = null;

                while (len > 0) {
                    // 随机挑选一个供码者
                    randomNumber = random.nextInt(len);
                    chosenSupplier = supplierList.get(randomNumber);
                    supplierList.remove(randomNumber);
                    if (getQrCodeParameters.getPayTypeId() != chosenSupplier.getPayTypeId()) {
                        len--;
                        continue;
                    }
                    // 查找该用户设备
                    List<Device> devices = chosenSupplier.getDevices();
                    int dLen = devices.size();
                    // 选择一个合适的供码设备
                    while (dLen > 0) {
                        randomNumber = random.nextInt(dLen);
                        chosenDevice = devices.get(randomNumber);
                        devices.remove(randomNumber);
                        if (chosenDevice.getOnline() == 0) {
                            dLen--;
                            continue;    // 平台账号在该设备上离线
                        }

                        // 没有离线时和手机沟通，再次验证支付宝是否在线,并且判断有没有支付宝账号
                        Alipay alipay = alipayDataService.findById(chosenDevice.getAlipayId());

                        // 该设备没有支付宝的信息
                        if (alipay == null) {
                            dLen--;
                            continue;
                        }
                        if (StringUtils.isBlank(alipay.getCardNumber())) {
                            dLen--;
                            continue;
                        }
                        GetReceiptCodeResponse getReceiptCodeResponse = checkAlipayOnline(chosenDevice.getImei(), alipay.getUserId());
                        if (getReceiptCodeResponse != null && getReceiptCodeResponse.getStatus().equals("success")) {
                            if (StringUtils.isBlank(alipay.getPassQrCode())) {  // 该支付宝账号的二维码链接为空，向安卓端发送获取二维码的请求
                                alipay.setPassQrCode(getReceiptCodeResponse.getQrcode());
                                if (StringUtils.isBlank(alipay.getPassOffCode())) {
                                    alipay.setPassOffCode(getReceiptCodeResponse.getOffcode());
                                }
                                alipayDataService.saveAlipay(alipay);
                            }
                            //if (checkOrderIsExpired())
                            if (chosenSupplier.getPayTypeId() == 1 || chosenSupplier.getPayTypeId() == 2) {//1:支付宝收款通码离线码  2：支付宝收款通码在线码  3：支付宝转账固码 4：支付宝转账通码 5：支付宝红包 6：支付宝收款固码 7：微信收款码
                                List<PlatformOrder> platformOrder = platformOrderDataService.findByImeiAndStateAndPayTypeId(getReceiptCodeResponse.getImei(), OrderState.WAITING_FOR_PAYING, 1);
                                List<PlatformOrder> platformOrder1 = platformOrderDataService.findByImeiAndStateAndPayTypeId(getReceiptCodeResponse.getImei(), OrderState.WAITING_FOR_PAYING, 2);
                                platformOrder.addAll(platformOrder1);
                                if (platformOrder.size() > 0) {
                                    for (PlatformOrder pl : platformOrder) {
                                        if (pl.getState() == OrderState.WAITING_FOR_PAYING)
                                            if (checkOrderIsExpired(pl.getTime())) {//如果过期，则置为过期
                                                pl.setState(OrderState.EXPIRED);
                                                platformOrderDataService.savePlatformOrder(pl);
                                            }
                                    }
                                    for (PlatformOrder pl : platformOrder) {
                                        if (!checkOrderIsExpired(pl.getTime()) && pl.getMoney() == Double.parseDouble(getQrCodeParameters.getMoney()))
                                            throw new OrderNotPayedException();//如果没过期，且面额相等，则抛异常
                                    }
                                }
                            }

                            break; // 支付宝在线
                        } else if (getReceiptCodeResponse != null && getReceiptCodeResponse.getStatus().equals("failed")) {
                            chosenDevice.setOnline(0);
                            deviceDataService.saveDevice(chosenDevice);
                            dLen--;
                        } else dLen--;
                    }

                    if (dLen == 0) {
                        len--;
                        continue; // 没有任何一个合适的设备

                    }

                    // 找到一个设备支付宝账号在线的供码者
                    // 检查供码者设定的供码类型
                    if (chosenSupplier.getPayTypeId() == 0) {
                        len--;
                        continue;
                    } else break;
                }

                // 没有一个供码者符合条件, 返回失败
                if (len == 0) return null;
                // 订单号生成规则： 1+10位的时间戳（从web端接收的）+两位随机数+用户的userid后四位不足补0 （支付宝通道则第一位为1）
                String orderId = "1" + getQrCodeParameters.getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", user.getId() % 10000);
                // 订单号查重
                while (platformOrderDataService.findByNumber(orderId) != null) {
                    orderId = "1" + getQrCodeParameters.getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", user.getId() % 10000);
                }

                // 检查供码者设定的供码类型, 获取收款码
                String qrCode = null;
                int payTypeId = chosenSupplier.getPayTypeId();
                switch (payTypeId) {
                    case 1: // 收款通码离线码
                        qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getPassOffCode();
                        System.out.println("off:" + qrCode);
                        break;
                    case 2:   //收款通码在线码
                        qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getPassQrCode();
                        System.out.println(qrCode);
                        break;
                    case 3:    //转账固码
                        qrCode = TRANSFERSOLIDURL + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "\",\"a\":\"" + money + "\",\"m\":\"" + orderId + "\"}";
                        break;
                    case 4: //转账通码
                        qrCode = TRANSFERPASSURL + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "&money=" + getQrCodeParameters.getMoney() + "&amount=&memo=" + orderId;
                        break;
                    case 5://红包
                        //http://192.168.123.228:9528/static/test_alipay.htm?j=
                        qrCode = "http://47.102.146.253/exempt-front/web/static/test_alipay.htm?j=" + alipayDataService.findById(chosenDevice.getAlipayId()).getLoginId() +
                                "&a=" + alipayDataService.findById(chosenDevice.getAlipayId()).getUserId() + "&h=" + getQrCodeParameters.getMoney() + "&i=" + orderId;
                        break;
                    case 6:    // 收款固码
                        qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getSolidCode();
                        break;
                    case 7:    // 微信收款码
                        //  qrCode = alipayDataService.findById(chosenDevice.getAlipayId()).getSolidCode();
                        break;
                }
                PlatformOrder platformOrder = new PlatformOrder(orderId, OrderState.WAITING_FOR_PAYING, date, qrCode,
                        getQrCodeParameters.getIp(), getQrCodeParameters.getId(), money, user.getId(),
                        chosenSupplier.getUser().getId(), chosenDevice.getImei(), payTypeId, payRateList1.getRate(), payRateList.getRate());

                platformOrder.setType(payType.getCodeCategory());
                // 需要支付宝的收款码
                if (payType.getCodeCategory().equals("支付宝"))
                    platformOrder.setTableId(chosenDevice.getAlipayId());

                platformOrderDataService.savePlatformOrder(platformOrder);

                return new GetQrCodeResponse("/redirect", "success", orderId);
            }
        }
    }

    @Override
    public String findQrCodeByOrderId(String orderid) throws WrongIdException {
        PlatformOrder platformOrder = platformOrderDataService.findByNumber(orderid);
        if (platformOrder == null) { // 订单号错误
            throw new WrongIdException();
        } else if (platformOrder.getState() == OrderState.EXPIRED || checkOrderIsExpired(platformOrder.getTime())) {  //已失效
            platformOrder.setState(OrderState.EXPIRED);//当扫码时，才会将状态置为已失效
            platformOrderDataService.savePlatformOrder(platformOrder);
            return "expired";
        } else if (platformOrder.getState() == OrderState.PAID) {
            return "payid";
        } else {
            return platformOrder.getPayCode();
        }
    }

    /**
     * 检查订单是否已失效
     *
     * @return 失效返回true，没失效返回false
     */
    private boolean checkOrderIsExpired(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        double minutes = (double) diff / (1000 * 60);
        // 预设失效时间为2分钟
        if (minutes > time) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查该设备上的支付宝账号是否在线
     * 服务端发送消息:{"cmd":"passcode","type":"alipay","imei":"设备唯一标识","userid":"支付宝userid"}
     *
     * @param imei   设备的唯一标识
     * @param userId 支付宝的账号
     * @return
     */
    private GetReceiptCodeResponse checkAlipayOnline(String imei, String userId) throws OrderNotPayedException {
        Boolean beensent = WebSocketHandler.sendMessageToUser(imei, new TextMessage(String.valueOf(new CheckOnlineParameters(imei, userId))));
        System.out.println("1111111111111111111");
        if (!beensent)
            return null;
        System.out.println("22222222222222222222222");
        Thread thread = Thread.currentThread();
        WebSocketHandler.mapThread.put(imei, thread);
        try {
            thread.sleep(8000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        }
        System.out.println("333333333333333333333333");
        WebSocketHandler.mapThread.remove(imei);
        TextMessage textMessage = WebSocketHandler.msgMap.get(imei);
        System.out.println(textMessage);
        System.out.println("4444444444444444444444");
        if (textMessage == null || StringUtils.isBlank(textMessage.getPayload())) {
            try {
                WebSocketHandler.msgMap.remove(imei);
                WebSocketHandler.socketSessionMap.get(imei).close();
                WebSocketHandler.socketSessionMap.remove(imei);
                System.out.println("55555555555555555555555555555");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb");
            }
            return null;
        }
        System.out.println("666666666666666666666666");
        //{"cmd":"passcode","imei":"304517300097652","type":"alipay","status":"success","userid":"2088022126490523","qrcode":"","offqrcode":""}
        try {
            JSONObject jsonObject = new JSONObject(textMessage.getPayload());
            WebSocketHandler.msgMap.remove(imei);
            System.out.println("7777777777777777777777777");
            String cmd = jsonObject.getString("cmd");
            String type = jsonObject.getString("type");
            String im = jsonObject.getString("imei");
//                String msg = jsonObject.getString("msg");
            String userid = jsonObject.getString("userid");
            String qrCode = jsonObject.getString("qrcode");
            String offQrCode = jsonObject.getString("offqrcode");
            String status = jsonObject.getString("status");
            System.out.println("8888888888888888888888");
            return new GetReceiptCodeResponse(cmd, type, im, status, "msg", userid, qrCode, offQrCode);
        } catch (JSONException e) {
            System.out.println("9999999999999999999999999");
            return null;
        }
    }

    @Override
    public PlatformOrder findPlatformOrderByImeiAndState(String imei, OrderState orderState) {
        return platformOrderDataService.findByImeiAndState(imei, orderState);
    }

    @Override
    public WithdrewOrder addWithdrewOrder(WithdrewParameters withdrewParameters) throws WrongIdException, BlankInputException, WrongInputException {
        User user = userDataService.getUserById(withdrewParameters.getId());
        if (user == null) throw new WrongIdException();
        double balance = 0.0;

        if ("merchant".equals(withdrewParameters.getType())) {   // 商家提出提款操作
            if (user.getRole() != 3) throw new WrongIdException();
            Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
            balance = merchant.getBalance();
            if (new BigDecimal(balance).compareTo(new BigDecimal(withdrewParameters.getMoney())) < 0)
                throw new WrongInputException();
            if (new BigDecimal(withdrewParameters.getMoney()).compareTo(new BigDecimal(100.0)) < 0 || new BigDecimal(withdrewParameters.getMoney()).compareTo(new BigDecimal(50000.0)) > 0)
                throw new BlankInputException();
            merchant.setBalance(GetThreeBitsPoint(balance - withdrewParameters.getMoney()));
            merchant.setWithdrewMoney(GetThreeBitsPoint(merchant.getWithdrewMoney() + withdrewParameters.getMoney()));
            merchantDataService.saveMerchant(merchant);
        } else if ("agent".equals(withdrewParameters.getType())) {
            if (user.getRole() != 2) throw new WrongIdException();
            Agent agent = agentDataService.findAgentById(user.getTableId());
            balance = agent.getBalance();
            if (new BigDecimal(balance).compareTo(new BigDecimal(withdrewParameters.getMoney())) < 0)
                throw new WrongInputException();
            if (new BigDecimal(withdrewParameters.getMoney()).compareTo(new BigDecimal(100.0)) < 0 || new BigDecimal(withdrewParameters.getMoney()).compareTo(new BigDecimal(50000.0)) > 0)
                throw new BlankInputException();
            agent.setBalance(GetThreeBitsPoint(balance - withdrewParameters.getMoney()));
            agent.setWithdrewMoney(GetThreeBitsPoint(agent.getWithdrewMoney() + withdrewParameters.getMoney()));
            agentDataService.saveAgent(agent);
        } else throw new WrongIdException();


        Random random = new Random();
        String orderNumber = "W" + new Date().getTime() + String.format("%02d", random.nextInt(100)) + String.format("%04d", withdrewParameters.getId() % 10000);
        return withdrewOrderDataService.saveWithdrewOrder(new WithdrewOrder(orderNumber, withdrewParameters.getId(), withdrewParameters.getType(), withdrewParameters.getCardId(), withdrewParameters.getMoney(), balance, WithdrewState.WAITING, new Date()));
    }

    @Override
    public Page<WithdrewOrder> getAllWaitingWithdrewOrder(Pageable pageable, WithdrewOrder withdrewOrder) {
        Page<WithdrewOrder> page = withdrewOrderDataService.findByState(WithdrewState.WAITING, pageable, withdrewOrder);
        List<WithdrewOrder> list = page.getContent().stream().peek(p -> {
            String first = p.getCard_in().substring(0, 4);
            String last = p.getCard_in().substring(p.getCard_in().length() - 4);
            String middle = "";
            for (int i = 0; i < p.getCard_in().substring(4, p.getCard_in().length() - 4).length(); i++)
                middle += "*";
            p.setCard_in(first + middle + last);
            System.out.println(p.getCard_in());
        }).collect(Collectors.toList());
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }

    @Override
    public void grabWithdrewOrderById(int oid, int uid) throws WrongIdException, WrongInputException {
        User user = userDataService.getUserById(uid);
        if (user == null) throw new WrongIdException();
        WithdrewOrder withdrewOrder = withdrewOrderDataService.findWithdrewOrderById(oid);
        if (withdrewOrder == null) throw new WrongIdException();
        if (withdrewOrder.getState() != WithdrewState.WAITING) throw new WrongInputException();
        withdrewOrder.setState(WithdrewState.DEALING);
        withdrewOrder.setOperateId(uid);
        withdrewOrderDataService.saveWithdrewOrder(withdrewOrder);
    }

    @Override
    public void dealWithdrewOrder(int id, WithdrewDealParameters withdrewDealParameters) throws WrongIdException, BlankInputException {
        User user = userDataService.getUserById(withdrewDealParameters.getOperatorId());
        if (user == null) throw new WrongIdException();
        WithdrewOrder withdrewOrder = withdrewOrderDataService.findWithdrewOrderById(id);
        if (withdrewOrder == null || withdrewOrder.getState() != WithdrewState.DEALING || withdrewOrder.getOperateId() != withdrewDealParameters.getOperatorId())
            throw new WrongIdException();
        if (WithdrewState.SUCCESS == withdrewDealParameters.getState()) {
            withdrewOrder.setState(WithdrewState.SUCCESS);
            withdrewOrder.setCard_out(withdrewDealParameters.getCompanyCardId());
            withdrewOrder.setMoney_in(GetThreeBitsPoint(withdrewOrder.getMoney_out() - 3));//手续费三元
            CompanyCard companyCard = companyCardDataService.findCompanyCardByCardNumber(withdrewDealParameters.getCompanyCardId());
            companyCard.setBalance(GetThreeBitsPoint(companyCard.getBalance() - withdrewOrder.getMoney_out() + 3));
            companyCardDataService.saveCompanyCard(companyCard);
            withdrewOrder.setCard_out_balance(GetThreeBitsPoint(companyCard.getBalance() - withdrewOrder.getMoney_out() + 3));
            CardChangeOrder cardChangeOrder = new CardChangeOrder(withdrewOrder.getCard_out(), withdrewOrder.getCard_in(),
                    withdrewOrder.getMoney_out(), withdrewOrder.getMoney_out() - 3, companyCard.getBalance(), 0, WithdrewState.SUCCESS, new Date(),
                    null, user.getUsername(), "出款", null);
            cardChangeOrder = changeOrderDataService.saveCardChangeOrder(cardChangeOrder);
            withdrewOrder.setChangeId(cardChangeOrder.getId());
            if ("merchant".equals(withdrewOrder.getType())) {
                User u = userDataService.getUserById(withdrewOrder.getApplicantId());
                Merchant merchant = merchantDataService.findMerchantById(u.getTableId());
                merchant.setWithdrewMoney(GetThreeBitsPoint(merchant.getWithdrewMoney() - withdrewOrder.getMoney_out()));  // 减少商户正在提现的金额
                merchantDataService.saveMerchant(merchant);
            } else if ("agent".equals(withdrewOrder.getType())) {
                User u = userDataService.getUserById(withdrewOrder.getApplicantId());
                Agent agent = agentDataService.findAgentById(u.getTableId());
                agent.setWithdrewMoney(GetThreeBitsPoint(agent.getWithdrewMoney() - withdrewOrder.getMoney_out()));  // 减少代理正在提现的金额
                agentDataService.saveAgent(agent);
            }
        } else if (WithdrewState.FAILED == withdrewDealParameters.getState()) {
            withdrewOrder.setState(WithdrewState.FAILED);
            if ("merchant".equals(withdrewOrder.getType())) {
                User u = userDataService.getUserById(withdrewOrder.getApplicantId());
                Merchant merchant = merchantDataService.findMerchantById(u.getTableId());
                merchant.setBalance(GetThreeBitsPoint(merchant.getBalance() + withdrewOrder.getMoney_out()));  // 增加商户余额
                merchant.setWithdrewMoney(GetThreeBitsPoint(merchant.getWithdrewMoney() - withdrewOrder.getMoney_out()));  // 减少商户正在提现的金额
                merchantDataService.saveMerchant(merchant);
            } else if ("agent".equals(withdrewOrder.getType())) {
                User u = userDataService.getUserById(withdrewOrder.getApplicantId());
                Agent agent = agentDataService.findAgentById(u.getTableId());
                agent.setBalance(GetThreeBitsPoint(agent.getBalance() + withdrewOrder.getMoney_out()));  // 增加代理余额
                agent.setWithdrewMoney(GetThreeBitsPoint(agent.getWithdrewMoney() - withdrewOrder.getMoney_out()));  // 减少代理正在提现的金额
                agentDataService.saveAgent(agent);
            }
        } else throw new BlankInputException(); // 审批状态错误
        withdrewOrder.setOperateId(withdrewDealParameters.getOperatorId());
        withdrewOrder.setOperateTime(new Date());
        withdrewOrder.setMemo(withdrewDealParameters.getMemo());

        withdrewOrderDataService.saveWithdrewOrder(withdrewOrder);

    }

    @Override
    public Page<WithdrewOrder> getMyWithdrewOrder(int id, Pageable pageable, WithdrewOrder withdrewOrder) throws WrongIdException {
        Page<WithdrewOrder> page = withdrewOrderDataService.findByState(null, pageable, withdrewOrder);
        User user = userDataService.getUserById(id);
        if (user.getRole() == 1 || user.getRole() == 4) {

            return withdrewOrderDataService.findByOperatorId(id).stream().peek(p -> {
                User u = userDataService.getUserById(p.getApplicantId());
                User u1 = userDataService.getUserById(p.getOperateId());
                p.setApplicantUsername(u.getUsername());
                p.setOperateUsername(u1.getUsername());
            }).collect(Collectors.toList());
        } else
            throw new WrongIdException();
    }

    @Override
    public Page<WithdrewOrder> getWithdrewOrder(int uid, Pageable pageable, WithdrewOrder withdrewOrder) {
        Page<WithdrewOrder> page = withdrewOrderDataService.findByState(null, pageable, withdrewOrder);
        List list = page.getContent().stream().peek(p -> {
            String first = p.getCard_in().substring(0, 4);
            String last = p.getCard_in().substring(p.getCard_in().length() - 4);
            String middle = "";
            for (int i = 0; i < p.getCard_in().substring(4, p.getCard_in().length() - 4).length(); i++)
                middle += "*";
            p.setCard_in(first + middle + last);
            System.out.println(p.getCard_in());
            User user = userDataService.getUserById(p.getApplicantId());
            User user1 = userDataService.getUserById(p.getOperateId());
            User user2 = userDataService.getUserById(uid);

            if (user2.getRole() == 2 || user2.getRole() == 3) {
                if (user.getId() != user2.getId())
                    p = null;
            } else {
                p.setApplicantUsername(user.getUsername());
                p.setOperateUsername(user1.getUsername());
                if (user2.getRole() == 4)
                    if (user1.getId() != user2.getId())
                        p = null;
            }

        }).collect(Collectors.toList());
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}
