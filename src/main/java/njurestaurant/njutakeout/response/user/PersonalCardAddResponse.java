package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class PersonalCardAddResponse extends Response {
    private int cardId;

    public PersonalCardAddResponse(int cardId) {
        this.cardId = cardId;
    }

    public PersonalCardAddResponse() {
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
