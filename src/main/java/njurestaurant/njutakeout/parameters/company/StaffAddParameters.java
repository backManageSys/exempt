package njurestaurant.njutakeout.parameters.company;

import java.util.Date;

public class StaffAddParameters {
    private String team;
    private String post;
    private String username;
    private String password;
    private String status;
    private String code;
    private String operator;

    public StaffAddParameters(String team, String post, String username, String password, String status, String code, String operator) {
        this.team = team;
        this.post = post;
        this.username = username;
        this.password = password;
        this.status = status;
        this.code = code;
        this.operator = operator;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
