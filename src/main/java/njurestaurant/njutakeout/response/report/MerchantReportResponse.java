package njurestaurant.njutakeout.response.report;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

/**
* 系统编号(商户内部编号)，日期，商户号(商户在平台的账号)，存款(名下所有成功订单的总金额)，
* 实际存款(名下所有成功订单的总金额-平台已经收取的手续费总额)，提款(已经提现成功的总金额，有多少笔提现成功的算多少)，
* 冻结余额(正在提现的或者被管理员冻结的)，可用余额，代理分润(如果他不是公司直属是有代理的，代理已经通过这个商户分红多少)，
* 公司分润(平台已经收取这个商户的手续费总额)，量分析(存款详细规划，支付宝/微信/云闪付各成功多少)，成功率(根据总订单和成功订单计算出成功率)
* */
public class MerchantReportResponse extends Response {
    private String number;
    private String date;
    /*商户用户名*/
    private String username;
    private String merchantName;
    private double deposit; //抽成前存款
    private double availiableDeposit;//  抽成后存款
    private double withdrewed;//已提现
    private double withdrewing;//提现中金额
    private double balance;
    private double agentProfit;//代理收益
    private double companyProfit;//公司收益
    private List<PlatformAnalyse> platformAnalyseList;
    private List<SuccessOrdersRate> successOrdersRateList;
    private List<SuccessOrder> successOrderList;
    private List<TotalOrder> totalOrderList;
    private int agentId;

    public MerchantReportResponse(String number, String date, String username, String merchantName, double deposit, double availiableDeposit, double withdrewed, double withdrewing, double balance, double agentProfit, double companyProfit, List<PlatformAnalyse> platformAnalyseList, List<SuccessOrdersRate> successOrdersRateList, List<SuccessOrder> successOrderList, List<TotalOrder> totalOrderList, int agentId) {
        this.number = number;
        this.date = date;
        this.username = username;
        this.merchantName = merchantName;
        this.deposit = deposit;
        this.availiableDeposit = availiableDeposit;
        this.withdrewed = withdrewed;
        this.withdrewing = withdrewing;
        this.balance = balance;
        this.agentProfit = agentProfit;
        this.companyProfit = companyProfit;
        this.platformAnalyseList = platformAnalyseList;
        this.successOrdersRateList = successOrdersRateList;
        this.successOrderList = successOrderList;
        this.totalOrderList = totalOrderList;
        this.agentId = agentId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getAvailiableDeposit() {
        return availiableDeposit;
    }

    public void setAvailiableDeposit(double availiableDeposit) {
        this.availiableDeposit = availiableDeposit;
    }

    public double getWithdrewed() {
        return withdrewed;
    }

    public void setWithdrewed(double withdrewed) {
        this.withdrewed = withdrewed;
    }

    public double getWithdrewing() {
        return withdrewing;
    }

    public void setWithdrewing(double withdrewing) {
        this.withdrewing = withdrewing;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAgentProfit() {
        return agentProfit;
    }

    public void setAgentProfit(double agentProfit) {
        this.agentProfit = agentProfit;
    }

    public double getCompanyProfit() {
        return companyProfit;
    }

    public void setCompanyProfit(double companyProfit) {
        this.companyProfit = companyProfit;
    }

    public List<PlatformAnalyse> getPlatformAnalyseList() {
        return platformAnalyseList;
    }

    public void setPlatformAnalyseList(List<PlatformAnalyse> platformAnalyseList) {
        this.platformAnalyseList = platformAnalyseList;
    }

    public List<SuccessOrdersRate> getSuccessOrdersRateList() {
        return successOrdersRateList;
    }

    public void setSuccessOrdersRateList(List<SuccessOrdersRate> successOrdersRateList) {
        this.successOrdersRateList = successOrdersRateList;
    }

    public List<SuccessOrder> getSuccessOrderList() {
        return successOrderList;
    }

    public void setSuccessOrderList(List<SuccessOrder> successOrderList) {
        this.successOrderList = successOrderList;
    }

    public List<TotalOrder> getTotalOrderList() {
        return totalOrderList;
    }

    public void setTotalOrderList(List<TotalOrder> totalOrderList) {
        this.totalOrderList = totalOrderList;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
