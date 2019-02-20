package njurestaurant.njutakeout.response.food;

import njurestaurant.njutakeout.response.Response;

public class PortDeleteResponse extends Response {
    private int portId;

    public PortDeleteResponse() {
    }

    public PortDeleteResponse(int portId) {
        this.portId = portId;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }
}
