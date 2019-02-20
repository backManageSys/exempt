package njurestaurant.njutakeout.springcontroller.app;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.account.UserBlService;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    private final UserBlService userBlService;

    @Autowired
    public AppController(UserBlService userBlService) {
        this.userBlService = userBlService;
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
    public ResponseEntity<Response> appLogin(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("imei")String imei) {
    	System.out.println("12");
        try {
            return new ResponseEntity<>(new JSONResponse(200, userBlService.appLogin(username,password,imei)), HttpStatus.OK);
        } catch (WrongUsernameOrPasswordException e) {
            return new ResponseEntity<>(new JSONResponse(10003, e.getResponse()), HttpStatus.OK);
        } catch (CannotRegisterException e) {
            return new ResponseEntity<>(new JSONResponse(10006, e.getResponse()), HttpStatus.OK);
        } catch (RoleIdentityNotConformException e){
            return new ResponseEntity<>(new JSONResponse(10009,e.getResponse()), HttpStatus.OK);
        } catch (WaitingforAuthorizeException e) {
            return new ResponseEntity<>(new JSONResponse(10014,e.getResponse()), HttpStatus.OK);
        }
    }

    @SystemControllerLog(descrption = "修改设备状态",actionType = "3")
    @ApiOperation(value = "修改设备状态", notes = "修改设备状态")
    @RequestMapping(value = "app/device", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> appDevice(@RequestParam("id")int id,@RequestParam("imei")String imei,@RequestParam("status")String status) {

        return new ResponseEntity<>(new JSONResponse(200, userBlService.appDevice(id,imei,status)), HttpStatus.OK);
    }

    @ApiOperation(value = "googleApi", notes = "谷歌api验证")
    @RequestMapping(value = "google/api", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> googleApi(@RequestParam("rp")String response) {
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
