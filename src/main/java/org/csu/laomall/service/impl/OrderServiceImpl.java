package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Order;
import org.csu.laomall.persistence.CartItemMapper;
import org.csu.laomall.persistence.OrderMapper;
import org.csu.laomall.service.AddressService;
import org.csu.laomall.service.CartService;
import org.csu.laomall.service.OrderService;
import org.csu.laomall.vo.CartItemVO;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressService addressService;

    @Override
    @Transactional()
    @TestOnly
    public Order placeOrder(String userId, int addressId) {
        List<CartItemVO> list = cartService.getCartItemByUserId(userId);
        if (list.size() == 0) {
            return null;
        }
        cartService.clearCart(userId);
        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(addressService.getRawString(addressId));
        order.setCreateTime(new java.util.Date());
        order.setStatus("未付款");
        order.setPayStatus("未付款");
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItemVO cartItemVO : list) {
            totalPrice = totalPrice.add(cartItemVO.getTotalPrice());

        }
        order.setPrice(totalPrice);
        orderMapper.insert(order);
        return order;
    }
}
