package njurestaurant.njutakeout.bl.account;

import njurestaurant.njutakeout.blservice.account.MerchantBlService;
import njurestaurant.njutakeout.data.dao.account.MerchantDao;
import njurestaurant.njutakeout.data.dao.account.UserDao;
import njurestaurant.njutakeout.dataservice.account.MerchantDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.entity.account.Merchant;
import njurestaurant.njutakeout.entity.account.PersonalCard;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.exception.UsernameIsExistentException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.parameters.company.MerchantApprovalParameters;
import njurestaurant.njutakeout.parameters.user.MerchantUpdateParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.user.MerchantAddResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantBlServiceImpl implements MerchantBlService {
    private final MerchantDataService merchantDataService;
    private final UserDataService userDataService;
    private final UserDao userDao;
    private final MerchantDao merchantDao;

    @Autowired
    public MerchantBlServiceImpl(MerchantDataService merchantDataService, UserDataService userDataService, UserDao userDao, MerchantDao merchantDao) {
        this.merchantDataService = merchantDataService;
        this.userDataService = userDataService;
        this.userDao = userDao;
        this.merchantDao = merchantDao;
    }

    @Value(value = "${spring.encrypt.publicKey}")
    private String publicKey;

    @Value(value = "${spring.encrypt.privateKey}")
    private String privateKey;

    /**
     * add a new merchant
     *
     * @param merchant the info of merchant
     * @return
     */
    @Override
    public MerchantAddResponse addMerchant(Merchant merchant) {
        return new MerchantAddResponse(merchantDataService.saveMerchant(merchant).getId());
    }

    /**
     * update the merchant
     *
     * @param merchantUpdateParameters the new info of merchant
     * @return
     */
    @Override
    public MerchantAddResponse updateMerchant(int id, MerchantUpdateParameters merchantUpdateParameters) throws WrongIdException, UsernameIsExistentException {
        Merchant merchant = merchantDao.findByUserId(id);
        if (merchant == null) {
            throw new WrongIdException();
        } else {
            User user = userDao.findUserById(id);
            if (!user.getUsername().equals(merchantUpdateParameters.getName())) {
                if (userDao.findUserByUsername(merchantUpdateParameters.getName()) != null)
                    throw new UsernameIsExistentException();
                else {

                    //           user.setOriginPassword(RSAUtils.encryptedDataOnJava(merchantUpdateParameters.getPassword(), publicKey));
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            if(!user.getPassword().equals(merchantUpdateParameters.getPassword()))
//                user.setPassword(encoder.encode(merchantUpdateParameters.getPassword()));
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    merchant.setName(merchantUpdateParameters.getName());
                    user.setUsername(merchantUpdateParameters.getName());
                    user.setPassword(encoder.encode(merchantUpdateParameters.getPassword()));
                    userDataService.saveUser(user);
                    merchant.setUser(user);
                    merchant.setStatus(merchantUpdateParameters.getStatus());
                    merchant.setPriority(merchantUpdateParameters.getLevel());
                    merchant.setAlipay_TPASS(merchantUpdateParameters.getAlipay_TPASS());
                    merchant.setAlipay_TSOLID(merchantUpdateParameters.getAlipay_TSOLID());
                    merchant.setAlipay_RPASSOFF(merchantUpdateParameters.getAlipay_RPASSOFF());
                    merchant.setAlipay_RPASSQR(merchantUpdateParameters.getAlipay_RPASSQR());
                    merchant.setAlipay_RSOLID(merchantUpdateParameters.getAlipay_RSOLID());
                    merchant.setWechat(merchantUpdateParameters.getWechat());
                    return new MerchantAddResponse(merchantDataService.saveMerchant(merchant).getUser().getId());
                }
            } else {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                merchant.setName(merchantUpdateParameters.getName());
                user.setUsername(merchantUpdateParameters.getName());
                user.setPassword(encoder.encode(merchantUpdateParameters.getPassword()));
                userDataService.saveUser(user);
                merchant.setUser(user);
                merchant.setStatus(merchantUpdateParameters.getStatus());
                merchant.setPriority(merchantUpdateParameters.getLevel());
                merchant.setAlipay_TPASS(merchantUpdateParameters.getAlipay_TPASS());
                merchant.setAlipay_TSOLID(merchantUpdateParameters.getAlipay_TSOLID());
                merchant.setAlipay_RPASSOFF(merchantUpdateParameters.getAlipay_RPASSOFF());
                merchant.setAlipay_RPASSQR(merchantUpdateParameters.getAlipay_RPASSQR());
                merchant.setAlipay_RSOLID(merchantUpdateParameters.getAlipay_RSOLID());
                merchant.setWechat(merchantUpdateParameters.getWechat());
                return new MerchantAddResponse(merchantDataService.saveMerchant(merchant).getUser().getId());
            }
        }
    }

    /**
     * @param id the merchant id
     * @return
     */
    @Override
    public Merchant selectMerchantById(int id) {
        return merchantDataService.findMerchantById(id);
    }

    @Override
    public Response ApprovalMerchant(int id, MerchantApprovalParameters merchantApprovalParameters) {
        Merchant merchant = merchantDataService.findMerchantById(id);
        if (merchant != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = merchant.getUser();
//            user.setPassword(encoder.encode(merchantApprovalParameters.getPassword()));
//            user.setUsername(merchantApprovalParameters.getUsername());
//            merchant.setUser(user);
//            merchant.setAlipay(merchantApprovalParameters.getAlipay());
//            merchant.setWechat(merchantApprovalParameters.getWechat());
                merchant.setApproverId(merchantApprovalParameters.getApproverId());
//            merchant.setPriority(merchantApprovalParameters.getLevel());
                merchant.setApprovalTime(new Date());
            if (merchantApprovalParameters.getStatus() == 1) {
                merchant.setStatus("启用");
            } else if (merchantApprovalParameters.getStatus() == 0) {
                merchant.setStatus("停用");
            } else {
                return new WrongResponse(10140, "Wrong state");
            }
            merchantDataService.saveMerchant(merchant);
            return new SuccessResponse("Update success.");
        } else {
            return new WrongResponse(10130, "Wrong id.");
        }
    }

    @Override
    public void delMerchantById(int id) {
        merchantDataService.deleteMerchantById(id);
    }

    @Override
    public List<Merchant> findAllMerchants() {
        List<Merchant> merchantList = merchantDataService.getAllMerchants();
        return JSONFilter(merchantList);
    }


    @Override
    public List<Merchant> findMerchantsBySuperior(int id) throws WrongIdException {
        User user = userDataService.getUserById(id);
        if (user == null || user.getId() == 0) {
            throw new WrongIdException();
        } else {
            List<Merchant> result = JSONFilter(merchantDataService.getMerchantsByApplyId(id));
            return result;  //最好传id
        }
    }

    @Override
    public List<Merchant> findMerchantsByState(String status) {
        List<Merchant> merchantList = merchantDataService.getMerchantsByState(status);
        return JSONFilter(merchantList);
    }

    @Override
    public Merchant findMerchantByMerchantName(String merchantName) {
        return merchantDao.findByName(merchantName);
    }

    private List<Merchant> JSONFilter(List<Merchant> merchantList) {
        if (merchantList.size() != 0) {
            for (Merchant merchant : merchantList) {
                List<PersonalCard> cardList = merchant.getUser().getCards();
                cardList.stream().peek(c -> c.setUser(null)).collect(Collectors.toList());
                User user = merchant.getUser();
//                if(user != null) {
//                    if(StringUtils.isNotBlank(user.getOriginPassword()))    user.setOriginPassword(RSAUtils.decryptDataOnJava(user.getOriginPassword(), privateKey));
//                    else user.setOriginPassword("");
//                }
            }
        }
        return merchantList;
    }
}
