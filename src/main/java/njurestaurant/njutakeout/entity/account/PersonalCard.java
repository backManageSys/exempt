package njurestaurant.njutakeout.entity.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personal_card")

public class PersonalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cardNumber")
    private String cardNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "bank")
    private String bank;
    @Column(name = "accountWithBank")
    private String accountWithBank;
    @Column(name = "bin")
    private String bin;
    @Column(name = "cardBalance")
    private double cardBalance;
    @Column(name = "status")
    private String status;
    @Column(name="addTime")
    private Date addTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public PersonalCard() {
    }

    public PersonalCard(String cardNumber, String name, String bank, String accountWithBank, String bin, Double balance, String status, Date addTime, User user) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.bank = bank;
        this.accountWithBank = accountWithBank;
        this.bin = bin;
        this.addTime = addTime;
        this.cardBalance = cardBalance;
        this.status = status;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountWithBank() {
        return accountWithBank;
    }

    public void setAccountWithBank(String accountWithBank) {
        this.accountWithBank = accountWithBank;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
