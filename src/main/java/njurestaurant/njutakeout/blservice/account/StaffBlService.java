package njurestaurant.njutakeout.blservice.account;

import njurestaurant.njutakeout.entity.account.Staff;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface StaffBlService {
//    /**
//     * login
//     *
//     * @param staffName the username of staff
//     * @param password the password of staff
//     * @return the login info to  response
//     * @throws WrongUsernameOrPasswordException the username or password is error
//     */
//    StaffLoginReponse login(String staffName, String password) throws WrongUsernameOrPasswordException, CannotRegisterException;

    Staff addStaff(Staff staff);

    void delStaffById(int id);

    List<Staff> findAllStaffs();
}
