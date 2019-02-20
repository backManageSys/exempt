package njurestaurant.njutakeout.parameters.user;

public class PersonalCardAddParameters {
    private int uid;
    private String number;
    private String name;
    private String bank;
    private String accountOfBank;
    private String bin;
    private String status;

    public PersonalCardAddParameters() {
    }

    public PersonalCardAddParameters(int uid, String number, String name, String bank, String accountOfBank, String bin, String status) {
        this.uid = uid;
        this.number = number;
        this.name = name;
        this.bank = bank;
        this.accountOfBank = accountOfBank;
        this.bin = bin;
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getAccountOfBank() {
        return accountOfBank;
    }

    public void setAccountOfBank(String accountOfBank) {
        this.accountOfBank = accountOfBank;
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
