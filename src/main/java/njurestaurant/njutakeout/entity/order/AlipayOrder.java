package njurestaurant.njutakeout.entity.order;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alipay_order")
public class AlipayOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "imei")
    private String imei;
    /*支付宝订单号*/
    @Column(name = "orderId")
    private String orderId;
    @Column(name = "money")
    private double money;
    @Column(name = "memo")
    private String memo;
    @Column(name = "time")
    private Date time;

    public AlipayOrder() {
    }

    public AlipayOrder(String imei, String orderId, double money, String memo, Date time) {
        this.imei = imei;
        this.orderId = orderId;
        this.money = money;
        this.memo = memo;
        this.time = time;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
