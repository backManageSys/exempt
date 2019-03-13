package njurestaurant.njutakeout.parameters.user;

public class UserPasswordParameters {
    private Integer uid;
    private String password;
    public UserPasswordParameters(Integer uid, String password) {
        this.uid = uid;
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
