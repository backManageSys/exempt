package njurestaurant.njutakeout.blservice.order;

import njurestaurant.njutakeout.entity.order.PlatformOrder;
import njurestaurant.njutakeout.exception.BlankInputException;
import njurestaurant.njutakeout.exception.OrderWrongInputException;
import njurestaurant.njutakeout.exception.WrongIdException;
import njurestaurant.njutakeout.exception.WrongInputException;
import njurestaurant.njutakeout.parameters.order.PlatformUpdateParameters;
import njurestaurant.njutakeout.response.order.OrderListResponse;
import njurestaurant.njutakeout.response.report.MerchantReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PlatformOrderBlService {
    PlatformOrder findPlatformOrderById(int id) throws WrongIdException;
    PlatformOrder findPlatformOrderByNumber(String number);
    /**
     * 查看全部订单明细
     *
     * @return the order information
     *
     */

    Page<OrderListResponse> findAllPlatformOrders(@RequestParam("condition") String condition, @RequestParam("page") Integer page, @RequestParam("size") Integer size);

    PlatformOrder updatePlatformOrder(int id, PlatformUpdateParameters platformUpdateParameters) throws BlankInputException, OrderWrongInputException;

   // List<OrderListResponse> merchantOrderReportByUid(int uid);


}
