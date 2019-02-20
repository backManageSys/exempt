package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.Log.SystemControllerLog;
import njurestaurant.njutakeout.blservice.order.TransactionBlService;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.exception.*;
import njurestaurant.njutakeout.parameters.app.GetQrCodeParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewDealParameters;
import njurestaurant.njutakeout.parameters.order.WithdrewParameters;
import njurestaurant.njutakeout.publicdatas.app.CodeType;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.JSONResponse;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.transaction.FailedToLoadCodeResponse;
import njurestaurant.njutakeout.response.transaction.GetQrCodeResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TransactionController {

    private final TransactionBlService transactionBlService;

    @Autowired
    public TransactionController(TransactionBlService transactionBlService) {
        this.transactionBlService = transactionBlService;
    }
    @SystemControllerLog(descrption = "获取二维码",actionType = "1")
    @ApiOperation(value = "获取二维码", notes = "获取二维码")
    @RequestMapping(value = "qrCode/get", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GetQrCodeResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getQrCode(@RequestBody GetQrCodeParameters getQrCodeParameters) {
        try {
            GetQrCodeResponse getQrCodeResponse = transactionBlService.getQrCode(getQrCodeParameters);
            if(getQrCodeResponse == null) {
                return new ResponseEntity<>(new JSONResponse(10300, new FailedToLoadCodeResponse("failed", "未获取到供码设备")), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new JSONResponse(200, getQrCodeResponse), HttpStatus.OK);
            }
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10300, new FailedToLoadCodeResponse("failed", "商户名不存在。")), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10300, new FailedToLoadCodeResponse("failed", "参数错误。")), HttpStatus.OK);
        } catch (IPRiskControlException e){
            return new ResponseEntity<>(new JSONResponse(12345, new FailedToLoadCodeResponse("failed", "ip风控")), HttpStatus.OK);
        }catch (IDRiskControlException e){
            return new ResponseEntity<>(new JSONResponse(54321, new FailedToLoadCodeResponse("failed", "id防刷单")), HttpStatus.OK);
        }catch (TooLittleMoneyException e){
            return new ResponseEntity<>(new JSONResponse(88888, new FailedToLoadCodeResponse("failed", "订单金额过小")), HttpStatus.OK);
        }catch (OrderNotPayedException e){
            return new ResponseEntity<>(new JSONResponse(1015, new FailedToLoadCodeResponse("failed", "订单未支付，不可获取二维码。")), HttpStatus.OK);
        }
    }

    @SystemControllerLog(descrption = "发起提现请求",actionType = "1")
    @ApiOperation(value = "发起提现请求", notes = "发起提现请求")
    @RequestMapping(value = "withdrew", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> withdrew(@RequestBody WithdrewParameters withdrewParameters) {
        try {
            transactionBlService.addWithdrewOrder(withdrewParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("发出提现成功，等待审核。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法进行该提现操作。")), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10120, new WrongResponse(10120, "提现类型错误。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "提现金额大于该用户现有的余额。")), HttpStatus.OK);
        }
    }
    @ApiOperation(value = "查看提现记录", notes = "查看提现记录")
    @RequestMapping(value = "withdrew/history", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> WithdrewHistory() {
            return new ResponseEntity<>(new JSONResponse(200, transactionBlService.getWithdrewOrder()), HttpStatus.OK);
    }

    @ApiOperation(value = "查看未处理的提现订单", notes = "财务查看未处理的提现订单")
    @RequestMapping(value = "withdrews/waiting", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = WithdrewOrder.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showWithdrewOrder() {
        return new ResponseEntity<>(new JSONResponse(200, transactionBlService.getAllWaitingWithdrewOrder()), HttpStatus.OK);
    }
    @SystemControllerLog(descrption = "抢单",actionType = "3")
    @ApiOperation(value = "抢单", notes = "抢单")
    @RequestMapping(value = "withdrew/get/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> showWithdrewOrder(@PathVariable("id")int id, @RequestParam("operatorId")String operatorId) {
        if(!NumberUtils.isNumber(operatorId))   return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "用户id格式错误")), HttpStatus.OK);
        try {
            transactionBlService.grabWithdrewOrderById(id, Integer.valueOf(operatorId));
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("抢单成功")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "订单id错误/用户id错误。")), HttpStatus.OK);
        } catch (WrongInputException e) {
            return new ResponseEntity<>(new JSONResponse(10410, new WrongResponse(10410, "该订单已被处理。")), HttpStatus.OK);
        }
    }
    @SystemControllerLog(descrption = "处理提现订单",actionType = "3")
    @ApiOperation(value = "处理提现订单", notes = "处理提现订单")
    @RequestMapping(value = "withdrew/deal/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> dealWithdrewOrder(@PathVariable("id") int id, @RequestBody WithdrewDealParameters withdrewDealParameters) {
        try {
            transactionBlService.dealWithdrewOrder(id, withdrewDealParameters);
            return new ResponseEntity<>(new JSONResponse(200, new SuccessResponse("处理成功。")), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该财务无法处理该订单/该订单已被处理。")), HttpStatus.OK);
        } catch (BlankInputException e) {
            return new ResponseEntity<>(new JSONResponse(10120, new WrongResponse(10120, "审批状态错误。")), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "财务订单", notes = "显示财务个人的全部提现订单")
    @RequestMapping(value = "withdrew/list/{uid}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = WithdrewOrder.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> dealWithdrewOrder(@PathVariable("uid") int id) {
        try {
            return new ResponseEntity<>(new JSONResponse(200, transactionBlService.getMyWithdrewOrder(id)), HttpStatus.OK);
        } catch (WrongIdException e) {
            return new ResponseEntity<>(new JSONResponse(10160, new WrongResponse(10160, "该用户无法查看提现订单。")), HttpStatus.OK);
        }
    }

//    @ApiOperation(value = "增加设备测试", notes = "测试")
//    @RequestMapping(value = "device/add/test", method = RequestMethod.GET)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Success", response = GetQrCodeResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
//            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
//    @ResponseBody
//    public ResponseEntity<Response> test() {
//        transactionBlService.addDevice();
//        return null;
//    }

    @ApiOperation(value = "重定向", notes = "支付宝跳转重定向")
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public void redirect(@RequestParam("orderId") String orderId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String code = transactionBlService.findQrCodeByOrderId(orderId);
            if(code.equals("expired")) {
            } else if(code.equals("paid")) {
            }
            else {
            	String result = "";
                int index = 0;
                for(int i = 0; i < code.length(); i++) {
                	if(code.charAt(i) == '\"') {
                		result = result + code.substring(index, i) + "%22";
                		index = i + 1;
                	}
                }
                if(index == 0) result = code;
                else result += code.substring(index, code.length());
            	response.sendRedirect(result);
            }
        } catch (WrongIdException e) {
        }
    }

    @ApiOperation(value = "根据imei号查找测试", notes = "支付宝跳转重定向")
    @RequestMapping(value = "/test/order", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response> platformTest(@RequestParam("imei")String imei) {

        return new ResponseEntity<>(new JSONResponse(200, transactionBlService.findPlatformOrderByImeiAndState(imei, OrderState.WAITING_FOR_PAYING)), HttpStatus.OK);
    }
}
