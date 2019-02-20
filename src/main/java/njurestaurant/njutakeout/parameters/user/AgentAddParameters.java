package njurestaurant.njutakeout.parameters.user;

public class AgentAddParameters {
    private String username;
    private String password;
    private String status;
    private double alipay;
    private double wechat;

    public AgentAddParameters(String username, String password, String status, double alipay, double wechat) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.alipay = alipay;
        this.wechat = wechat;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
