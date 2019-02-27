package njurestaurant.njutakeout.springcontroller.app;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.data.dao.app.AlipayDao;
import njurestaurant.njutakeout.dataservice.account.SupplierDataService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.app.AlipayDataService;
import njurestaurant.njutakeout.dataservice.app.DeviceDataService;
import njurestaurant.njutakeout.dataservice.company.CompanyCardDataService;
import njurestaurant.njutakeout.entity.account.Supplier;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.app.Alipay;
import njurestaurant.njutakeout.entity.app.Device;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.app.AddPersonalCard;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AppController {

    private final UserBlService userBlService;
    private final AlipayDao alipayDao;
    private final AlipayDataService alipayDataService;
    private final UserDataService userDataService;
    private final SupplierDataService supplierDataService;
    private final DeviceDataService deviceDataService;
    private final CompanyCardDataService companyCardDataService;

    @Autowired
    public AppController(UserBlService userBlService, AlipayDao alipayDao, AlipayDataService alipayDataService, UserDataService userDataService, SupplierDataService supplierDataService, DeviceDataService deviceDataService, CompanyCardDataService companyCardDataService) {
        this.userBlService = userBlService;
        this.alipayDao = alipayDao;
        this.alipayDataService = alipayDataService;
        this.userDataService = userDataService;
        this.supplierDataService = supplierDataService;
        this.deviceDataService = deviceDataService;
        this.companyCardDataService = companyCardDataService;
    }

    //    @ApiOperation(value = "websocket测试", notes = "websocket测试")
//    @MessageMapping(value = "ws/test")
//    @SendTo("ws/client")
//    public String greeting(String message) throws Exception {
//        // 模拟延时，以便测试客户端是否在异步工作
////        Thread.sleep(1000);
//        return "Hello, ";
//    }


    @ApiOperation(value = "app用户登录", notes = "供码用户在app上登录")
    @RequestMapping(value = "app/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> appLogin(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("imei") String imei) {
        System.out.println("12");
        try {
            return new ResponseEntity<>(new JSONResponse(200, userBlService.appLogin(username, password, imei)), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            return new ResponseEntity<>(new JSONResponse(10003, e.getResponse()), HttpStatus.OK);
        } catch (CannotRegisterException e) {
            return new ResponseEntity<>(new JSONResponse(10006, e.getResponse()), HttpStatus.OK);
        } catch (RoleIdentityNotConformException e) {
            return new ResponseEntity<>(new JSONResponse(10009, e.getResponse()), HttpStatus.OK);
        } catch (WaitingforAuthorizeException e) {
            return new ResponseEntity<>(new JSONResponse(10014, e.getResponse()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "查看某个供码用户所有设备", notes = "查看某个供码用户所有设备")
    @RequestMapping(value = "app/device/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getAppDevice(@RequestParam("uid") int uid) {
        User user = userDataService.getUserById(uid);
        return new ResponseEntity<>(new JSONResponse(200, deviceDataService.findDevicesBySupplierId(user.getTableId())), HttpStatus.OK);
    }

    @SystemControllerLog(descrption = "修改设备状态", actionType = "3")
    @ApiOperation(value = "修改设备状态", notes = "修改设备状态")
    @RequestMapping(value = "app/device", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> appDevice(@RequestParam("id") int id, @RequestParam("imei") String imei, @RequestParam("status") String status) {

        return new ResponseEntity<>(new JSONResponse(200, userBlService.appDevice(id, imei, status)), HttpStatus.OK);
    }

    @ApiOperation(value = "根据设备号查找支付宝信息", notes = "根据设备号查找支付宝信息")
    @RequestMapping(value = "app/getAlipayByDevice", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getAlipayByDevice(@RequestParam("imei") String imei) {
        return new ResponseEntity<>(new JSONResponse(200, alipayDao.findByImei(imei)), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "供码用户添加个人银行卡", actionType = "3")
    @ApiOperation(value = "供码用户添加个人银行卡", notes = "供码用户添加个人银行卡")
    @RequestMapping(value = "app/supplier/addPersonalCard", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> addPersonalCard(@RequestParam("loginId") String loginId, @RequestBody AddPersonalCard addPersonalCard) {
        Alipay alipay = alipayDataService.findByLoginId(loginId);
        if (StringUtils.isBlank(alipay.getCardNumber())) {
            alipay.setCardNumber(addPersonalCard.getCardNumber());
            alipay.setCardBalance(addPersonalCard.getCardBalance());
            alipayDataService.saveAlipay(alipay);
            return new ResponseEntity<>(new JSONResponse(200, "供码用户添加个人银行卡成功"), HttpStatus.OK);
        }else
            return new ResponseEntity<>(new JSONResponse(200, "已添加银行卡，不能重复添加"), HttpStatus.OK);
    }

    //    @ApiOperation(value = "供码用户修改个人银行卡", notes = "供码用户修改个人银行卡")
//    @RequestMapping(value = "app/supplier/updatePersonalCard", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<Response> updatePersonalCard(@RequestParam("alipayId") int alipayId , @RequestBody AddPersonalCard addPersonalCard) {
//        Alipay alipay = alipayDataService.findById(alipayId);
//        alipay.setCardNumber(addPersonalCard.getCardNumber());
//        alipay.setCardBalance(addPersonalCard.getCardBalance());
//        alipayDataService.saveAlipay(alipay);
//        return new ResponseEntity<>(new JSONResponse(200,"供码用户修改个人银行卡成功" ), HttpStatus.OK);
//    }
    @ApiOperation(value = "供码用户查看个人银行卡", notes = "供码用户查看个人银行卡")
    @RequestMapping(value = "app/supplier/getPersonalCard", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getPersonalCard(@RequestParam("alipayId") int alipayId) {
        return new ResponseEntity<>(new JSONResponse(200, alipayDataService.findById(alipayId)), HttpStatus.OK);
    }

    @ApiOperation(value = "供码用户查看名下所有支付宝账号", notes = "供码用户查看名下所有支付宝账号")
    @RequestMapping(value = "app/supplier/getLoginId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getLoginId(@RequestParam("uid") int uid) {
        User user = userDataService.getUserById(uid);
        Supplier supplier = supplierDataService.findSupplierById(user.getTableId());
        List<Device> list = deviceDataService.findDevicesBySupplierId(supplier.getId());
        List<String> list1 = new ArrayList<>();
        list.forEach((Device device) -> System.out.println(device.getImei()));
        list.forEach((Device device) -> list1.add(alipayDataService.findById(device.getAlipayId()).getLoginId()));
        return new ResponseEntity<>(new JSONResponse(200, list1), HttpStatus.OK);
    }

    @ApiOperation(value = "供码用户查看名下所有归集卡", notes = "供码用户查看名下所有归集卡")
    @RequestMapping(value = "app/supplier/getCompanyCard", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> getCompanyCard(@RequestParam("uid") int uid) {
        return new ResponseEntity<>(new JSONResponse(200, companyCardDataService.findCompanyCardByOperateId(uid)), HttpStatus.OK);
    }

    @ApiOperation(value = "googleApi", notes = "谷歌api验证")
    @RequestMapping(value = "google/api", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> googleApi(@RequestParam("rp") String response) {
        System.out.println("googleApi...");
        Map<String, String> map = new HashMap<>();
        map.put("response", response);
        map.put("secret", "6LdRB4sUAAAAAOzE_J2uhZ4LDhbxkIUFqfx1N1gB");
        JSONObject jsonObject = JSONObject.fromObject(map);
        System.out.println("JSON:" + jsonObject);
        try {
            String returnStr = HttpUtil.postData("https://www.google.com/recaptcha/api/siteverify", jsonObject.toString(), "application/json");
            System.out.println(returnStr);
            jsonObject = JSONObject.fromObject(returnStr);
            String success = jsonObject.getString("success");
            return new ResponseEntity<>(new JSONResponse(200, success), HttpStatus.OK);
        } catch (SystemException e) {
            return new ResponseEntity<>(new JSONResponse(10001, new WrongResponse(10001, "系统出错。")), HttpStatus.OK);
        }
    }

}
