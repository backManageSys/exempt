package njurestaurant.njutakeout.parameters.order;

public class WithdrewDealParameters {
    private int operatorId;
    private String memo;
    private String state;
    private String companyCardId;

    public WithdrewDealParameters(int operatorId, String memo, String state, String companyCardId) {
        this.operatorId = operatorId;
        this.memo = memo;
        this.state = state;
        this.companyCardId = companyCardId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCompanyCardId() {
        return companyCardId;
    }

    public void setCompanyCardId(String companyCardId) {
        this.companyCardId = companyCardId;
    }
}
