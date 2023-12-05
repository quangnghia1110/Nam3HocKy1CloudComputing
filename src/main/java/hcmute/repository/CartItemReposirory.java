package hcmute.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import hcmute.model.Laptop;
import hcmute.model.CartItem;

@Repository
public interface CartItemReposirory extends JpaRepository<CartItem, Long> {

    List<CartItem> findByLaptop(Laptop book);
=======
import hcmute.model.Book;
import hcmute.model.CartItem;

@Repository
public interface CartItemReposirory extends JpaRepository<CartItem, Long> {

    List<CartItem> findByBook(Book book);
>>>>>>> branch 'master' of https://github.com/quangnghia1110/doancuoiky.git

}