package njurestaurant.njutakeout.bl.report;

import njurestaurant.njutakeout.blservice.report.ReportBlService;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.dataservice.account.*;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.dataservice.company.PayPlatformDataService;
import njurestaurant.njutakeout.dataservice.order.ChangeOrderDataService;
import njurestaurant.njutakeout.dataservice.order.PlatformOrderDataService;
import njurestaurant.njutakeout.dataservice.order.WithdrewOrderDataService;
import njurestaurant.njutakeout.entity.account.*;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.entity.company.CompanyCard;
import njurestaurant.njutakeout.entity.company.PayPlatform;
import njurestaurant.njutakeout.entity.order.*;
import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import njurestaurant.njutakeout.response.report.*;
import njurestaurant.njutakeout.util.FormatDateTime;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static njurestaurant.njutakeout.config.websocket.WebSocketHandler.GetThreeBitsPoint;


@Service
public class ReportBlServiceImpl implements ReportBlService {
    private final PlatformOrderDataService platformOrderDataService;
    private final WithdrewOrderDataService withdrewOrderDataService;
    private final UserDataService userDataService;
    private final SupplierDataService supplierDataService;
    private final MerchantDataService merchantDataService;
    private final AgentDataService agentDataService;
    private final AlipayDataService alipayDataService;
    private final ChangeOrderDataService changeOrderDataService;
    private final DeviceDataService deviceDataService;
    private final PersonalCardDataService personalCardDataService;
    private final CompanyCardDataService companyCardDataService;
    private final PayRateListDataService payRateListDataService;
    private final PayPlatformDataService payPlatformDataService;
    private final UserDao userDao;

    @Autowired
    public ReportBlServiceImpl(PlatformOrderDataService platformOrderDataService, WithdrewOrderDataService withdrewOrderDataService, UserDataService userDataService, SupplierDataService supplierDataService, MerchantDataService merchantDataService, AgentDataService agentDataService, DeviceDataService deviceDataService, AlipayDataService alipayDataService, ChangeOrderDataService changeOrderDataService, PersonalCardDataService personalCardDataService, CompanyCardDataService companyCardDataService, PayRateListDataService payRateListDataService, PayPlatformDataService payPlatformDataService, UserDao userDao) {
        this.platformOrderDataService = platformOrderDataService;
        this.withdrewOrderDataService = withdrewOrderDataService;
        this.userDataService = userDataService;
        this.supplierDataService = supplierDataService;
        this.merchantDataService = merchantDataService;
        this.agentDataService = agentDataService;
        this.deviceDataService = deviceDataService;
        this.alipayDataService = alipayDataService;
        this.changeOrderDataService = changeOrderDataService;
        this.companyCardDataService = companyCardDataService;
        this.personalCardDataService = personalCardDataService;
        this.payRateListDataService = payRateListDataService;
        this.payPlatformDataService = payPlatformDataService;
        this.userDao = userDao;
    }

