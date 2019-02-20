package njurestaurant.njutakeout.exception;

import njurestaurant.njutakeout.response.WrongResponse;

public class RoleIdentityNotConformException extends Exception {
    private WrongResponse response = new WrongResponse(10009, "Role identity does not conform.");

    public WrongResponse getResponse() {
        return response;
    }
}
