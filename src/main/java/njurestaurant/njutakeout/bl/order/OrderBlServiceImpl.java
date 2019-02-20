package njurestaurant.njutakeout.bl.order;

import njurestaurant.njutakeout.blservice.order.OrderBlService;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.event.EventDataService;
import njurestaurant.njutakeout.dataservice.food.FoodDataService;
import njurestaurant.njutakeout.dataservice.order.OrderDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.event.Event;
import njurestaurant.njutakeout.entity.event.FullSubtractionEvent;
import njurestaurant.njutakeout.entity.event.ItemSubtractionEvent;
import njurestaurant.njutakeout.entity.event.ItemSubtractionOnceEvent;
import njurestaurant.njutakeout.entity.food.Food;
import njurestaurant.njutakeout.entity.order.Comment;
import njurestaurant.njutakeout.entity.order.Order;
import njurestaurant.njutakeout.entity.order.OrderFood;
import njurestaurant.njutakeout.exception.FoodIdDoesNotExistException;
import njurestaurant.njutakeout.exception.OrderIdDoesNotExistException;
import njurestaurant.njutakeout.exception.PrintFailException;
import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.parameters.order.OrderCommentParameters;
import njurestaurant.njutakeout.parameters.order.OrderFinalPriceParameters;
import njurestaurant.njutakeout.parameters.order.OrderSubmitFood;
import njurestaurant.njutakeout.parameters.order.OrderSubmitParameters;
import njurestaurant.njutakeout.publicdatas.event.EventState;
import njurestaurant.njutakeout.publicdatas.order.OrderState;
import njurestaurant.njutakeout.response.comment.CommentItem;
import njurestaurant.njutakeout.response.comment.CommentLoadResponse;
import njurestaurant.njutakeout.response.comment.OrderCommentResponse;
import njurestaurant.njutakeout.response.order.*;
import njurestaurant.njutakeout.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderBlServiceImpl implements OrderBlService {
    private final OrderDataService orderDataService;
    private final UserDataService userDataService;
    private final FoodDataService foodDataService;
    private final EventDataService eventDataService;

    @Value(value = "${wechat.order_url}")
    private String ORDER_URL;
    @Value(value = "${wechat.id}")
    private String APP_ID;
    @Value(value = "${wechat.mch_id}")
    private String MCH_ID;
    @Value(value = "${wechat.body}")
    private String BODY;
    @Value(value = "${wechat.device_info}")
    private String DEVICE_INFO;
    @Value(value = "${wechat.notify_url}")
    private String NOTIFY_URL;
    @Value(value = "${wechat.trade_type}")
    private String TRADE_TYPE;
    @Value(value = "${wechat.api_key}")
    private String API_KEY;
    @Value(value = "${wechat.sign_type}")
    private String SIGN_TYPE;

    @Autowired
    public OrderBlServiceImpl(OrderDataService orderDataService, UserDataService userDataService, FoodDataService foodDataService, EventDataService eventDataService) {
        this.orderDataService = orderDataService;
        this.userDataService = userDataService;
        this.foodDataService = foodDataService;
        this.eventDataService = eventDataService;
    }

    /**
     * get the final price of the order filtered by events
     *
     * @param orderFinalPriceParameters
     * @param username
     * @return
     */
    @Override
    public FinalPriceGetResponse getFinalPrice(OrderFinalPriceParameters orderFinalPriceParameters, String username) {
        double originTotal = orderFinalPriceParameters.getTotal();
        List<OrderSubmitFood> orderSubmitFoods = orderFinalPriceParameters.getFoods();
        List<Event> events = eventDataService.getAllEvents().stream().filter((event) -> event.getEventState() == EventState.ACTIVE).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        for (Event event : events) {
            switch (event.getEventType()) {
                case FirstOrder:
                    User user = userDataService.getUserByUsername(username);
//                    if (user.getOrders().size() == 0) {
//                        double result = originTotal / 2;
//                        return new FinalPriceGetResponse(result);
//                    }
                    break;
                case FullSubtraction:
                    if (originTotal >= ((FullSubtractionEvent) event).getFullPrice()) {
                        double result = (originTotal - ((FullSubtractionEvent) event).getMinusPrice()) <= 0 ? 0.01 : (originTotal - ((FullSubtractionEvent) event).getMinusPrice());
                        return new FinalPriceGetResponse(result);
                    }
                    break;
                case ItemSubtraction:
                    Boolean isContain = false;
                    for (int itemId : ((ItemSubtractionEvent) event).getItemList()) {
                        if (orderSubmitFoods.stream().anyMatch((food) -> food.getFoodId() == itemId)) {
                            isContain = true;
                        }
                    }
                    if (isContain) {
                        double result = (originTotal - ((ItemSubtractionEvent) event).getMinusPrice()) <= 0 ? 0.01 : (originTotal - ((ItemSubtractionEvent) event).getMinusPrice());
                        return new FinalPriceGetResponse(result);
                    }
                    break;
                case ItemSubtractionOnce:
                    if (!orderDataService.hasOrderedTheFoodBefore(((ItemSubtractionOnceEvent) event).getItemId(), username)) {
                        if (orderSubmitFoods.stream().anyMatch((food) -> food.getFoodId() == ((ItemSubtractionOnceEvent) event).getItemId())) {
                            double result = (originTotal - ((ItemSubtractionOnceEvent) event).getMinusPrice()) <= 0 ? 0.01 : (originTotal - ((ItemSubtractionOnceEvent) event).getMinusPrice());
                            return new FinalPriceGetResponse(result);
                        }
                    }
            }
        }
        return new FinalPriceGetResponse(originTotal);
    }

    /**
     * submit the order
     *
     * @param orderSubmitParameters
     * @param username
     * @return
     */
    @Override
    public OrderSubmitResponse submitOrder(OrderSubmitParameters orderSubmitParameters, String username) throws FoodIdDoesNotExistException, SystemException {
        List<OrderFood> foodList = new ArrayList<>();
        for (OrderSubmitFood orderSubmitFood : orderSubmitParameters.getFoods()) {
            Food food = foodDataService.getFoodById(orderSubmitFood.getFoodId());
            OrderFood orderFood = new OrderFood(food.getId(), food.getPort().getName(), food.getName(), orderSubmitFood.getSpecialChoice(), food.getPrice());
            foodList.add(orderFood);
        }
        Order order = new Order(OrderUUIDUtil.generateUUID(), OrderState.REQUESTING, orderSubmitParameters.getTotal(), orderSubmitParameters.getDate(), orderSubmitParameters.getAddress(), orderSubmitParameters.getComment(), foodList, orderSubmitParameters.getPhone(), "", "", "", "", "", userDataService.getUserByUsername(username));
        order = orderDataService.saveOrder(order);
        try {
            return unifiedorder(order);
        } catch (SystemException e) {
            e.printStackTrace();
            orderDataService.deleteOrder(order.getId());
            throw new SystemException();
        }
    }

    /**
     * load all of the orders of someone
     *
     * @param username
     * @return
     */
    @Override
    public OrderLoadResponse loadOrders(String username) {
        List<Order> orders = orderDataService.getNotDeletedOrdersByUsername(username);
        Collections.reverse(orders);
        List<OrderItem> orderItemList = generateOrderItemList(orders);
        return new OrderLoadResponse(orderItemList);
    }

    /**
     * confirm one order
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderConfirmResponse confirmOrder(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        order.setOrderState(OrderState.CONFIRMED);
        orderDataService.updateOrder(order);
        return new OrderConfirmResponse();
    }

    /**
     * delete one order
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderDeleteResponse deleteOrder(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        order.setOrderState(OrderState.DELETED);
        orderDataService.updateOrder(order);
        return new OrderDeleteResponse();
    }

    /**
     * supplier accept an order
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderAcceptResponse acceptOrder(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        order.setOrderState(OrderState.VALID);
        orderDataService.updateOrder(order);
        return new OrderAcceptResponse();
    }

    /**
     * supplier load all orders
     *
     * @return
     */
    @Override
    public OrderLoadResponse loadAllOrders() {
        List<Order> orders = orderDataService.getAllOrders();
        Collections.reverse(orders);
        List<OrderItem> orderItemList = generateOrderItemList(orders);
        return new OrderLoadResponse(orderItemList);
    }

    /**
     * supplier reject an order
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderRejectResponse rejectOrder(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        order.setOrderState(OrderState.REJECTED);
        orderDataService.updateOrder(order);
        return new OrderRejectResponse();
    }

    /**
     * supplier confirm an order arrives
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderConfirmArriveResponse confirmArriveOrder(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        order.setOrderState(OrderState.ARRIVED);
        orderDataService.updateOrder(order);
        return new OrderConfirmArriveResponse();
    }

    /**
     * set order state to wait for paying
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderWaitingForPayingResponse waitForPayingOrder(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        order.setOrderState(OrderState.DELETED);
        orderDataService.updateOrder(order);
        return new OrderWaitingForPayingResponse();
    }

    /**
     * load the order info to pay
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderSubmitResponse loadPayOrderInfo(int orderId) throws OrderIdDoesNotExistException {
        Order order = orderDataService.getOrderByOrderId(orderId);
        return new OrderSubmitResponse(order.getId(), order.getTimeStamp(), order.getNonceStr(), order.getPakcage(), order.getSignType(), order.getPaySign());
    }

    /**
     * print the order
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderPrintResponse printOrder(int orderId) throws OrderIdDoesNotExistException, PrintFailException {
        acceptOrder(orderId);
        Order order = orderDataService.getOrderByOrderId(orderId);
        StringBuffer sb = new StringBuffer("");
        sb.append("订单编号：" + order.getOrderUUID() + "\r");
        sb.append("----------------------\r");
        sb.append("@@2联系电话：" + order.getPhone() + "\r");
        sb.append("用餐时间：" + order.getDate() + "\r");
        sb.append("用餐地址：" + order.getAddress() + "\r");
        sb.append("详细门牌号：" + order.getAddress() + "\r");
        sb.append("----------------------\r");
        sb.append("@@2菜品明细\r\r");
        for (OrderFood orderFood : order.getFoods()) {
            sb.append("@@2" + orderFood.getPortName() + "-" + orderFood.getName() + "-" + orderFood.getSpecialChoice() + "-" + orderFood.getPrice() + "\r");
        }
        sb.append("----------------------\r");
        sb.append("客户备注\r\r");
        sb.append(order.getComment() + "\r");
        sb.append("----------------------\r");
        sb.append("总价:" + order.getTotal() + "元\r");

        PrintMessage.sendContent(sb.toString());//打印消息
        return new OrderPrintResponse();
    }

    /**
     * comment an order
     *
     * @param orderCommentParameters
     * @return
     */
    @Override
    public OrderCommentResponse commentOrder(OrderCommentParameters orderCommentParameters) throws OrderIdDoesNotExistException {
        orderDataService.saveComment(new Comment(orderCommentParameters.getOrderId(), orderCommentParameters.getFoodIds(), orderCommentParameters.getComment(), new Date()));
        Order order = orderDataService.getOrderByOrderId(orderCommentParameters.getOrderId());
        order.setOrderState(OrderState.COMMENTED);
        orderDataService.updateOrder(order);
        return new OrderCommentResponse();
    }

    /**
     * load all comments
     *
     * @return
     */
    @Override
    public CommentLoadResponse loadComments() throws OrderIdDoesNotExistException {
        List<CommentItem> commentItems = new ArrayList<>();
        List<Comment> comments = orderDataService.loadAllComments();
        for (Comment comment : comments) {
            Order order = orderDataService.getOrderByOrderId(comment.getOrderId());
            String avatarUrl = order.getUser().getAvatarUrl();
            commentItems.add(new CommentItem(avatarUrl, FormatDateTime.toLongDateString(comment.getDate()), comment.getComment()));
        }
        return new CommentLoadResponse(commentItems);
    }

    private OrderSubmitResponse unifiedorder(Order order) throws SystemException {
        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", APP_ID);
        packageParams.put("mch_id", MCH_ID);
        packageParams.put("nonce_str", RandomUtil.generateNonceStr());//时间戳
        packageParams.put("body", BODY);//支付主体
        packageParams.put("out_trade_no", order.getOrderUUID() + "");//编号
        packageParams.put("total_fee", (int) (order.getTotal() * 100) + "");//价格
        packageParams.put("notify_url", NOTIFY_URL);//支付返回地址，不用纠结这个东西，我就是随便写了一个接口，内容什么都没有
        packageParams.put("trade_type", TRADE_TYPE);//这个api有，固定的
        packageParams.put("openid", order.getUser().getUsername());//openid
        //获取sign
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, API_KEY);//最后这个是自己设置的32位密钥
        packageParams.put("sign", sign);

        String requestXML = PayCommonUtil.getRequestXml(packageParams);

        //得到含有prepay_id的XML
        String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);

        String timeStamp = new Date().getTime() + "";
        String nonceStr = XMLUtil.parserXmlToGetNonceStr(resXml);
        String pakcage = "prepay_id=" + XMLUtil.parserXmlToGetPrepayId(resXml);
        String signType = SIGN_TYPE;
        String apiKey = API_KEY;

        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("appId", APP_ID);
        sortedMap.put("timeStamp", timeStamp);
        sortedMap.put("nonceStr", nonceStr);
        sortedMap.put("package", pakcage);
        sortedMap.put("signType", signType);
        String paySign = PayCommonUtil.createSign("UTF-8", sortedMap, apiKey);

        order.setTimeStamp(timeStamp);
        order.setNonceStr(nonceStr);
        order.setPakcage(pakcage);
        order.setSignType(signType);
        order.setPaySign(paySign);
        orderDataService.updateOrder(order);
        return new OrderSubmitResponse(order.getId(), timeStamp, nonceStr, pakcage, signType, paySign);
    }

    private List<OrderItem> generateOrderItemList(List<Order> orders) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Order order : orders) {
            List<OrderFoodItem> orderFoodItemList = new ArrayList<>();
            for (OrderFood orderFood : order.getFoods()) {
                OrderFoodItem orderFoodItem = new OrderFoodItem(orderFood.getId(), orderFood.getPortName(), orderFood.getName(), orderFood.getSpecialChoice(), orderFood.getPrice());
                orderFoodItemList.add(orderFoodItem);
            }
            OrderItem orderItem = new OrderItem(order.getId(), order.getTotal(), order.getDate(), order.getAddress(), order.getPhone(), order.getComment(), orderFoodItemList, order.getOrderState());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
}
