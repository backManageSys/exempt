package njurestaurant.njutakeout.parameters.user;

public class AdminUpdateParameters {
    private String password;
    private String team;
    private String post;
    private String status;

    public AdminUpdateParameters(String password, String team, String post, String status) {
        this.password = password;
        this.team = team;
        this.post = post;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
