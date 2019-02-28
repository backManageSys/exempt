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
    private String cardNumber;
    private String bank;
    private String name;
    private String status;
    private double in;
    private double out;
    private double balance;

    public FundingReportResponse(String number, String date, String cardNumber, String bank, String name, String status, double in, double out, double balance) {
        this.number = number;
        this.date = date;
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.name = name;
        this.status = status;
        this.in = in;
        this.out = out;
        this.balance = balance;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getIn() {
        return in;
    }

    public void setIn(double in) {
        this.in = in;
    }

    public double getOut() {
        return out;
    }

    public void setOut(double out) {
        this.out = out;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
