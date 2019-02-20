package njurestaurant.njutakeout.data.account;

import njurestaurant.njutakeout.data.dao.account.StaffDao;
import njurestaurant.njutakeout.dataservice.account.StaffDataService;
import njurestaurant.njutakeout.entity.account.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffDataServiceImpl implements StaffDataService {
    private final StaffDao staffDao;

    @Autowired
    public StaffDataServiceImpl(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    public boolean isStaffExistent(String staffName) {
        return staffDao.findByStaffName(staffName) != null;
    }

    /**
     * save the staff
     *
     * @param staff the staff to be saved
     */
    @Override
    public Staff saveStaff(Staff staff)  {
         return staffDao.save(staff);
    }

//    /**
//     * confirm the password of the staff
//     *
//     * @param staffName the username of staff
//     * @param password the password of staff
//     * @return true if password is correct else false
//     */
//    @Override
//    public boolean confirmPassword(String staffName, String password) {
//        Staff staff = staffDao.findByStaffName(staffName);
//        if(staff != null) {
//            if(BCrypt.checkpw(password, staff.getPassword())) {
//                return true;
//            }
//            return false;
//        } else {
//            return false;
//        }
//    }


    @Override
    public void deleteStaffById(int id) {
        staffDao.deleteById(id);
    }

    @Override
    public Staff findStaffById(int id) {
        Optional<Staff> optionalStaff = staffDao.findById(id);
        if(optionalStaff.isPresent()) {
            return optionalStaff.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Staff> getAllStaffs() {
        return staffDao.findAll();
    }
}
