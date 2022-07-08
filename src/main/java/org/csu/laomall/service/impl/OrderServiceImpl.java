package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.laomall.entity.Order;
import org.csu.laomall.entity.OrderItem;
import org.csu.laomall.entity.Product;
import org.csu.laomall.persistence.OrderItemMapper;
import org.csu.laomall.persistence.OrderMapper;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.service.AddressService;
import org.csu.laomall.service.CartService;
import org.csu.laomall.service.OrderService;
import org.csu.laomall.vo.CartItemVO;
import org.csu.laomall.vo.OrderItemVO;
import org.csu.laomall.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private ProductMapper productMapper;

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
            Product product = productMapper.selectById(cartItemVO.getProductId());
            product.setInventory(product.getInventory() - cartItemVO.getQuantity());
            product.setSales(product.getSales() + cartItemVO.getQuantity());
            productMapper.updateById(product);
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
    public OrderVO getOrderDetailByOrderId(int orderId) {
        Order order = orderMapper.selectById(orderId);
        List<OrderItem> orderItemList = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
        List<OrderItemVO> orderItemVOS = new ArrayList<>();
        orderItemList.forEach(orderItem -> {
            OrderItemVO orderItemVO = new OrderItemVO(orderItem);
            Product product = productMapper.selectById(orderItem.getProductId());
            orderItemVO.setProduct(product);
            orderItemVOS.add(orderItemVO);
        });
        return new OrderVO(order, orderItemVOS);
    }
    @Override
    public Order payOrder(int orderId, String payType) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return null;
        }
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

    @Override
    public List<Order> getOrderList() {
        return orderMapper.selectList(null);

    }
}
