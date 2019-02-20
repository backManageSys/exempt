package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.TeamBlService;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.TeamVerifyCodeWrongException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TeamBlServiceImpl implements TeamBlService {
    private final TeamDataService teamDataService;

    @Autowired
    public TeamBlServiceImpl(TeamDataService teamDataService) {
        this.teamDataService = teamDataService;
    }

    /**
     *
     * @param teamAddParameters params of the new team
     * @return
     */
    @Override
    public TeamAddResponse addTeam(TeamAddParameters teamAddParameters) throws IsExistentException, BlankInputException {
        if(StringUtils.isBlank(teamAddParameters.getTeamName())) {
            throw new BlankInputException();
        } else if(!teamDataService.isExistentTeamName(teamAddParameters.getTeamName())) {
            Team team =  new Team(teamAddParameters.getSupervisor(), teamAddParameters.getArea(), teamAddParameters.getStatus(), teamAddParameters.getVerifyCode(), teamAddParameters.getOperator(), new Date(), teamAddParameters.getTeamName());
            return new TeamAddResponse(teamDataService.saveTeam(team).getId());
        } else {
            throw new IsExistentException();
        }
    }

    /**
     * load all teams
     *
     * @return
     */
    @Override
    public List<Team> loadAllTeam() {
        return teamDataService.findAllTeams();
    }

    @Override
    public Response delTeamById(int id, String verifyCode) throws WrongIdException, TeamVerifyCodeWrongException {
        Team team = teamDataService.findTeamById(id);
        if(team == null) throw new WrongIdException();
        if(team.getVerifyCode().equals(verifyCode)) {
            teamDataService.deleteTeamById(id);
            return new SuccessResponse("delete success.");
        } else {
            throw new TeamVerifyCodeWrongException();
        }
    }

    @Override
    public TeamAddResponse updateTeam(TeamAddParameters teamAddParameters, int id) throws WrongIdException, IsExistentException {
        if(teamDataService.isExistentTeamName(teamAddParameters.getTeamName())) {
            throw new IsExistentException();
        } else if(teamDataService.findTeamById(id) == null) {
            throw new WrongIdException();
        } else {
            Team team =  new Team(teamAddParameters.getSupervisor(), teamAddParameters.getArea(), teamAddParameters.getStatus(), teamAddParameters.getVerifyCode(), teamAddParameters.getOperator(), new Date(), teamAddParameters.getTeamName());
            team.setId(id);
            return new TeamAddResponse(teamDataService.saveTeam(team).getId());
        }
    }

    @Override
    public Response verifyTeamCode(int id, String verifyCode) throws WrongIdException, TeamVerifyCodeWrongException {
        Team team = teamDataService.findTeamById(id);
        if(team == null) {
            throw new WrongIdException();
        } else {
            if(team.getVerifyCode().equals(verifyCode)) {
                return new TeamAddResponse(team.getId());
            } else {
                throw new TeamVerifyCodeWrongException();
            }
        }
    }

    @Override
    public Response verifyTeamCodeByTeamName(String teamName, String verifyCode) throws WrongIdException, TeamVerifyCodeWrongException {
        Team team = teamDataService.findTamByTeamName(teamName);
        if(team == null) {
            throw new WrongIdException();
        } else {
            if(team.getVerifyCode().equals(verifyCode)) {
                return new TeamAddResponse(team.getId());
            } else {
                throw new TeamVerifyCodeWrongException();
            }
        }
    }
}
