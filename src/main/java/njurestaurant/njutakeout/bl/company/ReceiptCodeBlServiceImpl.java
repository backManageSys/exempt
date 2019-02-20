package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.ReceiptCodeBlService;
import njurestaurant.njutakeout.dataservice.company.ReceiptCodeDataService;
import njurestaurant.njutakeout.dataservice.company.TeamDataService;
import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.entity.company.Team;
import njurestaurant.njutakeout.exception.IsExistentException;
import njurestaurant.njutakeout.response.company.ReceiptCodeAddResponse;
import njurestaurant.njutakeout.response.company.ReceiptCodeLoadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReceiptCodeBlServiceImpl implements ReceiptCodeBlService {

    private final ReceiptCodeDataService receiptCodeDataService;
    private final TeamDataService teamDataService;

    @Autowired
    public ReceiptCodeBlServiceImpl(ReceiptCodeDataService receiptCodeDataService, TeamDataService teamDataService) {
        this.receiptCodeDataService = receiptCodeDataService;
        this.teamDataService = teamDataService;
    }

    /**
     * save the new receipt code
     *
     * @param receiptCode
     * @return
     */
    @Override
    public ReceiptCode addReceiptCode(ReceiptCode receiptCode) {
        return receiptCodeDataService.saveReceiptCode(receiptCode);
    }

    /**
     * load all receipt code
     *
     * @return
     */
    @Override
    public List<ReceiptCodeLoadResponse> loadReceiptCodes() {
        List<ReceiptCode> receiptCodes = receiptCodeDataService.findAllReceipt();
        List<Team> teams = teamDataService.findAllTeams();
        Map<Integer, String> teamIdMap = new HashMap<>();
        for(Team t : teams) {
            teamIdMap.put(t.getId(), t.getTeamName());
        }
        List<ReceiptCodeLoadResponse> result = new ArrayList<>();
        for(ReceiptCode r : receiptCodes) {
            if(checkStringIsInt(r.getTeamId()) && teamIdMap.containsKey(Integer.valueOf(r.getTeamId()))) {
                ReceiptCodeLoadResponse receiptCodeLoadResponse = new ReceiptCodeLoadResponse(r.getId(), teamIdMap.get(Integer.valueOf(r.getTeamId())), r.getTeamId(), r.getType(), r.getDuration(), r.getPriorityLevel(), r.getAccountInfo(), r.getAccountNumber());
                result.add(receiptCodeLoadResponse);
            }
        }
        return result;
    }

    @Override
    public void delReceiptCode(int id) {
        receiptCodeDataService.deleteReceiptById(id);
    }

    @Override
    public ReceiptCodeLoadResponse findReceiptCodeById(int id) {
        ReceiptCode receiptCode =  receiptCodeDataService.findReceiptCodeById(id);
        List<Team> teams = teamDataService.findAllTeams();
        for(Team t : teams) {
            if(t.getId() == Integer.valueOf(receiptCode.getTeamId())) {
                return new ReceiptCodeLoadResponse(receiptCode.getId(), t.getTeamName(), receiptCode.getTeamId(), receiptCode.getType(), receiptCode.getDuration(), receiptCode.getPriorityLevel(), receiptCode.getAccountInfo(), receiptCode.getAccountNumber());
            }
        }
        return null;
    }

    private boolean checkStringIsInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
