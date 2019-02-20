package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class StaffAddResponse extends Response {
    private int staffId;

    public StaffAddResponse(int staffId) {
        this.staffId = staffId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
}
