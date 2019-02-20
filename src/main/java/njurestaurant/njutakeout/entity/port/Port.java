package njurestaurant.njutakeout.entity.port;

import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.publicdatas.port.PortState;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "port")
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "portState")
    private PortState portState;

    @OneToMany(mappedBy = "port", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Food> foods;

    public Port() {
    }

    public Port(String name, PortState portState, List<Food> foods) {
        this.name = name;
        this.portState = portState;
        this.foods = foods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PortState getPortState() {
        return portState;
    }

    public void setPortState(PortState portState) {
        this.portState = portState;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
