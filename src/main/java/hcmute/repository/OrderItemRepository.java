package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import hcmute.model.Laptop;
import hcmute.model.order.Order;
import hcmute.model.order.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

	List<OrderItem> findByLaptop(Laptop laptop);

	Boolean existsByLaptopAndOrder(Laptop laptop, Order order);
=======
import hcmute.model.Book;
import hcmute.model.order.Order;
import hcmute.model.order.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

	List<OrderItem> findByBook(Book book);

	Boolean existsByBookAndOrder(Book book, Order order);
>>>>>>> branch 'master' of https://github.com/quangnghia1110/doancuoiky.git

}
