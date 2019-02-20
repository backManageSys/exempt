package njurestaurant.njutakeout.response.food;

public class SimplePortItem {
    private int id;
    private String portName;

    public SimplePortItem() {
    }

    public SimplePortItem(int id, String portName) {
        this.id = id;
        this.portName = portName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
