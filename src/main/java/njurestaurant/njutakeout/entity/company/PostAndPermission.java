package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;

/**
 * 岗位权限记录
 */
@Entity
@Table(name = "postAndPermission")
public class PostAndPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "post")
    private String post;
    @Column(name = "permission")
    private String permission;

    public PostAndPermission() {
    }

    public PostAndPermission(String post, String permission) {
        this.post = post;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
