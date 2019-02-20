package njurestaurant.njutakeout.data.dao.company;

import njurestaurant.njutakeout.entity.company.AllocationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllocationRecordDao extends JpaRepository<AllocationRecord, String> {
}
