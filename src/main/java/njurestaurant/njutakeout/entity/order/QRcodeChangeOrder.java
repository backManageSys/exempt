package njurestaurant.njutakeout.entity.order;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "QRcodeChangeOrder")
public class QRcodeChangeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //支付宝账号
    @Column(name = "login_id")
    private String loginId;
    //提现金额
    @Column(name = "money")
    private double money;
    //到卡金额
    @Column(name = "real_money")
    private double realMoney;
    //账变前余额
    @Column(name = "balance")
    private double balance;
    //卡号
    @Column(name = "cardNumber")
    private String cardNumber;
    //卡上余额
    @Column(name = "card_balance")
    private double cardBalance;
    //状态
    @Column(name = "state")
    private String state;
    //操作时间
    @Column(name = "operateTime")
    private Date operateTime;
    //操作人
    @Column(name = "operateUsername")
    private String operateUsername;

    public QRcodeChangeOrder() {
    }

    public QRcodeChangeOrder(String loginId, double money, double realMoney, double balance, String cardNumber, double cardBalance, String state, Date operateTime, String operateUsername) {
        this.loginId = loginId;
        this.money = money;
        this.realMoney = realMoney;
        this.balance = balance;
        this.cardNumber = cardNumber;
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(double realMoney) {
        this.realMoney = realMoney;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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
