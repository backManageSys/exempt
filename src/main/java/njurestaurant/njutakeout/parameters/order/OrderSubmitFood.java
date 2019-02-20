package njurestaurant.njutakeout.parameters.order;

public class OrderSubmitFood {
    private int foodId;
    private String specialChoice;

    public OrderSubmitFood() {
    }

    public OrderSubmitFood(int foodId, String specialChoice) {
        this.foodId = foodId;
        this.specialChoice = specialChoice;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getSpecialChoice() {
        return specialChoice;
    }

    public void setSpecialChoice(String specialChoice) {
        this.specialChoice = specialChoice;
    }
}
