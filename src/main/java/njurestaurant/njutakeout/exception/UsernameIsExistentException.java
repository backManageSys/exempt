package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class UsernameIsExistentException extends Exception {
    private WrongResponse response = new WrongResponse(10100, "用户名存在.");

    public WrongResponse getResponse() {
        return response;
    }
}
