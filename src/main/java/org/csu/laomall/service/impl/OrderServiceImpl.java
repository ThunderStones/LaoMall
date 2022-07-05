package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Order;
import org.csu.laomall.entity.OrderItem;
import org.csu.laomall.persistence.OrderItemMapper;
import org.csu.laomall.persistence.OrderMapper;
import org.csu.laomall.service.AddressService;
import org.csu.laomall.service.CartService;
import org.csu.laomall.service.OrderService;
import org.csu.laomall.vo.CartItemVO;
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
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional()

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
        order.setPrice(new BigDecimal(0));
        BigDecimal totalPrice = new BigDecimal(0);
        orderMapper.insert(order);
        for (CartItemVO cartItemVO : list) {
            totalPrice = totalPrice.add(cartItemVO.getTotalPrice());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setProductId(cartItemVO.getProductId());
            orderItem.setPrice(cartItemVO.getUnitPrice());
            orderItem.setNum(cartItemVO.getQuantity());
            orderItem.setTotalPrice(cartItemVO.getTotalPrice());
            orderItemMapper.insert(orderItem);
        }
        order.setPrice(totalPrice);
        orderMapper.updateById(order);
        return order;
    }

    @Override
    public List<Order> getOrderListByUserId(String userId) {
        return orderMapper.selectList(new QueryWrapper<Order>().eq("user_id", userId));
    }

    @Override
    public Order getOrderByOrderId(int orderId) {
        return orderMapper.selectById(orderId);
    }

    @Override
    public Order payOrder(int orderId, String payType) {
        Order order = orderMapper.selectById(orderId);
        order.setPayStatus("已付款");
        switch (payType) {
            case "alipay":
                order.setPayType("支付宝");
                break;
            case "wechat":
                order.setPayType("微信");
                break;
            case "other":
                order.setPayType("其他");
                break;
        }
        order.setPayTime(new java.util.Date());
        order.setStatus("送货中");
        orderMapper.updateById(order);
        return orderMapper.selectById(orderId);
    }

    @Override
    public Order receiptOrder(Order order) {
        order.setStatus("已收货");
        order.setReceiptTime(new java.util.Date());
        orderMapper.updateById(order);
        return orderMapper.selectById(order.getOrderId());
    }

    @Override
    public Order cancelOrder(Order order) {
        order.setStatus("已取消");
        orderMapper.updateById(order);
        return orderMapper.selectById(order.getOrderId());
    }
}
