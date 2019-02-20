package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.PersonalCardBlService;
import njurestaurant.njutakeout.dataservice.account.PersonalCardDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.user.PersonalCardAddParameters;
import njurestaurant.njutakeout.response.user.PersonalCardAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalCardBlServiceImpl implements PersonalCardBlService {
    private final PersonalCardDataService personalCardDataService;
    private final UserDataService userDataService;

    @Autowired
    public PersonalCardBlServiceImpl(PersonalCardDataService personalCardDataService, UserDataService userDataService) {
        this.personalCardDataService = personalCardDataService;
        this.userDataService = userDataService;
    }

    @Override
    public PersonalCardAddResponse addPersonalCard(PersonalCardAddParameters personalCardAddParameters) throws WrongIdException, IsExistentException {
        User user = userDataService.getUserById(personalCardAddParameters.getUid());
        if(user == null || user.getId() == 0) {
            throw new WrongIdException();
        }
        if(personalCardDataService.findPersonalCardByAccountWithBank(personalCardAddParameters.getAccountOfBank()) != null) {
            throw new IsExistentException();
        }
        PersonalCard tmp = personalCardDataService.savePersonalCard(new PersonalCard(personalCardAddParameters.getNumber(),
                personalCardAddParameters.getName(), personalCardAddParameters.getBank(),personalCardAddParameters.getAccountOfBank(),
                personalCardAddParameters.getBin(), 0.0, personalCardAddParameters.getStatus(), new Date(), user));
        return new PersonalCardAddResponse(tmp.getId());
    }

    @Override
    public PersonalCard getPersonalCardById(int id) {
        PersonalCard personalCard = personalCardDataService.findPersonalCardById(id);
        User user = personalCard.getUser();
        user.setPassword(null);
        user.setCards(null);
        personalCard.setUser(user);
        return personalCard;
    }

    @Override
    public List<PersonalCard> getMyPersonalCardByUserId(int uid) throws WrongIdException {
        User user = userDataService.getUserById(uid);
        if(user == null || user.getId() == 0) {
            throw new WrongIdException();
        }
        List<PersonalCard> personalCardList = personalCardDataService.findPersonalCarsByUid(uid);
        return personalCardList.stream().peek(p -> p.setUser(null)).collect(Collectors.toList());
    }

    @Override
    public List<PersonalCard> getAllCards() {
        List<PersonalCard> personalCardList = JSONFilter(personalCardDataService.findAllCards());
        return personalCardList;
    }

    private List<PersonalCard> JSONFilter(List<PersonalCard> personalCards) {
        personalCards.stream().peek(p -> {
            User u = p.getUser();
            u.setPassword(null);
            u.setCards(new ArrayList<>());
            p.setUser(u);
        }).collect(Collectors.toList());
        return personalCards;
    }
}
