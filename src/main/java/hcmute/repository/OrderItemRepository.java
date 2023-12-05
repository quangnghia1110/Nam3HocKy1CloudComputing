package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Book;
import hcmute.model.order.Order;
import hcmute.model.order.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

	List<OrderItem> findByBook(Book book);

	Boolean existsByBookAndOrder(Book book, Order order);

}
