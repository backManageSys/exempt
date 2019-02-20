package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class UserDeleteResponse extends Response {
    private int userId;
    private int tableId;

    public UserDeleteResponse() {
    }

    public UserDeleteResponse(int userId, int tableId) {
        this.userId = userId;
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
