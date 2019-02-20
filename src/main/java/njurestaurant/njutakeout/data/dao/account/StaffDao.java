package njurestaurant.njutakeout.data.dao.account;

import njurestaurant.njutakeout.entity.account.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDao extends JpaRepository<Staff, Integer> {
    Staff findByStaffName(String staffName);
    Staff findByUserId(int id);
}
