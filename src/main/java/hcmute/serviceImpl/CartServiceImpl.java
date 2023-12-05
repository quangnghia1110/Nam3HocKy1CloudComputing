package hcmute.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.model.Book;
import hcmute.model.Cart;
import hcmute.model.CartItem;
import hcmute.model.user.User;
import hcmute.repository.BookRepository;
import hcmute.repository.CartItemReposirory;
import hcmute.repository.CartReposiroty;
import hcmute.repository.UserRepository;
import hcmute.service.ICartService;
import hcmute.utils.AppConstant;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    @Autowired
    CartReposiroty cartReposiroty;

    @Autowired
    CartItemReposirory cartItemReposirory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public Cart getCartByUser(Long userId) {
        User user = userRepository.findById(userId).get();
        if (Objects.isNull(user)) {
            log.error(AppConstant.USER_NOT_FOUND + userId);
            return null;
        }
        Optional<Cart> cart = cartReposiroty.findById(user.getCart().getId());
        return cart.get();
    }

    @Override
    public void addToCart(User user, Long bookId, int quantity) {

        Optional<Cart> cart = cartReposiroty.findById(user.getCart().getId());

        if (Objects.isNull(cart)) {
            log.error(AppConstant.CART_NOT_FOUND + user.getId());
            return;
        }

        List<CartItem> cartItems = cart.get().getCartItems();

        for (CartItem cartItem : cartItems) {
            if (bookId == cartItem.getBook().getId()) {
                // increase if already have cartItem with current book id
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemReposirory.save(cartItem);
                return;
            }
        }

        // If user's cart not have current book with the id

        Book book = bookRepository.findById(bookId).get();
        if (Objects.isNull(book)) {
            log.error(AppConstant.BOOK_NOT_FOUND + bookId);
            return;
        }
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart.get());
        cartItemReposirory.save(cartItem);
    }

    @Override
    public int getStatus(List<String> cartItems) {
        for (String id : cartItems) {
            CartItem cartItem = cartItemReposirory.findById(Long.parseLong(id)).get();
            if (cartItem.getQuantity() > cartItem.getBook().getInventory().getQuantiy()) {
                return Integer.parseInt(id);
            } else if (cartItem.getBook().getAvailable() == false) {
                return Integer.parseInt(id);
            }
        }
        return 0;
    }

}