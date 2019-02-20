package njurestaurant.njutakeout.parameters.user;

public class AgentUpdateParameters {
    private String password;
    private String name;
    private double alipay;
    private double wechat;
    private String status;
    public AgentUpdateParameters(String name, String password, double alipay, double wechat, String status) {
        this.password = password;
        this.name = name;
        this.alipay = alipay;
        this.wechat = wechat;
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
