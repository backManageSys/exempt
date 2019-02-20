package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.entity.company.Permission;
import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class UserInfoResponse extends Response {
    private String post;
    private List<String> permissions;
    private Object info;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String post, List<String> permissions, Object info) {
        this.post = post;
        this.permissions = permissions;
        this.info = info;
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

    public void setPermission(List<String> permissions) {
        this.permissions = permissions;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
