package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.PlatformOrderBlService;
import njurestaurant.njutakeout.config.websocket.WebSocketHandler;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.data.dao.app.DeviceDao;
import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayOrderDataService;
import njurestaurant.njutakeout.dataservice.company.PayTypeDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.company.PayType;
import njurestaurant.njutakeout.entity.order.AlipayOrder;
import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.OrderWrongInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.publicdatas.account.AgentDailyFlow;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;
import njurestaurant.njutakeout.util.FormatDateTime;
import njurestaurant.njutakeout.util.StringParseUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static njurestaurant.njutakeout.config.websocket.WebSocketHandler.GetThreeBitsPoint;
import static njurestaurant.njutakeout.publicdatas.order.OrderState.PAID;

@Service
public class PlatformOrderBlServiceImpl implements PlatformOrderBlService {

    private final PlatformOrderDataService platformOrderDataService;
    private final UserDataService userDataService;
    private final MerchantDataService merchantDataService;
    private final AlipayDataService alipayDataService;
    private final UserDao userDao;
    private final DeviceDao deviceDao;
    private final SupplierDataService supplierDataService;
    private final AlipayOrderDataService alipayOrderDataService;
    private final AgentDataService agentDataService;
    private final PayRateListDataService payRateListDataService;
    private final PayTypeDataService payTypeDataService;

    @Autowired
    public PlatformOrderBlServiceImpl(PlatformOrderDataService platformOrderDataService, UserDataService userDataService, MerchantDataService merchantDataService, AlipayDataService alipayDataService, UserDao userDao, DeviceDao deviceDao, SupplierDataService supplierDataService, AlipayOrderDataService alipayOrderDataService, AgentDataService agentDataService, PayRateListDataService payRateListDataService, PayTypeDataService payTypeDataService) {
        this.platformOrderDataService = platformOrderDataService;
        this.userDataService = userDataService;
        this.merchantDataService = merchantDataService;
        this.alipayDataService = alipayDataService;
        this.userDao = userDao;
        this.deviceDao = deviceDao;
        this.supplierDataService = supplierDataService;
        this.alipayOrderDataService = alipayOrderDataService;
        this.agentDataService = agentDataService;
        this.payRateListDataService = payRateListDataService;
        this.payTypeDataService = payTypeDataService;
    }

    @Override
    public PlatformOrder findPlatformOrderById(int id) throws WrongIdException {
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if (platformOrder == null) {
            throw new WrongIdException();
        } else {

            return platformOrder;
        }
    }

    @Override
    public PlatformOrder findPlatformOrderByNumber(String number) {
        return platformOrderDataService.findByNumber(number);
    }

    /**
     * 查看全部订单明细
     *
     * @return the order information
     */
    @Override
    public Page<OrderListResponse> findAllPlatformOrders(Pageable pageable,PlatformOrder platformOrder) {
        // 找出全部商家的信息
        List<User> merchantUser = userDataService.getUserByRole(3);
        Map<Integer, String> usernameMap = new HashMap<>();
        for (User user : merchantUser) {
            usernameMap.put(user.getId(), user.getUsername());
        }
        Page<PlatformOrder> page= platformOrderDataService.findAll(pageable,platformOrder);
        // 将错误的商家id信息过滤
        List<OrderListResponse> result= page.getContent().stream().map(p -> {
            if (usernameMap.containsKey(p.getUid())) {
                User user = userDao.findUserById(p.getUid());
                Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                if (p.getState() == OrderState.WAITING_FOR_PAYING)
                    if (checkOrderIsExpired(p.getTime())) {
                        p.setState(OrderState.EXPIRED);
                        platformOrderDataService.savePlatformOrder(p);
                    }
                PayType payType = payTypeDataService.findById(p.getPayTypeId());
                if (userDao.findUserById(merchant.getApplyId()).getRole() == 1)
                    return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), p.getSupplierid(), 0, usernameMap.get(p.getUid()),
                            payType.getCodeCategory(), payType.getCodeType());
                else if (userDao.findUserById(merchant.getApplyId()).getRole() == 2)
                    return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), p.getSupplierid(), merchant.getApplyId(), usernameMap.get(p.getUid()),
                            payType.getCodeCategory(), payType.getCodeType());
            } else return null;
            return null;
        }).filter(pf -> pf != null).collect(Collectors.toList());
        Page page1 =new PageImpl(result,page.getPageable(),page.getTotalElements());
