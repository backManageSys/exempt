package njurestaurant.njutakeout.entity.order;


import njurestaurant.njutakeout.publicdatas.order.WithdrewState;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CardChangeOrder")
public class CardChangeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //转出卡号
    @Column(name = "cardNumber_out")
    private String cardNumber_out;
    //转入卡号
    @Column(name = "cardNumber_in")
    private String cardNumber_in;
    //转出金额
    @Column(name = "money_out")
    private double money_out;
    //到卡金额
    @Column(name = "money_in")
    private double money_in;
    //转出卡卡上余额
    @Column(name = "card_balance_out")
    private double cardBalanceOut;
    //转入卡卡上余额
    @Column(name = "card_balance_in")
    private double cardBalanceIn;
    //订单状态
    @Column(name = "state")
    private WithdrewState state;
    //操作时间
    @Column(name = "operateTime")
    private Date operateTime;
    //最终操作时间
    @Column(name = "finalOperateTime")
    private Date finalOperateTime;
    //操作人
    @Column(name = "operateUsername")
    private String operateUsername;
    //账变类型
    @Column(name = "type")
    private String type;
    //原因
    @Column(name = "reason")
    private String reason;
    @Transient
    private Date startDate;
    @Transient
    private Date endDate;
    @Transient
    private String withdrewStateSql;

    public CardChangeOrder() {
    }

    public CardChangeOrder(String cardNumber_out, String cardNumber_in, double money_out, double money_in, double cardBalanceOut, double cardBalanceIn, WithdrewState state, Date operateTime, Date finalOperateTime, String operateUsername, String type, String reason) {
        this.cardNumber_out = cardNumber_out;
        this.cardNumber_in = cardNumber_in;
        this.money_out = money_out;
        this.money_in = money_in;
        this.cardBalanceOut = cardBalanceOut;
        this.cardBalanceIn = cardBalanceIn;
        this.state = state;
        this.operateTime = operateTime;
        this.finalOperateTime = finalOperateTime;
        this.operateUsername = operateUsername;
        this.type = type;
        this.reason = reason;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber_out() {
        return cardNumber_out;
    }

    public void setCardNumber_out(String cardNumber_out) {
        this.cardNumber_out = cardNumber_out;
    }

    public String getCardNumber_in() {
        return cardNumber_in;
    }

    public void setCardNumber_in(String cardNumber_in) {
        this.cardNumber_in = cardNumber_in;
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

    public double getCardBalanceOut() {
        return cardBalanceOut;
    }

    public void setCardBalanceOut(double cardBalanceOut) {
        this.cardBalanceOut = cardBalanceOut;
    }

    public double getCardBalanceIn() {
        return cardBalanceIn;
    }

    public void setCardBalanceIn(double cardBalanceIn) {
        this.cardBalanceIn = cardBalanceIn;
    }

    public WithdrewState getState() {
        return state;
    }

    public void setState(WithdrewState state) {
        this.state = state;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Date getFinalOperateTime() {
        return finalOperateTime;
    }

    public void setFinalOperateTime(Date finalOperateTime) {
        this.finalOperateTime = finalOperateTime;
    }

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getWithdrewStateSql() {
        return withdrewStateSql;
    }

    public void setWithdrewStateSql(String withdrewStateSql) {
        this.withdrewStateSql = withdrewStateSql;
    }
}
