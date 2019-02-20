package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class TeamVerifyCodeWrongException extends Exception {
    private WrongResponse response = new WrongResponse(10200, "团队验证码错误.");

    public WrongResponse getResponse() {
        return response;
    }
}
