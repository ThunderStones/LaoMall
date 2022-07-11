package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.CartItem;
import org.csu.laomall.entity.Product;
import org.csu.laomall.persistence.CartItemMapper;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.service.CartService;
import org.csu.laomall.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<CartItemVO> getCartItemByUserId(String userId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<CartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        List<CartItemVO> cartItemVOList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            Product product = productMapper.selectById(cartItem.getProductId());
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setId(cartItem.getCartItemId());
            cartItemVO.setProductId(cartItem.getProductId());
            cartItemVO.setQuantity(cartItem.getQuantity());
            cartItemVO.setInStock(product.getInventory() > 0);
            cartItemVO.setUnitPrice(product.getPrice());
            cartItemVO.calculateTotalPrice();
            cartItemVO.setProduct(product);
            cartItemVOList.add(cartItemVO);
        }
        return cartItemVOList;
    }

    @Override
    public void addItem(String userId, int productId, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(Math.min(quantity, 99));
        cartItem.setUpdateTime(new Date());
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        if (cartItemMapper.selectOne(queryWrapper) == null) {
            cartItemMapper.insert(cartItem);
        } else {
            cartItemMapper.update(cartItem, queryWrapper);
        }
    }

    @Override
    public boolean removeItem(String userId, int productId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        return cartItemMapper.delete(queryWrapper) > 0;
    }

    @Override
    public void clearCart(String userId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        cartItemMapper.delete(queryWrapper);
    }

    @Override
    public CartItemVO getCartItemByUserIdAndCartId(String userId, Integer cartId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("cart_item_id", cartId);
        CartItem cartItem = cartItemMapper.selectOne(queryWrapper);
        Product product = productMapper.selectById(cartItem.getProductId());
        CartItemVO cartItemVO = new CartItemVO();
        cartItemVO.setId(cartItem.getCartItemId());
        cartItemVO.setProductId(cartItem.getProductId());
        cartItemVO.setQuantity(cartItem.getQuantity());
        cartItemVO.setInStock(product.getInventory() > 0);
        cartItemVO.setUnitPrice(product.getPrice());
        cartItemVO.calculateTotalPrice();
        cartItemVO.setProduct(product);
        return cartItemVO;
    }
}
