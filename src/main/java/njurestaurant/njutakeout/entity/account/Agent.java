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
    /*代理商余额*/
    @Column(name = "balance")
    private double balance;
    /*正在提现的金额*/
    @Column(name = "withdrewMoney")
    private double withdrewMoney;
    @Column(name = "applyId")
    private int applyId;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Agent() {
    }

    public Agent(String agentName, String status, double balance, double withdrewMoney, int applyId, User user) {
        this.agentName = agentName;
        this.status = status;
        this.balance = balance;
        this.withdrewMoney = withdrewMoney;
        this.applyId = applyId;
        this.user = user;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWithdrewMoney() {
        return withdrewMoney;
    }

    public void setWithdrewMoney(double withdrewMoney) {
        this.withdrewMoney = withdrewMoney;
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
