package njurestaurant.njutakeout.dataservice.order;

import njurestaurant.njutakeout.entity.order.Comment;
import njurestaurant.njutakeout.entity.order.Order;
import njurestaurant.njutakeout.exception.OrderIdDoesNotExistException;

import java.util.List;

public interface OrderDataService {
    /**
     * save order
     *
     * @param order
     */
    Order saveOrder(Order order);

    /**
     * delete order
     *
     * @param orderId
     */
    void deleteOrder(int orderId);

    /**
     * get order list which not deleted through user username
     *
     * @param username
     * @return
     */
    List<Order> getNotDeletedOrdersByUsername(String username);

    /**
     * get all orders
     *
     * @return
     */
    List<Order> getAllOrders();

    /**
     * get order by its id
     * get order by its id
     *
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(int orderId) throws OrderIdDoesNotExistException;

    /**
     * update order
     *
     * @param order
     */
    void updateOrder(Order order);

    /**
     * confirm if the user has order a food before
     *
     * @param foodId
     * @param username
     * @return
     */
    boolean hasOrderedTheFoodBefore(int foodId, String username);

    /**
     * save a comment
     *
     * @param comment
     */
    void saveComment(Comment comment) throws OrderIdDoesNotExistException;

    /**
     * @return
     */
    List<Comment> loadAllComments();

    /**
     * @param foodId
     * @return
     */
    int getAmountOfLikePeople(int foodId);
}
