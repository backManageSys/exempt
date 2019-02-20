package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Agent;
import njurestaurant.njutakeout.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AgentDao extends JpaRepository<Agent, Integer> {
    Agent findAgentByAgentName(String name);
    Agent findByUserId(int id);
}
