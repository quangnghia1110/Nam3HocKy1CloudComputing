package hcmute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Cart;
import hcmute.model.user.User;

@Repository
public interface CartReposiroty extends JpaRepository<Cart, Long> {

	Cart findByUser(User user);

}
