package org.csu.laomall.service;

import org.csu.laomall.vo.CartItemVO;

import java.util.List;

public interface CartService {
    List<CartItemVO> getCartItemByUserId(String userId);

    void addItem(String userId, int productId, int quantity);

    boolean removeItem(String userId, int productId);

    void clearCart(String userId);

    CartItemVO getCartItemByUserIdAndCartId(String userId, Integer cartId);
}
