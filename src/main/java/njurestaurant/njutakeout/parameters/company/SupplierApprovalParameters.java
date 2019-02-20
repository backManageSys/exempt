package njurestaurant.njutakeout.parameters.company;

public class SupplierApprovalParameters {
    private int id;/*审核人的userid*/
    private String username;
    private String password;
    private int level;
    private int state;

    public SupplierApprovalParameters(int id, String username, String password, int level, int state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
