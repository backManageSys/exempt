package njurestaurant.njutakeout.parameters.company;

public class MerchantApprovalParameters {
    private String username;
    private String password;
    private double alipay;
    private double wechat;
    private int approverId;
    private int level;
    private int status;

    public MerchantApprovalParameters(String username, String password, double alipay, double wechat, int approverId, int level, int status) {
        this.username = username;
        this.password = password;
        this.alipay = alipay;
        this.wechat = wechat;
        this.approverId = approverId;
        this.level = level;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
