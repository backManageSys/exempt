package njurestaurant.njutakeout.dataservice.account;

import njurestaurant.njutakeout.entity.account.Agent;

import java.util.List;

public interface AgentDataService {
    /**
     * save a agent
     *
     * @param agent the information of agent
     */
    Agent saveAgent(Agent agent);

    /**
     * find the agent whether exist
     * @param name account name of agent
     */
    boolean isAgentExistentByName(String name);

    /**
     * delete agent by id
     *
     * @param id the id of agent
     * @return
     */
    void deleteAgentById(int id);

    Agent findAgentById(int id);

    List<Agent> getAllAgent();
}
