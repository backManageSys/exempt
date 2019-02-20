package njurestaurant.njutakeout.entity.app;

import javax.persistence.*;

@Entity
@Table(name = "qrCode")
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cmd")
    private String cmd;
    @Column(name = "type")
    private String type;
    @Column(name = "imei")
    private String imei;
    @Column(name = "status")
    private String status;
    @Column(name = "userid")
    private String userid;
    @Column(name = "qrCode")
    private String qrCode;
    @Column(name = "offCode")
    private String offCode;
    @Column(name = "money")
    private double money;
    @Column(name = "memo")
    private String memo;

    public QRCode() {
    }

    public QRCode(String cmd, String type, String imei, String status, String userid, String qrCode, String offCode, double money, String memo) {
        this.cmd = cmd;
        this.type = type;
        this.imei = imei;
        this.status = status;
        this.userid = userid;
        this.qrCode = qrCode;
        this.offCode = offCode;
        this.money = money;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getOffCode() {
        return offCode;
    }

    public void setOffCode(String offCode) {
        this.offCode = offCode;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
