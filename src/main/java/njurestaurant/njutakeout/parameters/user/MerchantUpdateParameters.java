package njurestaurant.njutakeout.parameters.user;

public class MerchantUpdateParameters {

    private String name;
    private String password;
    private double alipay_TPASS;
    private double alipay_TSOLID;
    private double alipay_RPASSOFF;
    private double alipay_RPASSQR;
    private double alipay_RSOLID;
    private double wechat;
    private String status;
    private int level;

    public MerchantUpdateParameters(String password, String name, double alipay_TPASS, double alipay_TSOLID, double alipay_RPASSOFF, double alipay_RPASSQR, double alipay_RSOLID, double wechat, String status, int level) {
        this.password = password;
        this.name = name;
        this.alipay_TPASS = alipay_TPASS;
        this.alipay_TSOLID = alipay_TSOLID;
        this.alipay_RPASSOFF = alipay_RPASSOFF;
        this.alipay_RPASSQR = alipay_RPASSQR;
        this.alipay_RSOLID = alipay_RSOLID;
        this.wechat = wechat;
        this.status = status;
        this.level = level;
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

    public double getAlipay_TPASS() {
        return alipay_TPASS;
    }

    public void setAlipay_TPASS(double alipay_TPASS) {
        this.alipay_TPASS = alipay_TPASS;
    }

    public double getAlipay_TSOLID() {
        return alipay_TSOLID;
    }

    public void setAlipay_TSOLID(double alipay_TSOLID) {
        this.alipay_TSOLID = alipay_TSOLID;
    }

    public double getAlipay_RPASSOFF() {
        return alipay_RPASSOFF;
    }

    public void setAlipay_RPASSOFF(double alipay_RPASSOFF) {
        this.alipay_RPASSOFF = alipay_RPASSOFF;
    }

    public double getAlipay_RPASSQR() {
        return alipay_RPASSQR;
    }

    public void setAlipay_RPASSQR(double alipay_RPASSQR) {
        this.alipay_RPASSQR = alipay_RPASSQR;
    }

    public double getAlipay_RSOLID() {
        return alipay_RSOLID;
    }

    public void setAlipay_RSOLID(double alipay_RSOLID) {
        this.alipay_RSOLID = alipay_RSOLID;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
