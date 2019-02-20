package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;

@Entity
@Table(name = "agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "agentName")
    private String agentName;
    @Column(name = "status")
    private String status;
    /*代理商手续费*/
    @Column(name = "alipay")
    private double alipay;
    @Column(name = "wechat")
    private double wechat;
    /*代理商余额*/
    @Column(name = "balance")
    private double balance;
    /*正在提现的金额*/
    @Column(name = "withdrewMoney")
    private double withdrewMoney;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Agent() {
    }

    public Agent(String agentName, String status, double alipay, double wechat, double balance, double withdrewMoney, User user) {
        this.agentName = agentName;
        this.status = status;
        this.alipay = alipay;
        this.wechat = wechat;
        this.balance = balance;
        this.withdrewMoney = withdrewMoney;
        this.user = user;
    }

    public double getWithdrewMoney() {
        return withdrewMoney;
    }

    public void setWithdrewMoney(double withdrewMoney) {
        this.withdrewMoney = withdrewMoney;
    }

    public double getAlipay() {
        return alipay;
    }

    public void setAlipay(double alipay) {
        this.alipay = alipay;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
