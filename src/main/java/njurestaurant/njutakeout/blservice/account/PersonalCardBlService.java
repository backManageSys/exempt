package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.user.PersonalCardAddParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonalCardBlService {
    /**
     * add a new personal bank card
     * @param personalCardAddParameters the info of the new bank card
     * @return
     */
    PersonalCardAddResponse addPersonalCard(PersonalCardAddParameters personalCardAddParameters) throws IsExistentException, WrongIdException;

    PersonalCard getPersonalCardById(int id);

    List<PersonalCard> getMyPersonalCardByUserId(int uid) throws WrongIdException;

    List<PersonalCard> getAllCards();
}
