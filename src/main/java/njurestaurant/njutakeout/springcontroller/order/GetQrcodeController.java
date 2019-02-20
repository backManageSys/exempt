package njurestaurant.njutakeout.springcontroller.order;

import org.springframework.web.bind.annotation.*;

//@PreAuthorize(value = "hasRole('" + Role.RESTAURANT_NAME + "')")
@RestController
public class GetQrcodeController {
//    //private final OrderBlService orderBlService;
//
//    @Autowired
//    public GetQrcodeController(OrderBlService orderBlService) {
//        this.orderBlService = orderBlService;
//    }

	//商户可选择获取通码或固码
	//商户和出码用户对应关系为多对一

//    @ApiOperation(value = "获取通码", notes = "返回通码url等信息")
//    @RequestMapping(value = "/getpasscode", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = GetPassCodeResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> getPasscode() {
//        try {
//
//        	String imei = WebSocketHandshakeInterceptor.imei;
//        	User user = UserBlService.findUserByimei(imei);
//        	JSONObject jsonObject = new JSONObject();
//        	jsonObject.put("cmd", "passcode");
//            jsonObject.put("type", "alipay");
//            jsonObject.put("imei", imei);
//            jsonObject.put("userid", "支付宝userid");
//            WebSocketHandler.sendMessageToUser(imei, new TextMessage(jsonObject.toString()));
//            return new ResponseEntity<>(new JSONResponse(200, userLoginResponse), HttpStatus.OK);
//        } catch (WrongUsernameOrPasswordException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new JSONResponse(401, e.getResponse()), HttpStatus.UNAUTHORIZED);
//        } catch (CannotRegisterException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new JSONResponse(503, e.getResponse()), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
//
//    @ApiOperation(value = "获取固码", notes = "返回固码url等信息")
//    @RequestMapping(value = "/getsolidcode", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = GetSolidCodeResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> getsolidcode(String money , String memo) {
//        try {
//
//        	String imei="123";
//        	User user = UserBlService.findUserByimei(imei);
//        	JSONObject jsonObject = new JSONObject();
//        	jsonObject.put("cmd", "solidcode");
//            jsonObject.put("type", "alipay");
//            jsonObject.put("imei", imei);
//            jsonObject.put("userid", "支付宝userid");
//            jsonObject.put("money", money);
//            jsonObject.put("memo", memo);
//            WebSocketHandler.sendMessageToUser(imei, new TextMessage(jsonObject.toString()));
//            return new ResponseEntity<>(new JSONResponse(200, userLoginResponse), HttpStatus.OK);
//        } catch (WrongUsernameOrPasswordException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new JSONResponse(401, e.getResponse()), HttpStatus.UNAUTHORIZED);
//        } catch (CannotRegisterException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new JSONResponse(503, e.getResponse()), HttpStatus.SERVICE_UNAVAILABLE);
//        }
//    }
}
