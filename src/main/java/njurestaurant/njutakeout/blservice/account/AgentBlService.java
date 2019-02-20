package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.response.user.AgentAddResponse;
import njurestaurant.njutakeout.response.user.AgentInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentBlService {
    /**
     * add a new agent
     * @param agent the info of the agent
     * @return
     */
    AgentAddResponse addAgent(Agent agent);

    /**
     * delete the agent by id
     * @param id the agent id
     */
    void delAgentById(int id);

    List<AgentInfoResponse> findAllAgents();
}
