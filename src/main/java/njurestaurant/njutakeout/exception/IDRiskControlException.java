package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class IDRiskControlException extends Exception {
    private WrongResponse response = new WrongResponse(54321, "ID Anti scratch list");

    public WrongResponse getResponse() {
        return response;
    }
}
