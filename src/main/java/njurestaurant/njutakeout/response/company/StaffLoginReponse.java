package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class StaffLoginReponse extends Response {
    private String token;

    public StaffLoginReponse() {
    }

    public StaffLoginReponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
