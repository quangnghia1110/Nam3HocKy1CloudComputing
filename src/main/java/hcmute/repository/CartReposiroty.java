package hcmute.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Cart;
import hcmute.model.user.User;

@Repository
public interface CartReposiroty extends JpaRepository<Cart, Long> {

	Optional<Cart> findById(Long cartId);

}
