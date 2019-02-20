package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class IPRiskControlException extends Exception {
    private WrongResponse response = new WrongResponse(12345, "IP risk control");

    public WrongResponse getResponse() {
        return response;
    }
}
