package njurestaurant.njutakeout.parameters.order;

import njurestaurant.njutakeout.publicdatas.order.WithdrewState;

public class WithdrewDealParameters {
    private int operatorId;
    private String memo;
    private WithdrewState state;
    private String companyCardId;

    public WithdrewDealParameters(int operatorId, String memo, WithdrewState state, String companyCardId) {
        this.operatorId = operatorId;
        this.memo = memo;
        this.state = state;
        this.companyCardId = companyCardId;
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

    public WithdrewState getState() {
        return state;
    }

    public void setState(WithdrewState state) {
        this.state = state;
    }

    public String getCompanyCardId() {
        return companyCardId;
    }

    public void setCompanyCardId(String companyCardId) {
        this.companyCardId = companyCardId;
    }
}
