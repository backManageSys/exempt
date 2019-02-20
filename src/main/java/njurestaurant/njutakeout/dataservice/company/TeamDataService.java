package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.Team;

import java.util.List;

public interface TeamDataService {
    /**
     * save the team
     *
     * @param team
     * @return
     */
    Team saveTeam(Team team);

    /**
     * find all teams
     *
     * @return
     */
    List<Team> findAllTeams();

    /**
     * find the team name whether existent
     *
     * @param teamName the team name
     * @return
     */
    boolean isExistentTeamName(String teamName);

    void deleteTeamById(int id);

    Team findTeamById(int id);

    Team findTamByTeamName(String teamName);
}
