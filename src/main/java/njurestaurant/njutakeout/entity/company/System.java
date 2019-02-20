package njurestaurant.njutakeout.entity.company;

import javax.persistence.*;

@Entity
@Table(name = "system")
public class System {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;

    public System() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public System(String title) {
        this.title = title;
    }
}
