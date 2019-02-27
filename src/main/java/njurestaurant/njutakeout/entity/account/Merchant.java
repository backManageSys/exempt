package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "balance")
    private double balance;
    @Column(name = "status")
    private String status;
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

    public Merchant(double balance, String status, Date addTime, String name, int applyId, User user, int priority, double withdrewMoney) {
        this.balance = balance;
        this.status = status;
        this.addTime = addTime;
        this.name = name;
        this.applyId = applyId;
        this.user = user;
        this.priority = priority;
        this.withdrewMoney = withdrewMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
