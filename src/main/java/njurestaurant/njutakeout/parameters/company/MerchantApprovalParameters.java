package njurestaurant.njutakeout.parameters.company;

public class MerchantApprovalParameters {
    private String password;
    private int approverId;
    private int level;
    private int status;

    public MerchantApprovalParameters(String password, int approverId, int level, int status) {
        this.password = password;
        this.approverId = approverId;
        this.level = level;
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
