package njurestaurant.njutakeout.response.report;

import njurestaurant.njutakeout.response.Response;

/**
 * 收款码报表(支付宝)
 * 编号number、日期（xxxx-xx-xx ~xxxx-xx-xx）data1,data2、供码用户名supplierName、支付宝账号、
 * 该支付宝号的实收账款、该支付宝提现到自己绑定的个人银行卡的金额
 */
public class ReceiptCodeReportResponse extends Response {
    private String number;
    private String date;
    private String supplierName;
    private String alipayLoginId;
    private double payMoney;
    private double withdrew;

    public ReceiptCodeReportResponse(String number, String date, String alipayLoginId) {
        this.number = number;
        this.date = date;
        this.alipayLoginId = alipayLoginId;
    }

    public ReceiptCodeReportResponse(String number, String date, String supplierName, String alipayLoginId, double payMoney, double withdrew) {
        this.number = number;
        this.date = date;
        this.supplierName = supplierName;
        this.alipayLoginId = alipayLoginId;
        this.payMoney = payMoney;
        this.withdrew = withdrew;
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

    public String getAlipayLoginId() {
        return alipayLoginId;
    }

    public void setAlipayLoginId(String alipayLoginId) {
        this.alipayLoginId = alipayLoginId;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public double getWithdrew() {
        return withdrew;
    }

    public void setWithdrew(double withdrew) {
        this.withdrew = withdrew;
    }
}
