package njurestaurant.njutakeout.parameters.user;

import java.util.List;

public class AgentUpdateParameters {
    private String password;
    private String status;

    public AgentUpdateParameters(String password, String status) {
        this.password = password;
        this.status = status;
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
