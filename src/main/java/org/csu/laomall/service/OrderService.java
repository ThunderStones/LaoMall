package org.csu.laomall.service;

import org.csu.laomall.entity.Order;
import org.csu.laomall.vo.OrderVO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order placeOrder(String userId, int addressId);

    List<Order> getOrderListByUserId(String userId);

    Order getOrderByOrderId(int orderId);

    Order payOrder(int orderId, String payType);

    Order receiptOrder(Order order);

    Order cancelOrder(Order order);

    List<Order> getOrderList();

    OrderVO getOrderDetailByOrderId(int orderId);

    Order placeOrderWithIds(String userId, int addressId, Integer[] cartIds);
}
