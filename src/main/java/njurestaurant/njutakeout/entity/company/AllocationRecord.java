package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限操作记录表
 */
@Entity
@Table(name = "allocationRecord")
public class AllocationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "post")
    private String post;
    @Column(name = "permission")
    private String permission;
    @Column(name = "date")
    private Date date;

    public AllocationRecord(String post, String permission, Date date) {
        this.post = post;
        this.permission = permission;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
