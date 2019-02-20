package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.AgentDao;
import njurestaurant.njutakeout.dataservice.account.AgentDataService;
import njurestaurant.njutakeout.entity.account.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentDataServiceImpl implements AgentDataService {

    private final AgentDao agentDao;

    @Autowired
    public AgentDataServiceImpl(AgentDao agentDao) {
        this.agentDao = agentDao;
    }

    /**
     * save a agent
     *
     * @param agent the information of agent
     */
    @Override
    public Agent saveAgent(Agent agent) {
        return agentDao.save(agent);
    }

    /**
     *
     * @param id the id of agent
     * @return
     */
    @Override
    public void deleteAgentById(int id) {
        agentDao.deleteById(id);
    }

    @Override
    public Agent findAgentById(int id) {
        Optional<Agent> optionalAgent = agentDao.findById(id);
        if(optionalAgent.isPresent()) {
            return optionalAgent.get();
        }
        return null;
    }

    @Override
    public boolean isAgentExistentByName(String name) {
        return agentDao.findAgentByAgentName(name) != null;
    }

    @Override
    public List<Agent> getAllAgent() {
        return agentDao.findAll();
    }
}
