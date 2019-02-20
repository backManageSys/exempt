package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.response.Response;

public class AdminAddResponse extends Response {
    private int tableId;
    private int role;

    public AdminAddResponse(int tableId, int role) {
        this.tableId = tableId;
        this.role = role;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
