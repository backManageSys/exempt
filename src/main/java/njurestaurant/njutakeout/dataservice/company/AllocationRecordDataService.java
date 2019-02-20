package njurestaurant.njutakeout.dataservice.company;

import njurestaurant.njutakeout.entity.company.AllocationRecord;

public interface AllocationRecordDataService {
    /**
     * save the allocation record of permission
     *
     * @param allocationRecord the record of allocation
     * @return
     */
    AllocationRecord saveAllocationRecord(AllocationRecord allocationRecord);
}
