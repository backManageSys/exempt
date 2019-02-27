package njurestaurant.njutakeout.parameters.app;

public class AddPersonalCard {
   private String cardNumber;
    private double cardBalance;

    public AddPersonalCard(String cardNumber, double cardBalance) {
        this.cardNumber = cardNumber;
        this.cardBalance = cardBalance;
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
}
