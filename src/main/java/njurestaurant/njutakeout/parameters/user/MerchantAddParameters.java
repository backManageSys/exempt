package njurestaurant.njutakeout.parameters.user;

import java.util.List;

public class MerchantAddParameters {
    private String username;
    private String password;
    private int applyId;
    private int level;
    private String status;
    private List<PayRateAddParameters> list;

    public MerchantAddParameters() {
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

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PayRateAddParameters> getList() {
        return list;
    }

    public void setList(List<PayRateAddParameters> list) {
        this.list = list;
    }
}