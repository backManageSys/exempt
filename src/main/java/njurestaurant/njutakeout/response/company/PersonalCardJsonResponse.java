package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class PersonalCardJsonResponse extends Response {
    private int id;
    private String cardNumber;
    private String name;
    private String bank;
    private String accountWithBank;
    private String bin;
    private String status;

    public PersonalCardJsonResponse() {
    }

    public PersonalCardJsonResponse(int id, String cardNumber, String name, String bank, String accountWithBank, String bin, String status) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.name = name;
        this.bank = bank;
        this.accountWithBank = accountWithBank;
        this.bin = bin;
        this.status = status;
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
}
