package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hcmute.model.Laptop;
import hcmute.model.CartItem;

@Repository
public interface CartItemReposirory extends JpaRepository<CartItem, Long> {

    List<CartItem> findByLaptop(Laptop book);

}