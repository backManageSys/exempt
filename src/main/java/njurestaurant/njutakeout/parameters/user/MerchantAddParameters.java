package njurestaurant.njutakeout.parameters.user;

public class MerchantAddParameters {
    private String username;
    private String password;
    private double alipay_TPASS;
    private double alipay_TSOLID;
    private double alipay_RPASSOFF;
    private double alipay_RPASSQR;
    private double alipay_RSOLID;
    private double alipay_RedEnvelope;
    private double wechat;
    private int applyId;
    private int level;
    private String status;

    public MerchantAddParameters() {
    }
    public MerchantAddParameters(String username, String password, double alipay_TPASS, double alipay_TSOLID, double alipay_RPASSOFF, double alipay_RPASSQR, double alipay_RSOLID, double alipay_redEnvelope, double wechat, int applyId, int level, String status) {
        this.username = username;
        this.password = password;
        this.alipay_TPASS = alipay_TPASS;
        this.alipay_TSOLID = alipay_TSOLID;
        this.alipay_RPASSOFF = alipay_RPASSOFF;
        this.alipay_RPASSQR = alipay_RPASSQR;
        this.alipay_RSOLID = alipay_RSOLID;
        this.alipay_RedEnvelope = alipay_redEnvelope;
        this.wechat = wechat;
        this.applyId = applyId;
        this.level = level;
        this.status = status;
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

    public double getAlipay_RedEnvelope() {
        return alipay_RedEnvelope;
    }

    public void setAlipay_RedEnvelope(double alipay_RedEnvelope) {
        this.alipay_RedEnvelope = alipay_RedEnvelope;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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


    public double getWechat() {
        return wechat;
    }

    public void setWechat(double wechat) {
        this.wechat = wechat;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}