package njurestaurant.njutakeout.entity.app;

import njurestaurant.njutakeout.entity.account.Supplier;

import javax.persistence.*;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /*设备唯一标识*/
    @Column(name = "imei")
    private String imei;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    /*是否在该设备上在线，1为在线，0为离线*/
    @Column(name = "online")
    private int online;
    /*支付宝表id*/
    @Column(name = "alipayId")
    private int alipayId;
    @Column(name = "status")
    private String status;

    public Device() {
    }

    public Device(String imei) {
        this.imei = imei;
    }

    public Device(String imei, Supplier supplier, String status) {
        this.imei = imei;
        this.supplier = supplier;
        this.status = status;
    }

    public Device(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(int alipayId) {
        this.alipayId = alipayId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
