package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bulletin")
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Date time;
    @Column(name = "publisher_id")
    private int publisherId;
    @Column(name = "content")
    private String content;

    public Bulletin(Date time, int publisherId, String content) {
        this.time = time;
        this.publisherId = publisherId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
