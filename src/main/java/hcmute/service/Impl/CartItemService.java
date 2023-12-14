package hcmute.service.Impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.model.CartItem;
import hcmute.repository.CartItemReposirory;
import hcmute.service.ICartItemService;
import hcmute.utils.AppConstant;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartItemService implements ICartItemService {

    @Autowired
    CartItemReposirory cartItemReposirory;

    @Override
    public void deleteById(Long itemId) {
        cartItemReposirory.deleteById(itemId);

    }

    @Override
    public void updateItem(Long itemId, int currItemNum) {
        CartItem item = cartItemReposirory.findById(itemId).get();
        if (Objects.isNull(item)) {
            log.info(AppConstant.CART_ITEM_NOT_FOUND + itemId);
        }
        item.setQuantity(currItemNum);
        cartItemReposirory.save(item);
    }

    @Override
    public CartItem getItemById(long itemId) {
        CartItem item = cartItemReposirory.findById(itemId).get();
        if (Objects.isNull(item)) {
            log.info(AppConstant.CART_ITEM_NOT_FOUND + itemId);
        }
        return item;
    }

}