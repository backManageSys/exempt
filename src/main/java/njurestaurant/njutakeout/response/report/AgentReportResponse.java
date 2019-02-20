package njurestaurant.njutakeout.response.report;

import njurestaurant.njutakeout.response.Response;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 代理商报表
 * 系统编号， 日期，代理账户
 * 分润点位（详细的分红点位，支付宝/微信），存款r（名下所有商户，所有成功订单总金额。细分支付宝/微信）
 * 分润r(名下所有商户，所有成功订单总金额*分润点位，分支付宝/微信)，提款r（名下所有商户，提现成功的总金额，显示实现段内）
 * 余额（名下所有商户未提现的总金额）
 */
public class AgentReportResponse extends Response {
    private String number;
    private String date;
    private String username;
    private String agentName;
    private double alipay;
    private double wechat;
    private List<PlatformAnalyse> depositList;
    private List<PlatformAnalyse>  profitList;
    private double withdrewed;
    private double balance;

    public AgentReportResponse(String number, String date, String username, String agentName, double alipay, double wechat, List<PlatformAnalyse> depositList, List<PlatformAnalyse> profitList, double withdrewed, double balance) {
        this.number = number;
        this.date = date;
        this.username = username;
        this.agentName = agentName;
        this.alipay = alipay;
        this.wechat = wechat;
        this.depositList = depositList;
        this.profitList = profitList;
        this.withdrewed = withdrewed;
        this.balance = balance;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getAlipay() {
        return alipay;
    }

    public void setAlipay(double alipay) {
        this.alipay = alipay;
    }

    public double getWechat() {
        return wechat;
    }

    public void setWechat(double wechat) {
        this.wechat = wechat;
    }

    public List<PlatformAnalyse> getDepositList() {
        return depositList;
    }

    public void setDepositList(List<PlatformAnalyse> depositList) {
        this.depositList = depositList;
    }

    public List<PlatformAnalyse> getProfitList() {
        return profitList;
    }

    public void setProfitList(List<PlatformAnalyse> profitList) {
        this.profitList = profitList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public double getWithdrewed() {
        return withdrewed;
    }

    public void setWithdrewed(double withdrewed) {
        this.withdrewed = withdrewed;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
