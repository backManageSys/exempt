package njurestaurant.njutakeout.entity.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "avatarUrl")
    private String avatarUrl;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
//    @Column(name = "originPassword")
//    private String originPassword;
    @Column(name = "role")
    private int role;
    @Column(name = "tableId")
    private int tableId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalCard> cards;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    private List<Order> orders;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    private List<Food> foods;


    public User() {
    }

    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, int role, int tableId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.tableId = tableId;
    }

    public User(String username, String password, int role, List<PersonalCard> cards) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.cards = cards;
    }

//    public List<PersonalCardJsonResponse> getCards() {
//        List<PersonalCardJsonResponse> personalCardJsonResponses = new ArrayList<>();
//        if(cards.size() > 0) {
//            for(PersonalCard p : cards) {
//                PersonalCardJsonResponse personalCardJsonResponse = new PersonalCardJsonResponse(p.getId(), p.getCardNumber(), p.getName(), p.getBank(), p.getAccountWithBank(), p.getBin(), p.getStatus());
//                personalCardJsonResponses.add(personalCardJsonResponse);
//            }
//        }
//        return personalCardJsonResponses;
//    }

    public List<PersonalCard> getCards() {
        return cards;
    }

    public void setCards(List<PersonalCard> cards) {
        this.cards = cards;
    }


    //    public User(String avatarUrl, String username, String password, Role role, List<Order> orders, List<Food> foods) {
//        this.avatarUrl = avatarUrl;
//        this.username = username;
//        this.password = password;
//        this.role = role;
//        this.orders = orders;
//        this.foods = foods;
//    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//
//    public List<Food> getFoods() {
//        return foods;
//    }
//
//    public void setFoods(List<Food> foods) {
//        this.foods = foods;
//    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
