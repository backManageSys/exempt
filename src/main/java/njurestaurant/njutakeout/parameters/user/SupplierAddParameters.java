package njurestaurant.njutakeout.parameters.user;

public class SupplierAddParameters {
    private int id; // 申请人id
    private String username;
    private String password;
    private int level;
    private String status;

    public SupplierAddParameters(String username, String password, int level, String status) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

