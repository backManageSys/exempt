package njurestaurant.njutakeout.data.company;

import njurestaurant.njutakeout.data.dao.company.AllocationRecordDao;
import njurestaurant.njutakeout.dataservice.company.AllocationRecordDataService;
import njurestaurant.njutakeout.entity.company.AllocationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllocationRecordDataServiceImpl implements AllocationRecordDataService {
    private final AllocationRecordDao allocationRecordDao;

    @Autowired
    public AllocationRecordDataServiceImpl(AllocationRecordDao allocationRecordDao) {
        this.allocationRecordDao = allocationRecordDao;
    }

    /**
     * record the operation of allocation
     * @param allocationRecord the record of allocation
     * @return
     */
    @Override
    public AllocationRecord saveAllocationRecord(AllocationRecord allocationRecord) {
        return allocationRecordDao.save(allocationRecord);
    }
}
