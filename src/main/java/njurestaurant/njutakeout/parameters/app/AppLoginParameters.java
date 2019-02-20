package njurestaurant.njutakeout.parameters.app;

public class AppLoginParameters {
    private String username;
    private String password;
    private String imei;

    public AppLoginParameters(String username, String password, String imei) {
        this.username = username;
        this.password = password;
        this.imei = imei;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
