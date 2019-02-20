package njurestaurant.njutakeout.entity.food;

import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.port.Port;
import njurestaurant.njutakeout.publicdatas.food.FoodState;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "foodState")
    private FoodState foodState;

    @Column(name = "hasChoice")
    @Type(type = "yes_no")
    private boolean hasChoice;

    @Column(name = "choice")
    private String[] choice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "port_id")
    private Port port;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Food() {
    }


    public Food(String name, String url, double price, String description, FoodState foodState, boolean hasChoice, String[] choice, Port port, User user) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.description = description;
        this.foodState = foodState;
        this.hasChoice = hasChoice;
        this.choice = choice;
        this.port = port;
        this.user = user;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FoodState getFoodState() {
        return foodState;
    }

    public void setFoodState(FoodState foodState) {
        this.foodState = foodState;
    }

    public boolean isHasChoice() {
        return hasChoice;
    }

    public void setHasChoice(boolean hasChoice) {
        this.hasChoice = hasChoice;
    }

    public String[] getChoice() {
        return choice;
    }

    public void setChoice(String[] choice) {
        this.choice = choice;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