//        Collections.sort(result, (o1, o2) -> {
//            //按照抽成前存款大小进行降序排列
//            return Integer.compare(0, o1.getTime().compareTo(o2.getTime()));
//        });
        return  page1;
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
        // 预设失效时间为3分钟
        if (minutes > TransactionBlServiceImpl.time) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws BlankInputException, OrderWrongInputException {
        double rate;
        PlatformOrder platformOrder = platformOrderDataService.findById(id);
        if (platformOrder == null) {
            throw new OrderWrongInputException(new WrongResponse(9999, "订单不存在"));
        } else {
            Date date = FormatDateTime.TenTimestampToDate(StringParseUtil.StringToInt(platformUpdateParameters.getPayTime()));
            OrderState orderState = OrderState.valueOf(platformUpdateParameters.getState());
            switch (orderState) {
                case WAITING_FOR_PAYING:
//                    if (platformOrder.getState() == OrderState.PAID || platformOrder.getState() == OrderState.EXPIRED)
                    throw new OrderWrongInputException(new WrongResponse(9998, "所有订单不允许修改成未支付订单"));
                case PAID:
                    if (platformOrder.getState() == OrderState.EXPIRED || platformOrder.getState() == OrderState.WAITING_FOR_PAYING) {
                        AlipayOrder alipayOrder = new AlipayOrder(platformOrder.getImei(), platformUpdateParameters.getOrderId(),
                                platformUpdateParameters.getRealPay(), platformUpdateParameters.getMemo(), date);
                        alipayOrderDataService.saveAlipayOrder(alipayOrder);
                    } else if (platformOrder.getState() == PAID) {
                        throw new OrderWrongInputException(new WrongResponse(9997, "不允许修改已支付订单"));
                    }
                    platformOrder.setMoney(platformUpdateParameters.getMoney());
                    platformOrder.setPayMoney(platformUpdateParameters.getRealPay());
                    platformOrder.setPayTime(date);
                    platformOrder.setState(PAID);
                    platformOrderDataService.savePlatformOrder(platformOrder);
                    //   platformOrderDataService.savePlatformOrder(platformOrder);
                    User user = userDataService.getUserById(platformOrder.getUid());
                    if (user != null) {
                        Merchant merchant = merchantDataService.findMerchantById(user.getTableId());
                        User suser = userDataService.getUserById(merchant.getApplyId());
                        if (merchant != null) {

                            merchant.setBalance(GetThreeBitsPoint(merchant.getBalance() + platformUpdateParameters.getRealPay() * (1 - platformOrder.getMerchantRate() / 100)));
                            merchantDataService.saveMerchant(merchant);
                        }
                        // 操作上级,商户的代理商
                        if (suser != null) {
                            if (suser.getRole() == 2) {
                                Agent agent = agentDataService.findAgentById(suser.getTableId());
                                agent.setBalance(GetThreeBitsPoint(agent.getBalance() + platformUpdateParameters.getRealPay() * (platformOrder.getMerchantRate()-platformOrder.getAgentRate())/ 100));
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
                                    AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.flow.get(agent.getId()) + platformUpdateParameters.getRealPay() * (1 - platformOrder.getMerchantRate() / 100)));
                                } else {
                                    AgentDailyFlow.flow.put(agent.getId(), GetThreeBitsPoint(platformUpdateParameters.getRealPay() * (1 - platformOrder.getMerchantRate() / 100)));
                                }
                                // 计算代理的每日佣金
                                if (AgentDailyFlow.commission.containsKey(agent.getId())) {
                                    AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(AgentDailyFlow.commission.get(agent.getId()) + platformUpdateParameters.getRealPay() * (platformOrder.getMerchantRate()-platformOrder.getAgentRate())  / 100));
                                } else {
                                    AgentDailyFlow.commission.put(agent.getId(), GetThreeBitsPoint(platformUpdateParameters.getRealPay() * (platformOrder.getMerchantRate()-platformOrder.getAgentRate()) / 100));
                                }
                            }
                        }
                    }
                    break;
                case EXPIRED:
                    throw new OrderWrongInputException(new WrongResponse(9996, "所有订单不允许修改成已失效订单"));
                default:
                    throw new BlankInputException();
            }
            return null;
        }
    }

//    @Override
//    public List<OrderListResponse> merchantOrderReportByUid(int uid) {
//        // 找出全部商家的信息
//        User user = userDataService.getUserById(uid);
//        // 如果改用户不是商家无法看到商家的个人订单报表信息
//        if (user.getRole() != 3) return null;
//        List<PlatformOrder> platformOrders = platformOrderDataService.findByUid(uid);
//        return platformOrders.stream().map(p -> {
//            String type = null;
//            if (p.getType().equals("alipay")) {  // 支付宝的收款方式
//                Alipay alipay = alipayDataService.findById(p.getTableId());
//                type = "支付宝";
//                return new OrderListResponse(p.getId(), p.getNumber(), p.getMoney(), p.getPayMoney(), p.getRechargeId(), p.getPayCode(), p.getState(), p.getTime(), p.getPayTime(), p.getUid(), user.getUsername(), type, alipay.getId(), alipay.getName());
//            } else return null; // 可能有微信的收款方式
//        }).filter(pf -> pf != null).collect(Collectors.toList());
//    }

//    @Override
//    public List<MerchantReportResponse> merchantsOrderReport() {
//        List<PlatformOrder> platformOrders = platformOrderDataService.findAll();
//        List<User> merchantUser = userDataService.getUserByRole(3);
//        Map<Integer, String> usernameMap = new HashMap<>();
//        for (User user : merchantUser) {
//            usernameMap.put(user.getId(), user.getUsername());
//        }
//        return null;
////        return platformOrders.stream().map(p -> {
////            if (usernameMap.containsKey(p.getUid())) {
////                MerchantReportResponse merchantReportResponse = new MerchantReportResponse(usernameMap.get(p.getUid()), p.getMoney(), p.getPayMoney(), p.getTime(), p.getState());
////                return merchantReportResponse;
////            } else {
////                return null;
////            }
////        }).filter(p -> p != null).collect(Collectors.toList());
//    }
}
