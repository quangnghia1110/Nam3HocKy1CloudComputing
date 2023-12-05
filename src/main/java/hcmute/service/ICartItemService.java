
package hcmute.service;

import hcmute.model.CartItem;

public interface ICartItemService {

    void deleteById(Long itemId);

    void updateItem(Long itemId, int currItemNum);

    CartItem getItemById(long parseLong);

}