package njurestaurant.njutakeout.response.order;

import njurestaurant.njutakeout.response.Response;

public class AlipayOrderAddResponse extends Response {
    private String cmd;
    private String status;
    private String msg;
    private String orderId;
    private String imei;

    public AlipayOrderAddResponse(String cmd, String status, String msg, String orderId, String imei) {
        this.cmd = cmd;
        this.status = status;
        this.msg = msg;
        this.orderId = orderId;
        this.imei = imei;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
