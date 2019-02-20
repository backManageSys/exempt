package njurestaurant.njutakeout.response.order;

public class OrderFoodItem {
    private int id;
    private String portName;
    private String name;
    private String specialChoice;
    private double price;

    public OrderFoodItem() {
    }

    public OrderFoodItem(int id, String portName, String name, String specialChoice, double price) {
        this.id = id;
        this.portName = portName;
        this.name = name;
        this.specialChoice = specialChoice;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialChoice() {
        return specialChoice;
    }

    public void setSpecialChoice(String specialChoice) {
        this.specialChoice = specialChoice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
