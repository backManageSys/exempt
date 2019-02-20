package njurestaurant.njutakeout.parameters.company;

import java.util.List;

public class PermissionsAllocationParameters {
    private String post;
    private List<String> permissions;

    public PermissionsAllocationParameters(String post, List<String> permissions) {
        this.post = post;
        this.permissions = permissions;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
