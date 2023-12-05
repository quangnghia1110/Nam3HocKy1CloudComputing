package hcmute.service;

import java.util.List;

import hcmute.dto.CheckoutForm;
import hcmute.dto.OrderShow;
import hcmute.dto.PageResponse;
import hcmute.model.order.Order;
import hcmute.model.user.User;

public interface IOrderService {


	Long createOrder(CheckoutForm checkoutForm);

	Order getOrderById(Long orderId);

	List<OrderShow> getOrderShows();

    OrderShow getOrderShowById(Long orderID);

    void updateOrder(OrderShow orderShow);

	List<Order> getAllOrderByUser(User user);

	List<Order> getTop3OrderByUser(User user);

	PageResponse<Order> getOrderByUserAndPage(User user, int parseInt);

	Boolean ExistByUserAndBook(User user, Long bookId);
}
