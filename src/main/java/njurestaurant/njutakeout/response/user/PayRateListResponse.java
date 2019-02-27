package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class PayRateListResponse extends Response {
    private int id;
    private int uid;
    private String codeCategory;
    private String codeType;
    private double rate;
    private String status;

    public PayRateListResponse(int id, int uid, String codeCategory, String codeType, double rate, String status) {
        this.id = id;
        this.uid = uid;
        this.codeCategory = codeCategory;
        this.codeType = codeType;
        this.rate = rate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCodeCategory() {
        return codeCategory;
    }

    public void setCodeCategory(String codeCategory) {
        this.codeCategory = codeCategory;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
