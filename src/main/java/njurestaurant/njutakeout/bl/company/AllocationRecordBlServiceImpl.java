package njurestaurant.njutakeout.bl.company;

import njurestaurant.njutakeout.blservice.company.AllocationRecordBlService;
import njurestaurant.njutakeout.dataservice.company.AllocationRecordDataService;
import njurestaurant.njutakeout.entity.company.AllocationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllocationRecordBlServiceImpl implements AllocationRecordBlService {
    private final AllocationRecordDataService allocationRecordDataService;

    @Autowired
    public AllocationRecordBlServiceImpl(AllocationRecordDataService allocationRecordDataService) {
        this.allocationRecordDataService = allocationRecordDataService;
    }

    /**
     * add a record of operation
     *
     * @param allocationRecord
     */
    @Override
    public void addAllocationRecordBlService(AllocationRecord allocationRecord) {
        allocationRecordDataService.saveAllocationRecord(allocationRecord);
    }
}
