package org.csu.laomall.service;

import org.csu.laomall.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(String userId, int addressId);

    List<Order> getOrderListByUserId(String userId);

    Order getOrderByOrderId(int orderId);

    Order payOrder(int orderId, String payType);

    Order receiptOrder(Order order);

    Order cancelOrder(Order order);
}
