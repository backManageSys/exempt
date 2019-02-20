package njurestaurant.njutakeout.response.report;

import njurestaurant.njutakeout.response.Response;

/**
 * 资金报表（公司内部）
 * 编号number、日期（xxxx-xx-xx ~xxxx-xx-xx）data1,data2、供码用户的个人银行卡转入到公司银行卡的金额、
 * 公司银行卡转出到代理商个人银行卡的金额、公司银行卡转出到商户个人银行卡的金额
 */
public class FundingReportResponse extends Response {
    private String number;
    private String date;
    private double supplierToCom;
    private double comToAgent;
    private double comToMerchant;

    public FundingReportResponse(String number, String date, double supplierToCom, double comToAgent, double comToMerchant) {
        this.number = number;
        this.date = date;
        this.supplierToCom = supplierToCom;
        this.comToAgent = comToAgent;
        this.comToMerchant = comToMerchant;
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

    public double getSupplierToCom() {
        return supplierToCom;
    }

    public void setSupplierToCom(double supplierToCom) {
        this.supplierToCom = supplierToCom;
    }

    public double getComToAgent() {
        return comToAgent;
    }

    public void setComToAgent(double comToAgent) {
        this.comToAgent = comToAgent;
    }

    public double getComToMerchant() {
        return comToMerchant;
    }

    public void setComToMerchant(double comToMerchant) {
        this.comToMerchant = comToMerchant;
    }
}
