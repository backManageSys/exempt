package njurestaurant.njutakeout.entity.order;

import njurestaurant.njutakeout.publicdatas.order.WithdrewState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "withdrewOrder")
public class WithdrewOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number")
    private String number;
    @Column(name = "applicant_id")
    private int applicantId;
    @Column(name = "type")
    private String type;
    @Column(name = "money")
    private double money;
    @Column(name = "balance")
    private double balance;
    @Column(name = "state")
    private WithdrewState state;
    @Column(name = "applyTime")
    private Date applyTime;
    @Column(name = "operateTime")
    private Date operateTime;
    @Column(name = "operateId")
    private int operateId;
    @Column(name = "memo")
    private String memo;
    @Column(name = "cardId")
    private int cardId;

    public WithdrewOrder() {
    }

    public WithdrewOrder(String number, int applicantId, String type, double money, double balance, WithdrewState state, Date applyTime, int cardId) {
        this.number = number;
        this.applicantId = applicantId;
        this.type = type;
        this.money = money;
        this.balance = balance;
        this.state = state;
        this.applyTime = applyTime;
        this.cardId = cardId;
    }

    public WithdrewOrder(WithdrewState state, Date operateTime, int operateId, String memo) {
        this.state = state;
        this.operateTime = operateTime;
        this.operateId = operateId;
        this.memo = memo;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public WithdrewState getState() {
        return state;
    }

    public void setState(WithdrewState state) {
        this.state = state;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }
}
