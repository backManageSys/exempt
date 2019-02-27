package njurestaurant.njutakeout.parameters.user;

import java.util.List;

public class MerchantUpdateParameters {
    private String password;
    private String status;
    private int level;
    private List<PayRateAddParameters> list;

    public MerchantUpdateParameters(String name, String password, String status, int level, List<PayRateAddParameters> list) {
        this.password = password;
        this.status = status;
        this.level = level;
        this.list = list;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<PayRateAddParameters> getList() {
        return list;
    }

    public void setList(List<PayRateAddParameters> list) {
        this.list = list;
    }
}
