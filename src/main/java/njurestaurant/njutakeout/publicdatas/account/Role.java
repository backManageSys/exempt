package njurestaurant.njutakeout.publicdatas.account;

import javax.persistence.Embeddable;

@Embeddable
public class Role {
    public final static String USER_NAME = "ROLE_USER";
    public final static String RESTAURANT_NAME = "ROLE_RESTAURANT";
    public final static String ADMIN_NAME = "ROLE_ADMIN";
    public final static Role USER = new Role(USER_NAME);
    public final static Role ADMIN = new Role(ADMIN_NAME);
    public final static Role RESTAURANT = new Role(RESTAURANT_NAME);

    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
