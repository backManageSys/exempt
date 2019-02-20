package njurestaurant.njutakeout.response.food;

import java.util.List;

public class PortItem {
    private String port;
    private int portId;
    private List<FoodItem> foods;

    public PortItem() {
    }

    public PortItem(String port, int portId, List<FoodItem> foods) {
        this.port = port;
        this.portId = portId;
        this.foods = foods;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public List<FoodItem> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodItem> foods) {
        this.foods = foods;
    }
}
