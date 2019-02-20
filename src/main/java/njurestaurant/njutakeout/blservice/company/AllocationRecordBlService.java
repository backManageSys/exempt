package njurestaurant.njutakeout.blservice.company;

import njurestaurant.njutakeout.entity.company.AllocationRecord;

public interface AllocationRecordBlService {
    /**
     * add a record of operation
     *
     * @param allocationRecord
     */
    void addAllocationRecordBlService(AllocationRecord allocationRecord);
}
