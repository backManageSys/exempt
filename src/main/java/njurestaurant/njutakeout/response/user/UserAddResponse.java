package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class UserAddResponse extends Response {

    private int tableId;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public UserAddResponse(int tableId) {
        this.tableId = tableId;
    }
}
