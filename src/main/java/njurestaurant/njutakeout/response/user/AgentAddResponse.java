package njurestaurant.njutakeout.response.user;

import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.response.Response;

public class AgentAddResponse extends Response {
    private int agentId;

    public AgentAddResponse(int agentId) {
        this.agentId = agentId;
    }

    public AgentAddResponse() {
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
