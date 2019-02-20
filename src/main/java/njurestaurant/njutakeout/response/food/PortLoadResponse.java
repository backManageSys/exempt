package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class PortLoadResponse extends Response {
    private List<SimplePortItem> simplePortItemList;

    public PortLoadResponse() {
    }

    public PortLoadResponse(List<SimplePortItem> simplePortItemList) {
        this.simplePortItemList = simplePortItemList;
    }

    public List<SimplePortItem> getSimplePortItemList() {
        return simplePortItemList;
    }

    public void setSimplePortItemList(List<SimplePortItem> simplePortItemList) {
        this.simplePortItemList = simplePortItemList;
    }
}
