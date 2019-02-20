package njurestaurant.njutakeout.response.app;

import org.apache.commons.lang.StringUtils;

// 入库成功服务端发送:{"cmd":"validation","status":"success","err":"msg","imei":"设备唯一标识"}
public class DeviceUpdateResponse {
    private String cmd = "validation";
    private String status = "success";
    private String err;
    private String imei;
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "{" +
                "cmd='" + cmd + '\'' +
                ", status='" + status + '\'' +
                ", err='" + err + '\'' +
                ", imei='" + imei + '\'' +
                '}';

    }
    public DeviceUpdateResponse(String err, String imei) {
        this.err = err;
        this.imei = imei;
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

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
