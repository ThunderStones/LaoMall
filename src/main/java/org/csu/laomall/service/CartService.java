package org.csu.laomall.service;

import org.csu.laomall.vo.CartItemVO;

import java.util.List;

public interface CartService {
    List<CartItemVO> getCartItemByUserId(String userId);

    void addItem(String userId, int productId, int quantity);

    void removeItem(String userId, String productId);

    void clearCart(String userId);
}
