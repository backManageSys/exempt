package njurestaurant.njutakeout.entity.account;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pay_rate_list")
public class PayRateList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "uid")
    private int uid;
    @Column(name = "payTypeId")
    private int payTypeId;
    @Column(name = "rate")
    private double rate;
    @Column(name = "status")
    private String status;

    public PayRateList() {
    }

    public PayRateList(int uid, int payTypeId, double rate, String status) {
        this.uid = uid;
        this.payTypeId = payTypeId;
        this.rate = rate;
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
