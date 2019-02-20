package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.TeamDao;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.entity.company.Team;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamDataServiceImpl implements TeamDataService {
    private final TeamDao teamDao;

    @Autowired
    public TeamDataServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public Team saveTeam(Team team) {
        return teamDao.save(team);
    }

    /**
     * find the team name whether existent
     *
     * @param teamName the team name
     * @return
     */
    @Override
    public boolean isExistentTeamName(String teamName) {
        return teamDao.findTeamByTeamName(teamName) != null;
    }

    /**
     * find all teams
     *
     * @return
     */
    @Override
    public List<Team> findAllTeams() {
        return teamDao.findAll();
    }

    @Override
    public void deleteTeamById(int id) {
        teamDao.deleteById(id);
    }

    @Override
    public Team findTeamById(int id) {
        Optional<Team> optionalTeam = teamDao.findById(id);
        if(optionalTeam.isPresent()) return optionalTeam.get();
        else return null;
    }

    @Override
    public Team findTamByTeamName(String teamName) {
        return teamDao.findTeamByTeamName(teamName);
    }
}
