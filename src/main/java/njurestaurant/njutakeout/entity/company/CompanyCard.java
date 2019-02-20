package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;

@Entity
@Table(name = "companyCard")
public class CompanyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "bank")
    private String bank;
    @Column(name = "cardNumber")
    private String cardNumber;
    @Column(name = "balance")
    private double balance;
    @Column(name = "attribution")
    private String attribution;
    @Column(name = "relation")
    private String relation;
    @Column(name = "status")
    private String status;

    public CompanyCard() {
    }

    public CompanyCard(String name, String bank, String cardNumber, double balance, String attribution, String relation, String status) {
        this.name = name;
        this.bank = bank;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.attribution = attribution;
        this.relation = relation;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
