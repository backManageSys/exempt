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
    //到账后转入卡卡上余额
    @Column(name = "card_balance")
    private double cardBalance;
    //审核状态(未审核、已审核)
    @Column(name = "state")
    private WithdrewState state;
    //操作时间
    @Column(name = "operateTime")
    private Date operateTime;
    //操作人
    @Column(name = "operateUsername")
    private String operateUsername;

    public CardChangeOrder() {
    }

    public CardChangeOrder(String cardNumber_out, String cardNumber_in, double money_out, double money_in, double cardBalance, WithdrewState state, Date operateTime, String operateUsername) {
        this.cardNumber_out = cardNumber_out;
        this.cardNumber_in = cardNumber_in;
        this.money_out = money_out;
        this.money_in = money_in;
        this.cardBalance = cardBalance;
        this.state = state;
        this.operateTime = operateTime;
        this.operateUsername = operateUsername;
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

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
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

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername;
    }
}
