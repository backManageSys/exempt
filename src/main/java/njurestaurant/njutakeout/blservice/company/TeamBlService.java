package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.TeamVerifyCodeWrongException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.company.TeamAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.company.TeamAddResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamBlService {
    /**
     * add a new team
     *
     * @param teamAddParameters params of the new team
     * @return
     */
    TeamAddResponse addTeam(TeamAddParameters teamAddParameters) throws IsExistentException, BlankInputException;

    /**
     * load all teams
     * @return
     */
    List<Team> loadAllTeam();

    Response delTeamById(int id, String verifyCode) throws WrongIdException, TeamVerifyCodeWrongException;

    TeamAddResponse updateTeam(TeamAddParameters teamAddParameters, int id) throws WrongIdException, IsExistentException;

    Response verifyTeamCode(int id, String verifyCode) throws WrongIdException, TeamVerifyCodeWrongException;

    Response verifyTeamCodeByTeamName(String teamName, String verifyCode) throws WrongIdException, TeamVerifyCodeWrongException;
}
