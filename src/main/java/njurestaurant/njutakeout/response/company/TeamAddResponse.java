package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.response.Response;

public class TeamAddResponse extends Response {
    private int teamId;

    public TeamAddResponse() {
    }

    public TeamAddResponse(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
