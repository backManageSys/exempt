package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.StaffBlService;
import njurestaurant.njutakeout.dataservice.account.StaffDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.Staff;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.security.jwt.JwtService;
import njurestaurant.njutakeout.util.RSAUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffBlServiceImpl implements StaffBlService {

    private final static long EXPIRATION = 604800;

    private final StaffDataService staffDataService;
    private final JwtService jwtService;

    @Autowired
    public StaffBlServiceImpl(StaffDataService staffDataService,  JwtService jwtService) {
        this.staffDataService = staffDataService;
        this.jwtService = jwtService;
    }

    @Value(value = "${spring.encrypt.privateKey}")
    private String privateKey;

    @Value(value = "${spring.encrypt.publicKey}")
    private String publicKey;

//    /**
//     * login
//     *
//     * @param staffName the username of staff
//     * @param password  the password of staff
//     * @return the login info to response
//     * @throws WrongUsernameOrPasswordException the username or password is error
//     */
//    @Override
//    public StaffLoginReponse login(String staffName, String password) throws WrongUsernameOrPasswordException, CannotRegisterException {
//        if (staffName.length() == 0) {
//            throw new CannotRegisterException();
//        }
//
//        if (staffDataService.confirmPassword(staffName, password)) {
//            JwtUser jwtUser = (JwtUser) jwtStaffDetailService.loadUserByUsername(staffName);
//            String token = jwtService.generateToken(jwtUser, EXPIRATION);
//            return new StaffLoginReponse(token);
//        } else {
//            throw new WrongUsernameOrPasswordException();
//        }
//    }

    /**
     * add a new staff
     *
     * @param staff the new staff
     * @return
     */
    @Override
    public Staff addStaff(Staff staff) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return staffDataService.saveStaff(new Staff(nickname, team, addTime, verifyCode, operator, status, role));
          return staffDataService.saveStaff(staff);
//        JwtUser jwtUser = (JwtUser) JwtUserDetailsService.loadUserByUsername();
//        String token = jwtService.generateToken(jwtUser, EXPIRATION);
//        return new StaffLoginReponse(token);
    }

    @Override
    public void delStaffById(int id) {
        staffDataService.deleteStaffById(id);
    }

    @Override
    public List<Staff> findAllStaffs() {
        List<Staff> staffList = JSONFilter(staffDataService.getAllStaffs());
//        staffList = staffList.stream().peek(s -> {
//            User user = s.getUser();
//            if(StringUtils.isNotBlank(user.getOriginPassword()))
//                user.setOriginPassword(RSAUtils.decryptDataOnJava(user.getOriginPassword(), privateKey));
//            else user.setOriginPassword("");
//        }).collect(Collectors.toList());
        return staffList;
    }

    private List<Staff> JSONFilter(List<Staff> staffList) {
        if(staffList.size() != 0) {
            for(Staff staff : staffList) {
                List<PersonalCard> cardList = staff.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
            }
        }
        return staffList;
    }

}
