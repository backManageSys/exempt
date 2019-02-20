package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /*支付宝点位*/
    @Column(name = "alipay_TPASS")
    private double alipay_TPASS;
    @Column(name = "alipay_TSOLID")
    private double alipay_TSOLID;
    @Column(name = "alipay_RPASSOFF")
    private double alipay_RPASSOFF;
    @Column(name = "alipay_RPASSQR")
    private double alipay_RPASSQR;
    @Column(name = "alipay_RSOLID")
    private double alipay_RSOLID;
    /*微信点位*/
    @Column(name = "wechat")
    private double wechat;
    @Column(name = "balance")
    private double balance;
    @Column(name = "status")
    private String status;
    @Column(name = "verifyCode")
    private String verifyCode;
    @Column(name = "addTime")
    private Date addTime;
    @Column(name = "name")
    private String name;
    /*操作上级id*/
    @Column(name = "apply_id")
    private int applyId;
    /*审批人id*/
    @Column(name = "approver_id")
    private int approverId;
    @Column(name = "approvalTime")
    private Date approvalTime;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "priority")
    private int priority;
    @Column(name = "withdrewMoney")
    private double withdrewMoney;

    public Merchant() {
    }

    public Merchant(double alipay_TPASS, double alipay_TSOLID, double alipay_RPASSOFF, double alipay_RPASSQR, double alipay_RSOLID, double wechat, double balance, String status, Date addTime, String name, int applyId, User user, int priority) {
        this.alipay_TPASS = alipay_TPASS;
        this.alipay_TSOLID = alipay_TSOLID;
        this.alipay_RPASSOFF = alipay_RPASSOFF;
        this.alipay_RPASSQR = alipay_RPASSQR;
        this.alipay_RSOLID = alipay_RSOLID;
        this.wechat = wechat;
        this.balance = balance;
        this.status = status;
        this.addTime = addTime;
        this.name = name;
        this.applyId = applyId;
        this.user = user;
        this.priority = priority;
    }


    public Merchant(double alipay_TPASS, double alipay_TSOLID, double alipay_RPASSOFF, double alipay_RPASSQR, double alipay_RSOLID, double wechat, double balance, String status, Date addTime, String name, int approverId, Date approvalTime, User user, int priority) {
        this.alipay_TPASS = alipay_TPASS;
        this.alipay_TSOLID = alipay_TSOLID;
        this.alipay_RPASSOFF = alipay_RPASSOFF;
        this.alipay_RPASSQR = alipay_RPASSQR;
        this.alipay_RSOLID = alipay_RSOLID;
        this.wechat = wechat;
        this.balance = balance;
        this.status = status;
        this.addTime = addTime;
        this.name = name;
        this.approverId = approverId;
        this.approvalTime = approvalTime;
        this.user = user;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getApproverId() {
        return approverId;
    }

    public void setApproverId(int approverId) {
        this.approverId = approverId;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getWithdrewMoney() {
        return withdrewMoney;
    }

    public void setWithdrewMoney(double withdrewMoney) {
        this.withdrewMoney = withdrewMoney;
    }
}
