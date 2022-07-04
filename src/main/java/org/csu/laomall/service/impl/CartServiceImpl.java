package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.CartItem;
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
        queryWrapper.eq("userId", userId);
        List<CartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        List<CartItemVO> cartItemVOList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setId(cartItem.getCartItemId());
            cartItemVO.setProductId(cartItem.getProductId());
            cartItemVO.setQuantity(cartItem.getQuantity());
            cartItemVO.setInStock(productMapper.selectById(cartItem.getProductId()).getInventory() > 0);
            cartItemVO.setUnitPrice(productMapper.selectById(cartItem.getProductId()).getPrice());
            cartItemVO.calculateTotalPrice();
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
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("productId", productId);
        if (cartItemMapper.selectOne(queryWrapper) == null) {
            cartItemMapper.insert(cartItem);
        } else {
            cartItemMapper.update(cartItem, queryWrapper);
        }
    }

    @Override
    public void removeItem(String userId, String productId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("productId", productId);
        cartItemMapper.delete(queryWrapper);
    }

    @Override
    public void clearCart(String userId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        cartItemMapper.delete(queryWrapper);
    }
}
