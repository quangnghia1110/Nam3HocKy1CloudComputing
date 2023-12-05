package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.order.Order;
import hcmute.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String string);

	User findByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	String findUsernameById(Long userId);

	User findByOrders(Order order);


}
