package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.SupplierBlService;
import njurestaurant.njutakeout.data.dao.account.SupplierDao;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.company.SupplierApprovalParameters;
import njurestaurant.njutakeout.parameters.user.SupplierUpdateParameters;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.UserAddResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierBlServiceImpl implements SupplierBlService {
    private final UserDataService userDataService;
    private final SupplierDataService supplierDataService;
    private final UserDao userDao;
    private final SupplierDao supplierDao;

    @Autowired
    public SupplierBlServiceImpl(UserDataService userDataService, SupplierDataService supplierDataService, UserDao userDao, SupplierDao supplierDao) {
        this.userDataService = userDataService;
        this.supplierDataService = supplierDataService;
        this.userDao = userDao;
        this.supplierDao = supplierDao;
    }

    @Value(value = "${spring.encrypt.publicKey}")
    private String publicKey;

    @Value(value = "${spring.encrypt.privateKey}")
    private String privateKey;

    /**
     * @param supplier the info of supplier
     * @return
     */
    @Override
    public UserAddResponse addSupplier(Supplier supplier) throws UsernameIsExistentException {
        if (userDataService.isUserExistent(supplier.getUser().getUsername())) {
            throw new UsernameIsExistentException();
        } else {
            return new UserAddResponse(supplierDataService.saveSupplier(supplier).getId());
        }
    }

    @Override
    public Response approvalSupplier(int sid, SupplierApprovalParameters supplierApprovalParameters) {
        Supplier supplier = supplierDataService.findSupplierById(sid);
        if (supplier == null) {
            return new WrongResponse(10130, "Wrong id.");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = supplier.getUser();
//            user.setUsername(supplierApprovalParameters.getUsername());
//            user.setPassword(encoder.encode(supplierApprovalParameters.getPassword()));
            supplier.setUser(user);
            supplier.setPriority(supplierApprovalParameters.getLevel());
            supplier.setApprovalTime(new Date());
            supplier.setApproverId(supplierApprovalParameters.getId());
            if (supplierApprovalParameters.getState() == 1) {
                supplier.setStatus("启用");
            } else if (supplierApprovalParameters.getState() == 0) {
                supplier.setStatus("停用");
            } else {
                return new WrongResponse(10140, "Wrong state");
            }
            supplierDataService.saveSupplier(supplier);
            return new SuccessResponse("Update success.");
        }
    }

    @Override
    public void delSupplierById(int id) {
        supplierDataService.deleteSupplierById(id);
    }

    @Override
    public List<Supplier> findAllSuppliers() {
        List<Supplier> supplierList = JSONFilter(supplierDataService.getAllSuppliers());
        return supplierList;
    }

    @Override
    public List<Supplier> findSupplierByState(String status) {
        List<Supplier> supplierList = JSONFilter(supplierDataService.findSuppliersByState(status));
        return supplierList;
    }

    private List<Supplier> JSONFilter(List<Supplier> suppliers) {
        if (suppliers.size() != 0) {
            for (Supplier supplier : suppliers) {
                List<PersonalCard> cardList = supplier.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
                List<Device> devices = supplier.getDevices();
                devices.stream().peek(d -> d.setSupplier(null)).collect(Collectors.toList());
                User user = supplier.getUser();
//                if(user != null) {
//                    if(StringUtils.isNotBlank(user.getOriginPassword()))    user.setOriginPassword(RSAUtils.decryptDataOnJava(user.getOriginPassword(), privateKey));
//                    else user.setOriginPassword("");
//                }
            }
        }
        return suppliers;
    }


    @Override
    public Supplier updateSupplier(int id, SupplierUpdateParameters supplierUpdateParameters) throws WrongIdException, BlankInputException, UsernameIsExistentException {
        Supplier supplier = supplierDao.findByUserId(id);
        if (supplier == null) {
            throw new WrongIdException();
        } else if (StringUtils.isBlank(supplierUpdateParameters.getPassword()) || StringUtils.isBlank(supplierUpdateParameters.getCodeType()) || supplierUpdateParameters.getLevel() < 0) {
            throw new BlankInputException();
        } else {
            User user = userDao.findUserById(id);
            if (!user.getUsername().equals(supplierUpdateParameters.getName())) {
                if (userDao.findUserByUsername(supplierUpdateParameters.getName()) != null)
                    throw new UsernameIsExistentException();
                else {

//            user.setOriginPassword(RSAUtils.encryptedDataOnJava(supplierUpdateParameters.getPassword(), publicKey));
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            if(!supplierUpdateParameters.getPassword().equals(user.getPassword()))
//                user.setPassword(encoder.encode(supplierUpdateParameters.getPassword()));
//            supplier.setPriority(supplierUpdateParameters.getLevel());

                user.setUsername(supplierUpdateParameters.getName());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(supplierUpdateParameters.getPassword()));
                userDataService.saveUser(user);
                supplier.setUser(user);
                supplier.setPriority(supplierUpdateParameters.getLevel());
                supplier.setStatus(supplierUpdateParameters.getStatus());
                switch (supplierUpdateParameters.getCodeType()) {
                    case "TSOLID":
                        supplier.setCodeType(CodeType.TSOLID);
                        break;
                    case "TPASS":
                        supplier.setCodeType(CodeType.TPASS);
                        break;
                    case "RSOLID":
                        supplier.setCodeType(CodeType.RSOLID);
                        break;
                    case "RPASSOFF":
                        supplier.setCodeType(CodeType.RPASSOFF);
                        break;
                    case "RPASSQR":
                        supplier.setCodeType(CodeType.RPASSQR);
                        break;
                    default:
                        throw new BlankInputException();
                }
                return supplierDataService.saveSupplier(supplier);
            }
        }else {
                //            user.setOriginPassword(RSAUtils.encryptedDataOnJava(supplierUpdateParameters.getPassword(), publicKey));
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            if(!supplierUpdateParameters.getPassword().equals(user.getPassword()))
//                user.setPassword(encoder.encode(supplierUpdateParameters.getPassword()));
//            supplier.setPriority(supplierUpdateParameters.getLevel());

                user.setUsername(supplierUpdateParameters.getName());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(supplierUpdateParameters.getPassword()));
                userDataService.saveUser(user);
                supplier.setUser(user);
                supplier.setPriority(supplierUpdateParameters.getLevel());
                supplier.setStatus(supplierUpdateParameters.getStatus());
                switch (supplierUpdateParameters.getCodeType()) {
                    case "TSOLID":
                        supplier.setCodeType(CodeType.TSOLID);
                        break;
                    case "TPASS":
                        supplier.setCodeType(CodeType.TPASS);
                        break;
                    case "RSOLID":
                        supplier.setCodeType(CodeType.RSOLID);
                        break;
                    case "RPASSOFF":
                        supplier.setCodeType(CodeType.RPASSOFF);
                        break;
                    case "RPASSQR":
                        supplier.setCodeType(CodeType.RPASSQR);
                        break;
                    default:
                        throw new BlankInputException();
                }
                return supplierDataService.saveSupplier(supplier);
            }
        }
    }
}
