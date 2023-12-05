package hcmute.service;

import java.util.List;

import hcmute.model.Cart;
import hcmute.model.user.User;

public interface ICartService {

    Cart getCartByUser(Long userId);

    void addToCart(User user, Long bookId, int quantity);

    int getStatus(List<String> cartItems);

}
