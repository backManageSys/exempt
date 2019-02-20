package njurestaurant.njutakeout.response.app;

public class GetReceiptCodeResponse {
    private String cmd;
    private String type;
    private String imei;
    private String status;
    private String msg;
    private String userid;
    private String qrcode;
    private String offcode;

    public GetReceiptCodeResponse() {
    }

    public GetReceiptCodeResponse(String cmd, String type, String imei, String status, String msg, String userid, String qrcode, String offcode) {
        this.cmd = cmd;
        this.type = type;
        this.imei = imei;
        this.status = status;
        this.msg = msg;
        this.userid = userid;
        this.qrcode = qrcode;
        this.offcode = offcode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getOffcode() {
        return offcode;
    }

    public void setOffcode(String offcode) {
        this.offcode = offcode;
    }
}
