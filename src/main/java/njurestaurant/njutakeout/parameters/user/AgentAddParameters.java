package njurestaurant.njutakeout.parameters.user;

import java.util.List;

public class AgentAddParameters {
    private String username;
    private String password;
    private String status;
    private int applyId;
    private List<PayRateAddParameters> list;

    public AgentAddParameters(String username, String password, String status, int applyId, List<PayRateAddParameters> list) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.applyId = applyId;
        this.list = list;
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

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }

    public List<PayRateAddParameters> getList() {
        return list;
    }

    public void setList(List<PayRateAddParameters> list) {
        this.list = list;
    }
}
