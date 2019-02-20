package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.exception.OrderIdDoesNotExistException;
import njurestaurant.njutakeout.exception.PrintFailException;
import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.parameters.order.OrderCommentParameters;
import njurestaurant.njutakeout.parameters.order.OrderFinalPriceParameters;
import njurestaurant.njutakeout.parameters.order.OrderSubmitParameters;
import njurestaurant.njutakeout.response.comment.CommentLoadResponse;
import njurestaurant.njutakeout.response.comment.OrderCommentResponse;
import njurestaurant.njutakeout.response.order.*;

public interface OrderBlService {
    /**
     * get the final price of the order filtered by events
     *
     * @param orderFinalPriceParameters
     * @param username
     * @return
     */
    FinalPriceGetResponse getFinalPrice(OrderFinalPriceParameters orderFinalPriceParameters, String username);

    /**
     * submit the order
     *
     * @param orderSubmitParameters
     * @param username
     * @return
     */
    OrderSubmitResponse submitOrder(OrderSubmitParameters orderSubmitParameters, String username) throws FoodIdDoesNotExistException, SystemException;

    /**
     * load all of the orders of someone
     *
     * @param username
     * @return
     */
    OrderLoadResponse loadOrders(String username);

    /**
     * confirm one order
     *
     * @param orderId
     * @return
     */
    OrderConfirmResponse confirmOrder(int orderId) throws OrderIdDoesNotExistException;

    /**
     * delete one order
     *
     * @param orderId
     * @return
     */
    OrderDeleteResponse deleteOrder(int orderId) throws OrderIdDoesNotExistException;

    /**
     * supplier accept an order
     *
     * @param orderId
     * @return
     */
    OrderAcceptResponse acceptOrder(int orderId) throws OrderIdDoesNotExistException;

    /**
     * supplier load all orders
     *
     * @return
     */
    OrderLoadResponse loadAllOrders();

    /**
     * supplier reject an order
     *
     * @param orderId
     * @return
     */
    OrderRejectResponse rejectOrder(int orderId) throws OrderIdDoesNotExistException;

    /**
     * supplier confirm an order arrives
     *
     * @param orderId
     * @return
     */
    OrderConfirmArriveResponse confirmArriveOrder(int orderId) throws OrderIdDoesNotExistException;

    /**
     * set order state to wait for paying
     *
     * @param orderId
     * @return
     */
    OrderWaitingForPayingResponse waitForPayingOrder(int orderId) throws OrderIdDoesNotExistException;

    /**
     * load the order info to pay
     *
     * @param orderId
     * @return
     */
    OrderSubmitResponse loadPayOrderInfo(int orderId) throws OrderIdDoesNotExistException;

    /**
     * print the order
     *
     * @param orderId
     * @return
     */
    OrderPrintResponse printOrder(int orderId) throws OrderIdDoesNotExistException, PrintFailException;

    /**
     * comment an order
     *
     * @param orderCommentParameters
     * @return
     */
    OrderCommentResponse commentOrder(OrderCommentParameters orderCommentParameters) throws OrderIdDoesNotExistException;

    /**
     * load all comments
     * @return
     */
    CommentLoadResponse loadComments() throws OrderIdDoesNotExistException;
}
