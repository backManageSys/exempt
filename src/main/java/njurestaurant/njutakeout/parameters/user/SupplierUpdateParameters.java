package njurestaurant.njutakeout.parameters.user;

public class SupplierUpdateParameters {
    private int uid;
    private String password;
    private int level;
    private int payTypeId;
    private String status;

    public SupplierUpdateParameters(int uid, String password, int level, int payTypeId, String status) {
        this.uid = uid;
        this.password = password;
        this.level = level;
        this.payTypeId = payTypeId;
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public int getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
