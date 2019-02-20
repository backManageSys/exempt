package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class CompanyCardAddResponse extends Response {
    private int cardId;

    public CompanyCardAddResponse(int cardId) {
        this.cardId = cardId;
    }

    public CompanyCardAddResponse() {
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
