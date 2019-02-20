package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.parameters.order.OrderMsgParameters;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.SuccessResponse;
import njurestaurant.njutakeout.response.WrongResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlipayOrderController {

    @ApiOperation(value = "订单接收", notes = "接收从客户端传来的订单消息")
    @RequestMapping(value = "order/add", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getFinalPrice(@RequestBody OrderMsgParameters orderMsgParameters) {
        return new ResponseEntity<>(new SuccessResponse("hello"), HttpStatus.OK);
//        return new ResponseEntity<>(orderBlService.getFinalPrice(orderFinalPriceParameters, UserInfoUtil.getUsername()), HttpStatus.OK);
    }
}
