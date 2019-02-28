package njurestaurant.njutakeout.response.report;

/**
 * 团队报表（就是供码用户报表）
 * 编号number、日期（xxxx-xx-xx ~xxxx-xx-xx）data1,data2、供码用户名supplierName、
 * 该供码用户所有支付宝的实收账款、该供码用户所有支付宝提现到自己绑定的个人银行卡的金额
 */
public class SupplierReportResponse {
    private String number;
    private String date;
    private String supplierName;
    private double realReceipt;
    private double withdrew;
    private double withdrewing;
    private double fee;

    public SupplierReportResponse(String number, String date, String supplierName, double realReceipt, double withdrew, double withdrewing, double fee) {
        this.number = number;
        this.date = date;
        this.supplierName = supplierName;
        this.realReceipt = realReceipt;
        this.withdrew = withdrew;
        this.withdrewing = withdrewing;
        this.fee = fee;
    }

    public double getWithdrewing() {
        return withdrewing;
    }

    public void setWithdrewing(double withdrewing) {
        this.withdrewing = withdrewing;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public double getRealReceipt() {
        return realReceipt;
    }

    public void setRealReceipt(double realReceipt) {
        this.realReceipt = realReceipt;
    }

    public double getWithdrew() {
        return withdrew;
    }

    public void setWithdrew(double withdrew) {
        this.withdrew = withdrew;
    }
}