    /**
     * 商户报表
     * 系统编号(商户内部编号)，日期，商户号(商户在平台的账号)，存款(名下所有成功订单的总金额)，
     * 实际存款(名下所有成功订单的总金额-平台已经收取的手续费总额)，提款(已经提现成功的总金额，有多少笔提现成功的算多少)，
     * 冻结余额(正在提现的或者被管理员冻结的)，可用余额，代理分润r(如果他不是公司直属是有代理的，代理已经通过这个商户分红多少)，
     * 公司分润r(平台已经收取这个商户的手续费总额)，量分析r(存款详细规划，支付宝/微信/云闪付各成功多少)，成功率(根据总订单和成功订单计算出成功率)
     *
     * @return
     */
    @Override
    public List<MerchantReportResponse> getReportOfMerchant(Date startDate, Date endDate) throws WrongInputException {
        System.out.println("33333333333333333333333333333333");
        System.out.println(startDate);
        System.out.println(endDate);
        if (startDate == null) startDate = new Date();
        if (endDate == null) endDate = new Date();
        String date;
        if (FormatDateTime.isDayBeforeOrEqualThan(startDate, endDate)) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            c1.set(Calendar.HOUR, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            startDate = c1.getTime();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            c2.set(Calendar.HOUR, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);
            endDate = c2.getTime();
            System.out.println(startDate);
            System.out.println(endDate);
            if (DateUtils.isSameDay(startDate, endDate))
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd");
            else
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd") + "~" + FormatDateTime.dateToString(endDate, "yyyy-MM-dd");
        } else {
            throw new WrongInputException();
        }
//        List<PlatformOrder> platformOrders = platformOrderDataService.findPlatformByDate(c1.getTime(), c2.getTime());
        // 全部的成功的订单记录和提现记录

        List<PlatformOrder> platformOrders = platformOrderDataService.findPlatformByDate(startDate, endDate);
        List<WithdrewOrder> withdrewOrders = withdrewOrderDataService.findByDateRange(startDate, endDate);
//        List<PlatformOrder> allPlatformOrders = platformOrderDataService.findAll();
//        List<WithdrewOrder> successWithdrewOrders = withdrewOrderDataService.findByState(WithdrewState.SUCCESS);
        List<Merchant> merchantList = merchantDataService.getAllMerchants();
        List<Agent> agentList = agentDataService.getAllAgent();
        List<User> userList = userDataService.getAll();
        // 每个商户的报表<userid, 返回值>
        Map<Integer, MerchantReportResponse> merchantReportResponseMap = new HashMap<>();
        // 商家列表<userid, 商家信息>
        Map<Integer, Merchant> merchantMap = new HashMap<>();
        // 代理商列表<userid, 代理信息>
        Map<Integer, Agent> agentMap = new HashMap<>();

        List<MerchantReportResponse> result = new ArrayList<>();

        // 处理数据
        if (merchantList.size() > 0) {
            for (Merchant merchant : merchantList) {
                String number = "Sh" + String.format("%08d", merchant.getUser().getId());
                List<PlatformAnalyse> list = new ArrayList<>();
                List<SuccessOrdersRate> list1 = new ArrayList<>();
                List<SuccessOrder> list2 = new ArrayList<>();
                List<TotalOrder> list3 = new ArrayList<>();
                for (PayPlatform payPlatform : payPlatformDataService.findAllPayPlatform())
                    list.add(new PlatformAnalyse(payPlatform.getCodeCategory(), 0));
                for (PayPlatform payPlatform : payPlatformDataService.findAllPayPlatform())
                    list1.add(new SuccessOrdersRate(payPlatform.getCodeCategory(), 0));
                for (PayPlatform payPlatform : payPlatformDataService.findAllPayPlatform())
                    list2.add(new SuccessOrder(payPlatform.getCodeCategory(), 0));
                for (PayPlatform payPlatform : payPlatformDataService.findAllPayPlatform())
                    list3.add(new TotalOrder(payPlatform.getCodeCategory(), 0));
                MerchantReportResponse merchantReportResponse = null;
                if (userDao.findUserById(merchant.getApplyId()).getRole() == 1)
                    merchantReportResponse = new MerchantReportResponse(number, date, merchant.getUser().getUsername(),
                            merchant.getName(), 0.0, 0.0, 0.0, merchant.getWithdrewMoney(),
                            merchant.getBalance(), 0.0, 0.0, list, list1, list2, list3, 0);
                if (userDao.findUserById(merchant.getApplyId()).getRole() == 2)
                    merchantReportResponse = new MerchantReportResponse(number, date, merchant.getUser().getUsername(),
                            merchant.getName(), 0.0, 0.0, 0.0, merchant.getWithdrewMoney(),
                            merchant.getBalance(), 0.0, 0.0, list, list1, list2, list3, merchant.getApplyId());
                merchantReportResponseMap.put(merchant.getUser().getId(), merchantReportResponse);
                merchantMap.put(merchant.getUser().getId(), merchant);
            }
            if (agentList.size() > 0) {
                for (Agent agent : agentList) {
                    agentMap.put(agent.getUser().getId(), agent);
                }
            }

            if (platformOrders.size() > 0) {
                for (PlatformOrder platformOrder : platformOrders) {
                    if (merchantReportResponseMap.containsKey(platformOrder.getUid())) {
                        MerchantReportResponse merchantReportResponse = merchantReportResponseMap.get(platformOrder.getUid());
                        // SuccessOrdersRateAnalyse(merchantReportResponse.getSuccessOrdersRateList(),platformOrder.getType(),)
                        System.out.println("qqqqqqqqqq");
                        merchantReportResponse.setTotalOrderList(TotalOrdersAnalyse(merchantReportResponse.getTotalOrderList(), platformOrder.getType(), 1));
                        System.out.println(merchantReportResponse.getTotalOrderList().get(0).getTotalOrders() + merchantReportResponse.getTotalOrderList().get(1).getTotalOrders());
                        if (platformOrder.getState() == OrderState.PAID) { // 成功的订单
                            System.out.println("eeeeeeeeee");
                            merchantReportResponse.setSuccessOrderList(SuccessOrdersAnalyse(merchantReportResponse.getSuccessOrderList(), platformOrder.getType(), 1));
                            System.out.println("rrrrrrrrr");
                            merchantReportResponse.setDeposit(GetThreeBitsPoint(platformOrder.getPayMoney() + merchantReportResponse.getDeposit()));
                            Merchant merchant = merchantMap.get(platformOrder.getUid());
                            merchantReportResponse.setAvailiableDeposit(GetThreeBitsPoint(merchantReportResponse.getAvailiableDeposit() +
                                    platformOrder.getPayMoney() * (1 - platformOrder.getMerchantRate() / 100)));
                            //    if (platformOrder.getTime() != null && DateUtils.isSameDay(date, platformOrder.getTime())) { // 和查询日期同一天
                            if (agentMap.containsKey(merchant.getApplyId())) {
                                Agent agent = agentMap.get(merchant.getApplyId());
                                if (agent != null)
                                    merchantReportResponse.setAgentProfit(GetThreeBitsPoint(merchantReportResponse.getAgentProfit() + platformOrder.getPayMoney() *
                                            (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));    // 代理分润
                            } else
                                merchantReportResponse.setAgentProfit(0);
                            List<PlatformAnalyse> platformAnalyses = null;
                            platformAnalyses = dailyAnalyse(merchantReportResponse.getPlatformAnalyseList(), platformOrder.getType(), platformOrder.getPayMoney());
                            merchantReportResponse.setPlatformAnalyseList(platformAnalyses);    // 每日量分析
                            merchantReportResponse.setCompanyProfit(GetThreeBitsPoint(platformOrder.getAgentRate() / 100
                                    * platformOrder.getPayMoney() + merchantReportResponse.getCompanyProfit()));    // 公司分润

                        }
                        List<SuccessOrdersRate> successOrdersRates = null;
                        System.out.println("ttttttttttttt");
                        successOrdersRates = SuccessOrdersRateAnalyse(merchantReportResponse.getSuccessOrdersRateList(), merchantReportResponse.getSuccessOrderList(), merchantReportResponse.getTotalOrderList());
                        System.out.println("yyyyyyyyyyyy");
                        merchantReportResponse.setSuccessOrdersRateList(successOrdersRates);
                        merchantReportResponseMap.put(platformOrder.getUid(), merchantReportResponse);

                    }
                }
            }

            if (withdrewOrders.size() > 0) {
                for (WithdrewOrder withdrewOrder : withdrewOrders) {
                    if (withdrewOrder.getState() != WithdrewState.SUCCESS) continue;
                    if (merchantReportResponseMap.containsKey(withdrewOrder.getApplicantId())) {
                        MerchantReportResponse merchantReportResponse = merchantReportResponseMap.get(withdrewOrder.getApplicantId());
                        merchantReportResponse.setWithdrewed(GetThreeBitsPoint(withdrewOrder.getMoney_in() + merchantReportResponse.getWithdrewed()));//提款//每单要扣除3元手续费
                        merchantReportResponseMap.put(withdrewOrder.getApplicantId(), merchantReportResponse);
                    }
                }
            }

            for (Map.Entry<Integer, MerchantReportResponse> entry : merchantReportResponseMap.entrySet()) {
                result.add(entry.getValue());
            }
        }
        Collections.sort(result, (o1, o2) -> {
            //按照抽成前存款大小进行降序排列
            if (o1.getDeposit() < o2.getDeposit()) {
                return 1;
            }
            if (o1.getDeposit() == o2.getDeposit()) {
                return 0;
            }
            return -1;
        });
        return result;
    }

    /**
     * 代理商报表
     * 系统编号， 日期，代理账户
     * 分润点位（详细的分红点位，支付宝/微信），存款r（名下所有商户，所有成功订单总金额。细分支付宝/微信）
     * 分润r(名下所有商户，所有成功订单总金额*分润点位，分支付宝/微信)，提款r（名下所有商户，提现成功的总金额，显示实现段内）
     * 余额（名下所有商户未提现的总金额）
     *
     * @param startDate 时间段的开始时间
     * @param endDate   时间段的结束时间
     * @return
     */
    @Override
    public List<AgentReportResponse> getReportOfAgent(Date startDate, Date endDate) throws WrongInputException {
        // 判断输入的时间格式是否错误
        if (startDate == null) startDate = new Date();
        if (endDate == null) endDate = new Date();
        String date;
        if (FormatDateTime.isDayBeforeOrEqualThan(startDate, endDate)) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            c1.set(Calendar.HOUR, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            startDate = c1.getTime();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            c2.set(Calendar.HOUR, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);
            endDate = c2.getTime();
            if (DateUtils.isSameDay(startDate, endDate)) date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd");
            else
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd") + "~" + FormatDateTime.dateToString(endDate, "yyyy-MM-dd");
        } else {
            throw new WrongInputException();
        }

        List<Merchant> merchantList = merchantDataService.getAllMerchants();
        List<Agent> agentList = agentDataService.getAllAgent();
        List<PlatformOrder> platformOrders = platformOrderDataService.findPlatformByDate(startDate, endDate);
        List<WithdrewOrder> withdrewOrders = withdrewOrderDataService.findByDateRange(startDate, endDate);

        Map<Integer, Merchant> merchantMap = new HashMap<>();
        Map<Integer, Agent> agentMap = new HashMap<>();
        Map<Integer, AgentReportResponse> agentReportResponseMap = new HashMap<>();

        List<AgentReportResponse> result = new ArrayList<>();

        // 数据处理
        if (agentList.size() > 0) {
            for (Agent agent : agentList) {
                String number = "Dl" + String.format("%08d", agent.getUser().getId());
                agentMap.put(agent.getUser().getId(), agent);
                List<PlatformAnalyse> dlist = new ArrayList<>();
                List<PlatformAnalyse> plist = new ArrayList<>();
                for (PayPlatform payPlatform : payPlatformDataService.findAllPayPlatform())
                    dlist.add(new PlatformAnalyse(payPlatform.getCodeCategory(), 0));
                for (PayPlatform payPlatform : payPlatformDataService.findAllPayPlatform())
                    plist.add(new PlatformAnalyse(payPlatform.getCodeCategory(), 0));
                agentReportResponseMap.put(agent.getUser().getId(), new AgentReportResponse(number, date, agent.getUser().getUsername(), agent.getAgentName(), dlist, plist, 0.0, agent.getWithdrewMoney(), agent.getBalance()));
            }
        }
//        if (merchantList.size() > 0) {
//            for (Merchant merchant : merchantList) {
//                if (agentReportResponseMap.containsKey(merchant.getApplyId())) { // 该用户上级是代理商
//                    merchantMap.put(merchant.getUser().getId(), merchant);
//                    AgentReportResponse agentReportResponse = agentReportResponseMap.get(merchant.getApplyId());
//                    agentReportResponse.setBalance(merchant.getBalance() + agentReportResponse.getBalance());//名下所有商戶的余额
//                    agentReportResponseMap.put(merchant.getApplyId(), agentReportResponse);
//                }
//            }
//        }

        // 报表生成
        if (platformOrders.size() > 0) {
            for (PlatformOrder platformOrder : platformOrders) {
                if (platformOrder.getState() != OrderState.PAID) continue;   // 过滤掉未成功的订单
                Merchant merchant = merchantMap.get(platformOrder.getUid());
                if (merchant != null) {
                    Agent agent = agentMap.get(merchant.getApplyId());
                    if (agent == null) continue;  // 该条记录的商户不属于某个代理
                    AgentReportResponse agentReportResponse = agentReportResponseMap.get(agent.getUser().getId());
                    List<PlatformAnalyse> dlist, plist;

                    dlist = dailyAnalyse(agentReportResponse.getDepositList(), platformOrder.getType(), platformOrder.getPayMoney());//名下所有商户的平台流量
                    plist = dailyAnalyse(agentReportResponse.getProfitList(), platformOrder.getType(), GetThreeBitsPoint(platformOrder.getPayMoney() *
                            (platformOrder.getMerchantRate() - platformOrder.getAgentRate()) / 100));//代理商的各平台利润
                    agentReportResponse.setDepositList(dlist);
                    agentReportResponse.setProfitList(plist);
                    agentReportResponseMap.put(merchant.getApplyId(), agentReportResponse);
                }
            }
        }
        if (withdrewOrders.size() > 0) { // 提款
            for (WithdrewOrder withdrewOrder : withdrewOrders) {
                if (withdrewOrder.getState() != WithdrewState.SUCCESS) continue; // 过滤未成功的提款
                Agent agent = agentMap.get(withdrewOrder.getApplicantId());
                if (agent != null) {
                    AgentReportResponse agentReportResponse = agentReportResponseMap.get(agent.getUser().getId());
                    if (agentReportResponse != null) {
                        agentReportResponse.setWithdrewed(GetThreeBitsPoint(withdrewOrder.getMoney_in() + agentReportResponse.getWithdrewed()));
                        agentReportResponseMap.put(agent.getUser().getId(), agentReportResponse);
                    }
                }
            }
        }

        for (Map.Entry<Integer, AgentReportResponse> entry : agentReportResponseMap.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    /**
     * 量分析，支付宝/微信/云闪付成功订单多少
     *
     * @param platformAnalyses 量分析数组
     * @param type             订单类型
     * @param money            订单金额
     * @return
     */
    private List<PlatformAnalyse> dailyAnalyse(List<PlatformAnalyse> platformAnalyses, String type, double money) {
        PlatformAnalyse temp = null;
        if (platformAnalyses.size() > 0) {
            for (PlatformAnalyse p : platformAnalyses) {
                if (type.equals(p.getType())) {
                    temp = p;
                    platformAnalyses.remove(p);
                    break;
                }
            }
        }
        temp.setMoney(temp.getMoney() + money);
        platformAnalyses.add(temp);
        return platformAnalyses;
    }

    private List<SuccessOrdersRate> SuccessOrdersRateAnalyse(List<SuccessOrdersRate> SuccessOrdersRates, List<SuccessOrder> successOrders, List<TotalOrder> totalOrders) {
        if (SuccessOrdersRates.size() > 0) {
            for (SuccessOrdersRate p : SuccessOrdersRates) {
                System.out.println("1zzzzz");
                if (GetTotalOrdersAnalyse(totalOrders, p.getType()) == 0)
                    p.setSuccessOrdersRate(0);
                else {
                    String a = new java.text.DecimalFormat("#.00000").format(GetSuccessOrders(successOrders, p.getType()) / GetTotalOrdersAnalyse(totalOrders, p.getType()));
                    p.setSuccessOrdersRate(Double.parseDouble(a));
                }
            }
        }
        for (SuccessOrdersRate p : SuccessOrdersRates) {
            System.out.println("1111111sdadad");
            System.out.println(p.getType() + p.getSuccessOrdersRate());
        }
        return SuccessOrdersRates;
    }

    private List<SuccessOrder> SuccessOrdersAnalyse(List<SuccessOrder> successOrders, String type, int SuccessOrders) {
        SuccessOrder temp = null;
        if (successOrders.size() > 0) {
            for (SuccessOrder p : successOrders) {
                if (type.equals(p.getType())) {
                    temp = p;
                    successOrders.remove(p);
                    break;
                }
            }
        }
        temp.setSuccessOrders(temp.getSuccessOrders() + SuccessOrders);
        successOrders.add(temp);
        return successOrders;
    }

    private List<TotalOrder> TotalOrdersAnalyse(List<TotalOrder> totalOrders, String type, int TotalOrders) {
        TotalOrder temp = null;
        if (totalOrders.size() > 0) {
            for (TotalOrder p : totalOrders) {
                if (type.equals(p.getType())) {
                    temp = p;
                    totalOrders.remove(p);
                    break;
                }
            }
        }
        temp.setTotalOrders(temp.getTotalOrders() + TotalOrders);
        totalOrders.add(temp);
        return totalOrders;
    }

    private int GetSuccessOrders(List<SuccessOrder> successOrders, String type) {
        SuccessOrder temp = null;
        if (successOrders.size() > 0) {
            for (SuccessOrder p : successOrders) {
                if (type.equals(p.getType())) {
                    temp = p;
                    return temp.getSuccessOrders();
                }
            }
        }
        return 0;
    }

    private int GetTotalOrdersAnalyse(List<TotalOrder> totalOrders, String type) {
        TotalOrder temp = null;
        if (totalOrders.size() > 0) {
            for (TotalOrder p : totalOrders) {
                if (type.equals(p.getType())) {
                    temp = p;
                    return temp.getTotalOrders();
                }
            }
        }
        return 0;
    }

    /**
     * 收款码报表(支付宝)
     * 编号number
     * 日期（xxxx-xx-xx ~xxxx-xx-xx）data1,data2
     * 供码用户名supplierName
     * 支付宝账号
     * 该支付宝号的实收账款
     * 该支付宝提现到自己绑定的个人银行卡的金额
     *
     * @param startDate 报表开始时间
     * @param endDate   报表结束时间
     * @return 收款码报表
     * @throws WrongInputException 时间输入错误
     */
    @Override
    public List<ReceiptCodeReportResponse> getReportOfReceiptCode(Date startDate, Date endDate) throws WrongInputException {
        // 判断输入的时间格式是否错误
        if (startDate == null) startDate = new Date();
        if (endDate == null) endDate = new Date();
        String date;
        if (FormatDateTime.isDayBeforeOrEqualThan(startDate, endDate)) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            c1.set(Calendar.HOUR, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            startDate = c1.getTime();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            c2.set(Calendar.HOUR, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);
            endDate = c2.getTime();
            if (DateUtils.isSameDay(startDate, endDate)) date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd");
            else
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd") + "~" + FormatDateTime.dateToString(endDate, "yyyy-MM-dd");
        } else {
            throw new WrongInputException();
        }

        // 数据准备
        List<Alipay> alipayList = alipayDataService.findAll();
        List<Device> deviceList = deviceDataService.findAll();
        List<PlatformOrder> platformOrderList = platformOrderDataService.findPlatformByDate(startDate, endDate);
        List<QRcodeChangeOrder> qRcodeChangeOrderList = changeOrderDataService.findAllQrCodeChangeOrderByDate(startDate, endDate);

        Map<Integer, Alipay> alipayMap = new HashMap<>();
        Map<String, Device> deviceMap = new HashMap<>();
        Map<String, ReceiptCodeReportResponse> receiptCodeReportResponseMap = new HashMap<>();
        if (deviceList.size() > 0) {
            for (Device device : deviceList) {
                deviceMap.put(device.getImei(), device);
            }
        }
        if (alipayList.size() > 0) {
            for (Alipay alipay : alipayList) {
                String name = "";
                if (deviceMap.containsKey(alipay.getImei())) { // 该支付宝账号有对应的设备号
                    Supplier supplier = deviceMap.get(alipay.getImei()).getSupplier();
                    if (supplier != null) { // 该设备有对应的供码用户账号
                        name = supplier.getUser().getUsername();
                    } else continue;
                } else continue;    // 该支付宝账号没有对应的设备号
                if (alipay.getLoginId() == null) continue;
                alipayMap.put(alipay.getId(), alipay);
                String number = "A" + String.format("%08d", alipay.getId());
                receiptCodeReportResponseMap.put(alipay.getLoginId(), new ReceiptCodeReportResponse(number, date, name, alipay.getLoginId(), 0, 0, 0, 0));
            }
        }

        // 根据平台订单计算该支付宝实收金额
        if (platformOrderList.size() > 0) {
            for (PlatformOrder platformOrder : platformOrderList) {
                if (platformOrder.getState() != OrderState.PAID) continue;   // 不是已成功的订单
                if (!alipayMap.containsKey(platformOrder.getTableId())) continue; //没有该支付宝id对应的账号信息
                Alipay alipay = alipayMap.get(platformOrder.getTableId());
                if (receiptCodeReportResponseMap.containsKey(alipay.getLoginId())) {
                    ReceiptCodeReportResponse receiptCodeReportResponse = receiptCodeReportResponseMap.get(alipay.getLoginId());
                    receiptCodeReportResponse.setPayMoney(platformOrder.getPayMoney() + receiptCodeReportResponse.getPayMoney());
                    receiptCodeReportResponseMap.put(alipay.getLoginId(), receiptCodeReportResponse);
                }
            }
        }
        // 根据内部码帐变订单计算该支付宝提现到个人卡的金额
        if (qRcodeChangeOrderList.size() > 0) {
            for (QRcodeChangeOrder qRcodeChangeOrder : qRcodeChangeOrderList) {
                if (!qRcodeChangeOrder.getState().equals("提现到账成功")) {
                    ReceiptCodeReportResponse receiptCodeReportResponse = receiptCodeReportResponseMap.get(qRcodeChangeOrder.getLoginId());
                    receiptCodeReportResponse.setWithdrewing(receiptCodeReportResponse.getWithdrewing() + qRcodeChangeOrder.getMoney());
                    receiptCodeReportResponseMap.put(qRcodeChangeOrder.getLoginId(), receiptCodeReportResponse);
                    continue; // 没有提现成功的订单
                }
                if (receiptCodeReportResponseMap.containsKey(qRcodeChangeOrder.getLoginId())) {
                    ReceiptCodeReportResponse receiptCodeReportResponse = receiptCodeReportResponseMap.get(qRcodeChangeOrder.getLoginId());
                    receiptCodeReportResponse.setWithdrew(receiptCodeReportResponse.getWithdrew() + qRcodeChangeOrder.getRealMoney());
                    receiptCodeReportResponse.setFee(receiptCodeReportResponse.getFee() + qRcodeChangeOrder.getMoney() - qRcodeChangeOrder.getRealMoney());
                    receiptCodeReportResponseMap.put(qRcodeChangeOrder.getLoginId(), receiptCodeReportResponse);
                }
            }
        }
        List<ReceiptCodeReportResponse> result = new ArrayList<>();
        // 生成报表
        for (Map.Entry<String, ReceiptCodeReportResponse> entry : receiptCodeReportResponseMap.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    /**
     * 资金报表（公司内部）
     * 编号number
     * 日期（xxxx-xx-xx ~xxxx-xx-xx）data1,data2
     * 供码用户的个人银行卡转入到公司银行卡的金额
     * 公司银行卡转出到代理商个人银行卡的金额
     * 公司银行卡转出到商户个人银行卡的金额
     *
     * @param startDate 开始时间
     * @param endDate   报表统计的技术时间
     * @return 资金报表
     * @throws WrongInputException 时间格式输入错误
     */
    @Override
    public List<FundingReportResponse> getReportOfFunding(Date startDate, Date endDate) throws WrongInputException {
        // 判断输入的时间格式是否错误
        if (startDate == null) startDate = new Date();
        if (endDate == null) endDate = new Date();
        String date;
        if (FormatDateTime.isDayBeforeOrEqualThan(startDate, endDate)) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            c1.set(Calendar.HOUR, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            startDate = c1.getTime();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            c2.set(Calendar.HOUR, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);
            endDate = c2.getTime();
            if (DateUtils.isSameDay(startDate, endDate)) date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd");
            else
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd") + "~" + FormatDateTime.dateToString(endDate, "yyyy-MM-dd");
        } else {
            throw new WrongInputException();
        }

        List<CardChangeOrder> cardChangeOrderList = changeOrderDataService.findAllCardChangeOrderByDate(startDate, endDate);
        // List<PersonalCard> personalCardList = personalCardDataService.findAllCards();
        List<CompanyCard> companyCardList = companyCardDataService.findAllCompanyCards();
        //  Map<String, PersonalCard> personalCardMap = new HashMap<>();
        Map<String, CompanyCard> companyCardMap = new HashMap<>();
        Map<String, FundingReportResponse> fundingReportResponseMap = new HashMap<>();

//        if (personalCardList.size() > 0) {
//            for (PersonalCard personalCard : personalCardList) {
//                personalCardMap.put(personalCard.getCardNumber(), personalCard);
//            }
//        }
        if (companyCardList.size() > 0) {
            for (CompanyCard companyCard : companyCardList) {
                companyCardMap.put(companyCard.getCardNumber(), companyCard);
                String number = "A" + String.format("%08d", companyCard.getId());
                fundingReportResponseMap.put(companyCard.getCardNumber(), new FundingReportResponse(
                        number, date, companyCard.getCardNumber(), companyCard.getBank(), companyCard.getName(), companyCard.getStatus(), 0, 0, companyCard.getBalance()));
            }
        }

        double supplierToCom = 0, comToAgent = 0, comToMerchant = 0;
        if (cardChangeOrderList.size() > 0) {
            for (CardChangeOrder cardChangeOrder : cardChangeOrderList) {
                if (companyCardMap.containsKey(cardChangeOrder.getCardNumber_out()) && cardChangeOrder.getState() == WithdrewState.SUCCESS) {
                    CompanyCard companyCard = companyCardMap.get(cardChangeOrder.getCardNumber_out());
                    FundingReportResponse fundingReportResponse = fundingReportResponseMap.get(companyCard.getCardNumber());
                    fundingReportResponse.setOut(fundingReportResponse.getOut() + cardChangeOrder.getMoney_in());
                }
                if (companyCardMap.containsKey(cardChangeOrder.getCardNumber_in()) && cardChangeOrder.getState() == WithdrewState.SUCCESS) {
                    CompanyCard companyCard = companyCardMap.get(cardChangeOrder.getCardNumber_in());
                    FundingReportResponse fundingReportResponse = fundingReportResponseMap.get(companyCard.getCardNumber());
                    fundingReportResponse.setIn(fundingReportResponse.getOut() + cardChangeOrder.getMoney_in());
                }
            }
        }
//        for (Map.Entry<String, ReceiptCodeReportResponse> entry : receiptCodeReportResponseMap.entrySet()) {
//            result.add(entry.getValue());
//        }

        // 生成报表
        List<FundingReportResponse> result = new ArrayList<>();
        for (Map.Entry<String, FundingReportResponse> entry : fundingReportResponseMap.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }

    /**
     * 团队报表（就是供码用户报表）
     * 编号number
     * 日期（xxxx-xx-xx ~xxxx-xx-xx）data1,data2
     * 供码用户名supplierName
     * 该供码用户所有支付宝的实收账款
     * 该供码用户所有支付宝提现到自己绑定的个人银行卡的金额
     *
     * @param startDate 时间域开始时间
     * @param endDate   时间域结束时间
     * @return 返回供码用户报表
     * @throws WrongInputException 时间格式错误异常
     */
    @Override
    public List<SupplierReportResponse> getReportOfSupplier(Date startDate, Date endDate) throws WrongInputException {
        // 判断输入的时间格式是否错误
        if (startDate == null) startDate = new Date();
        if (endDate == null) endDate = new Date();
        String date;
        if (FormatDateTime.isDayBeforeOrEqualThan(startDate, endDate)) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            c1.set(Calendar.HOUR, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            startDate = c1.getTime();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(endDate);
            c2.set(Calendar.HOUR, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 999);
            endDate = c2.getTime();
            if (DateUtils.isSameDay(startDate, endDate)) date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd");
            else
                date = FormatDateTime.dateToString(startDate, "yyyy-MM-dd") + "~" + FormatDateTime.dateToString(endDate, "yyyy-MM-dd");
        } else {
            throw new WrongInputException();
        }

        // 数据准备
        List<Alipay> alipayList = alipayDataService.findAll();
        List<Device> deviceList = deviceDataService.findAll();
        List<Supplier> supplierList = supplierDataService.getAllSuppliers();
        List<PlatformOrder> platformOrderList = platformOrderDataService.findPlatformByDate(startDate, endDate);
        List<QRcodeChangeOrder> qRcodeChangeOrderList = changeOrderDataService.findAllQrCodeChangeOrderByDate(startDate, endDate);

        Map<Integer, Supplier> supplierMap = new HashMap<>();
        Map<String, Integer> deviceMap = new HashMap<>();
        Map<Integer, SupplierReportResponse> supplierReportResponseMap = new HashMap<>();
        Map<String, Integer> alipayMap = new HashMap<>();
        if (supplierList.size() > 0) {
            for (Supplier supplier : supplierList) {
                // if(supplier.getStatus().equals("启用")) {
                supplierMap.put(supplier.getId(), supplier);
                // }
            }
        }
        if (deviceList.size() > 0) {
            for (Device device : deviceList) {
                if (device.getSupplier() != null && supplierMap.containsKey(device.getSupplier().getId())) {    // 筛选不存在的供码用户
                    Supplier supplier = device.getSupplier();
                    String number = "Gm" + String.format("%08d", supplier.getId());
                    SupplierReportResponse supplierReportResponse = new SupplierReportResponse(number, date, supplier.getUser().getUsername(),
                            0, 0, 0, 0);
                    supplierReportResponseMap.put(supplier.getId(), supplierReportResponse);
                    deviceMap.put(device.getImei(), supplier.getId());
                }
            }
        }
        if (alipayList.size() > 0) {
            for (Alipay alipay : alipayList) {
                if (deviceMap.containsKey(alipay.getImei())) { // 该支付宝账号有对应的设备号
                    int sid = deviceMap.get(alipay.getImei());
                    alipayMap.put(alipay.getLoginId(), sid);
                }
            }
        }

        if (platformOrderList.size() > 0) {
            for (PlatformOrder platformOrder : platformOrderList) {
                if (platformOrder.getState() != OrderState.PAID) continue; // 过滤没有成功的订单
                int sid = userDataService.getUserById(platformOrder.getSupplierid()).getTableId();
                if (supplierReportResponseMap.containsKey(sid)) {
                    SupplierReportResponse supplierReportResponse = supplierReportResponseMap.get(deviceMap.get(platformOrder.getImei()));
                    supplierReportResponse.setRealReceipt(platformOrder.getPayMoney() + supplierReportResponse.getRealReceipt());
                    supplierReportResponseMap.put(sid, supplierReportResponse);
                }

            }
        }
        if (qRcodeChangeOrderList.size() > 0) {
            for (QRcodeChangeOrder qRcodeChangeOrder : qRcodeChangeOrderList) {
                if (!qRcodeChangeOrder.getState().equals("提现到账成功")) {
                    int sid = alipayMap.get(qRcodeChangeOrder.getLoginId());
                    SupplierReportResponse supplierReportResponse = supplierReportResponseMap.get(sid);
                    supplierReportResponse.setWithdrewing(supplierReportResponse.getWithdrewing() + qRcodeChangeOrder.getMoney());
                    supplierReportResponseMap.put(sid, supplierReportResponse);
                    continue;
                }
                if (alipayMap.containsKey(qRcodeChangeOrder.getLoginId())) {
                    int sid = alipayMap.get(qRcodeChangeOrder.getLoginId());
                    SupplierReportResponse supplierReportResponse = supplierReportResponseMap.get(sid);
                    supplierReportResponse.setWithdrew(supplierReportResponse.getWithdrew() + qRcodeChangeOrder.getRealMoney());
                    supplierReportResponse.setFee(supplierReportResponse.getFee()+ qRcodeChangeOrder.getMoney() - qRcodeChangeOrder.getRealMoney());
                    supplierReportResponseMap.put(sid, supplierReportResponse);
                }
            }
        }

        // 生成报表
        List<SupplierReportResponse> supplierReportResponses = new ArrayList<>();
        for (Map.Entry<Integer, SupplierReportResponse> entry : supplierReportResponseMap.entrySet()) {
            supplierReportResponses.add(entry.getValue());
        }

        return supplierReportResponses;
    }

}
