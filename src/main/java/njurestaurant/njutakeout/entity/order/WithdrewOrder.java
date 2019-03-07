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
    @Column(name = "card_out")
    private String card_out;
    @Column(name = "card_in")
    private String  card_in;
    @Column(name = "money_out")
    private double money_out;
    @Column(name = "money_in")
    private double money_in;
    @Column(name = "card_out_balance")
    private double card_out_balance;
    @Column(name = "balance")
    private double balance;
    @Column(name = "state")
    private WithdrewState state;
    @Column(name = "applyTime")
    private Date applyTime;
    @Column(name = "operateTime")
    private Date operateTime;
    @Column(name = "revokeTime")
    private Date revokeTime;
    @Column(name = "operateId")
    private int operateId;
    @Column(name = "memo")
    private String memo;
    @Column(name = "changeId")
    private int changeId;
    @Transient
    private String  applicantUsername;
    @Transient
    private String operateUsername;
    @Transient
    private Date startDate;
    @Transient
    private Date endDate;
    @Transient
    private String withdrewStateSql;

    public WithdrewOrder() {
    }

    public WithdrewOrder(String number, int applicantId, String type, String card_in, double money_out, double balance, WithdrewState state, Date applyTime) {
        this.number = number;
        this.applicantId = applicantId;
        this.type = type;
        this.card_in = card_in;
        this.money_out = money_out;
        this.balance = balance;
        this.state = state;
        this.applyTime = applyTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getChangeId() {
        return changeId;
    }

    public void setChangeId(int changeId) {
        this.changeId = changeId;
    }

    public String getApplicantUsername() {
        return applicantUsername;
    }

    public void setApplicantUsername(String applicantUsername) {
        this.applicantUsername = applicantUsername;
    }

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername;
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

    public String getCard_out() {
        return card_out;
    }

    public void setCard_out(String card_out) {
        this.card_out = card_out;
    }

    public String getCard_in() {
        return card_in;
    }

    public void setCard_in(String card_in) {
        this.card_in = card_in;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMoney_out() {
        return money_out;
    }

    public void setMoney_out(double money_out) {
        this.money_out = money_out;
    }

    public double getMoney_in() {
        return money_in;
    }

    public void setMoney_in(double money_in) {
        this.money_in = money_in;
    }

    public double getCard_out_balance() {
        return card_out_balance;
    }

    public void setCard_out_balance(double card_out_balance) {
        this.card_out_balance = card_out_balance;
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

    public Date getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) {
        this.revokeTime = revokeTime;
    }

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWithdrewStateSql() {
        return withdrewStateSql;
    }

    public void setWithdrewStateSql(String withdrewStateSql) {
        this.withdrewStateSql = withdrewStateSql;
    }
}
