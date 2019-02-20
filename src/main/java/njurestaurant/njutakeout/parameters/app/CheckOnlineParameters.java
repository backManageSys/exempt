package njurestaurant.njutakeout.parameters.app;

/**
 * 发送获取二维码的消息给设备
 * 服务端消息:{"cmd":"passcode","type":"alipay","imei":"设备唯一标识","userid":"支付宝userid"}
 */
public class CheckOnlineParameters {
    private String cmd = "passcode";
    private String type = "alipay";
    private String imei;
    private String userid;
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
            return "{" +
                    "cmd='" + cmd + '\'' +
                    ", type='" + type + '\'' +
                    ", imei='" + imei + '\'' +
                    ", userid='" + userid + '\'' +
                    '}';
        
    }

    public CheckOnlineParameters(String imei, String userid) {
        this.imei = imei;
        this.userid = userid;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
