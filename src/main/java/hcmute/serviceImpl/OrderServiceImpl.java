package hcmute.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hcmute.dto.CheckoutForm;
import hcmute.dto.OrderShow;
import hcmute.dto.PageResponse;
import hcmute.model.Laptop;
import hcmute.model.CartItem;
import hcmute.model.Inventory;
import hcmute.model.order.Order;
import hcmute.model.order.OrderItem;
import hcmute.model.order.OrderTrack;
import hcmute.model.user.User;
import hcmute.repository.LaptopRepository;
import hcmute.repository.CartItemReposirory;
import hcmute.repository.InventoryRepository;
import hcmute.repository.OrderItemRepository;
import hcmute.repository.OrderRepository;
import hcmute.repository.OrderTrackRepository;
import hcmute.repository.UserRepository;
import hcmute.security.UserPrincipal;
import hcmute.service.IOrderService;
import hcmute.utils.AppConstant;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderService{

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	CartItemReposirory cartItemRepository;
	
	@Autowired
	OrderTrackRepository trackRepository;
	
	@Autowired
	LaptopRepository laptopRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public List<Order> getAllOrderByUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public Long createOrder(CheckoutForm checkoutForm) {
		
		User user = userRepository.findByEmail(checkoutForm.getEmail());
		if(Objects.isNull(user)) {
			log.error(AppConstant.USER_NOT_FOUND + checkoutForm.getEmail());
		}
		
		List<String> itemsId = checkoutForm.getCheckoutItems();
		List<CartItem> cartItems = new ArrayList<>();
		
		
		for (String item : itemsId) {
			CartItem cartItem =  cartItemRepository.findById(Long.parseLong(item)).get();
			if(Objects.nonNull(cartItem)) {
				cartItems.add(cartItem);
				// delete that item in cart to transfer to order
				cartItemRepository.deleteById(Long.parseLong(item));
				
				// Decrease quantity
				Inventory inventory = inventoryRepository.findByLaptop(cartItem.getLaptop());
				inventory.setQuantiy(inventory.getQuantiy() - cartItem.getQuantity());
				inventoryRepository.save(inventory);
			}
		}
		OrderTrack orderTrack;
		if(checkoutForm.getPaymentMethod().equals("Momo")) {
			orderTrack = trackRepository.findByStatus("Chờ thanh toán");
		}else {
			orderTrack = trackRepository.findByStatus("Đang chuẩn bị");
		}
		
		Order order = new Order();
		String deliverMethod = checkoutForm.getDeliverMethod();
		order.setUser(user);
		order.setDeliverMethod(deliverMethod);
		order.setPaymentMethod(checkoutForm.getPaymentMethod());
		order.setOrderTrack(orderTrack);
		order.setOrderDate(new Date());
		order.setCostVAT("0");
		order.setDeliverCost(checkoutForm.getDeliverCost());
		
		orderRepository.save(order);
		List<OrderItem> orderItems = convertToOrderItem(order,cartItems);
		List<OrderItem> orderItemsSaved = orderItemRepository.saveAll(orderItems);
		
		order.setOrderItems(orderItemsSaved);
		orderRepository.save(order);
		// update cart item
		Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		User userSaved = userRepository.findById(userPrincipal.getId()).get();
		userPrincipal.setCartItemNum(String.valueOf(userSaved.getCart().getCartItems().size() - itemsId.size()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return order.getId();
	}

	
	private List<OrderItem> convertToOrderItem(Order order,List<CartItem> cartItems) {
		
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setLaptop(cartItem.getLaptop());
			orderItem.setOrder(order);
			orderItems.add(orderItem);
		}
		return orderItems;
		
	}

	@Override
	public Order getOrderById(Long orderId) {
		
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
										.getPrincipal();
		
		User user = userRepository.findById(userPrincipal.getId()).get();
		if(Objects.isNull(user)) {
			log.error(AppConstant.USER_NOT_FOUND+ userPrincipal.getId());
			return null;
		}
		
	
		
		// check if authenticated user has that order
		Order order = orderRepository.findById(orderId).get();
		if(Objects.isNull(order)) {
			log.error("Not found order");
			return null;
		}
		return order;
	}

	@Override
	public List<OrderShow> getOrderShows() {
		List<User> users = userRepository.findAll();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<OrderShow> orderShows = new ArrayList<>();
		for (User user: users) {
			for (Order order: user.getOrders()) {
				OrderShow orderShow = new OrderShow();
				orderShow.setUsername(user.getUsername());
				orderShow.setOrder(order);
				orderShow.setOrderDate(formatter.format(order.getOrderDate()));
				orderShows.add(orderShow);
			}
		}
		return orderShows;
	}

	@Override
	public OrderShow getOrderShowById(Long orderID) {
		List<Order> temp = orderRepository.findAll();
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

		Order order = getOrderById(orderID);
		OrderShow orderShow = new OrderShow();
		orderShow.setOrder(order);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		orderShow.setOrderDate(order.getOrderDate().toString());
		return orderShow;
	}

	@Override
	public void updateOrder(OrderShow orderShow) {
		Order order = orderRepository.findById(orderShow.getOrder().getId()).get();
		if (Objects.isNull(order)) {
			log.error(AppConstant.BOOK_NOT_FOUND + orderShow.getOrder().getId());
		}
		log.info(orderShow.getOrder().getOrderTrack().getStatus());
		order.setOrderTrack(trackRepository.findById(orderShow.getOrder().getOrderTrack().getId()).get());
		orderRepository.save(order);
	}

	@Override
	public List<Order> getTop3OrderByUser(User user) {
		return orderRepository.findTop3ByUser(user);
	}

	@Override
	public PageResponse<Order> getOrderByUserAndPage(User user, int page) {
		Pageable pageable = PageRequest.of(page, 3); // 3 = size of each page
		Page<Order> orders = orderRepository.findAllByUser(user,pageable);
		PageResponse<Order> pageResponse = new PageResponse<>();
		pageResponse.setContent(orders.getContent());
		pageResponse.setTotalPages(orders.getTotalPages());
		pageResponse.setLast(orders.isLast());
		pageResponse.setFirst(orders.isFirst());
		return pageResponse;
	}

	@Override
	public Boolean ExistByUserAndBook(User user, Long laptopId) {
		Laptop laptop = laptopRepository.findById(laptopId).get();
		List<Order> orders = orderRepository.findByUser(user);
		Boolean result = false;
		for (Order order : orders) {
			result = orderItemRepository.existsByLaptopAndOrder(laptop, order);
			if (result == true) {
				break;
			}
		}
		return result;
	}
}
