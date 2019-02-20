package njurestaurant.njutakeout.springcontroller.order;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import njurestaurant.njutakeout.blservice.order.OrderBlService;
import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.exception.OrderIdDoesNotExistException;
import njurestaurant.njutakeout.exception.PrintFailException;
import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.parameters.order.OrderCommentParameters;
import njurestaurant.njutakeout.parameters.order.OrderFinalPriceParameters;
import njurestaurant.njutakeout.parameters.order.OrderSubmitParameters;
import njurestaurant.njutakeout.publicdatas.account.Role;
import njurestaurant.njutakeout.response.Response;
import njurestaurant.njutakeout.response.WrongResponse;
import njurestaurant.njutakeout.response.comment.CommentLoadResponse;
import njurestaurant.njutakeout.response.comment.OrderCommentResponse;
import njurestaurant.njutakeout.response.order.*;
import njurestaurant.njutakeout.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize(value = "hasRole('" + Role.USER_NAME + "')")
@RestController
public class CustomerOrderController {
    private final OrderBlService orderBlService;

    @Autowired
    public CustomerOrderController(OrderBlService orderBlService) {
        this.orderBlService = orderBlService;
    }

    @ApiOperation(value = "计算总计", notes = "根据活动信息来计算总计金额")
    @RequestMapping(value = "order/price", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinalPriceGetResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> getFinalPrice(@RequestBody OrderFinalPriceParameters orderFinalPriceParameters) {
        return new ResponseEntity<>(orderBlService.getFinalPrice(orderFinalPriceParameters, UserInfoUtil.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "提交订单", notes = "提交订单")
    @RequestMapping(value = "order", method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderSubmitResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> submitOrder(@RequestBody OrderSubmitParameters orderSubmitParameters) {
        try {
            return new ResponseEntity<>(orderBlService.submitOrder(orderSubmitParameters, UserInfoUtil.getUsername()), HttpStatus.OK);
        } catch (FoodIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        } catch (SystemException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "加载订单", notes = "加载所有订单信息")
    @RequestMapping(value = "order", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> loadOrders() {
        return new ResponseEntity<>(orderBlService.loadOrders(UserInfoUtil.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "支付订单", notes = "加载需支付的订单的信息")
    @RequestMapping(value = "order/pay/{orderId}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> loadPayOrderInfo(@PathVariable(name = "orderId") int orderId) {
        try {
            return new ResponseEntity<>(orderBlService.loadPayOrderInfo(orderId), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "确认订单", notes = "确认某订单")
    @RequestMapping(value = "order/{orderId}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderConfirmResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 404, message = "order does not found", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> confirmOrder(@PathVariable(name = "orderId") int orderId) {
        try {
            return new ResponseEntity<>(orderBlService.confirmOrder(orderId), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "删除订单", notes = "删除某订单")
    @RequestMapping(value = "order/{orderId}", method = RequestMethod.DELETE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderDeleteResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 404, message = "order does not found", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> deleteOrder(@PathVariable(name = "orderId") int orderId) {
        try {
            return new ResponseEntity<>(orderBlService.deleteOrder(orderId), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "订单搁置", notes = "使订单搁置")
    @RequestMapping(value = "order/wait/{orderId}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderWaitingForPayingResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> waitForPayingOrder(@PathVariable(name = "orderId") int orderId) {
        try {
            return new ResponseEntity<>(orderBlService.waitForPayingOrder(orderId), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "打印订单", notes = "打印订单")
    @RequestMapping(value = "order/print/{orderId}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderPrintResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> printOrder(@PathVariable(name = "orderId") int orderId) {
        try {
            return new ResponseEntity<>(orderBlService.printOrder(orderId), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        } catch (PrintFailException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @ApiOperation(value = "评论订单", notes = "评论订单")
    @RequestMapping(value = "order/comment", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OrderCommentResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> commentOrder(@RequestBody OrderCommentParameters orderCommentParameters) {
        try {
            return new ResponseEntity<>(orderBlService.commentOrder(orderCommentParameters), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "加载评论", notes = "加载所有评论")
    @RequestMapping(value = "order/comment", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CommentLoadResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = WrongResponse.class),
            @ApiResponse(code = 500, message = "Failure", response = WrongResponse.class)})
    @ResponseBody
    public ResponseEntity<Response> loadComments() {
        try {
            return new ResponseEntity<>(orderBlService.loadComments(), HttpStatus.OK);
        } catch (OrderIdDoesNotExistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.NOT_FOUND);
        }
    }
}
