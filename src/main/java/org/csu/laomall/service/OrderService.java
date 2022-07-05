package org.csu.laomall.service;

import org.csu.laomall.entity.Order;

public interface OrderService {
    Order placeOrder(String userId, int addressId);
}
