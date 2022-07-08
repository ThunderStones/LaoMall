package org.csu.laomall.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.csu.laomall.entity.OrderItem;
import org.csu.laomall.entity.Product;

import java.math.BigDecimal;
@Data
public class OrderItemVO {
    private Integer orderItemId;
    private int orderId;
    private int productId;
    private BigDecimal price;
    private int num;
    private BigDecimal totalPrice;
    private Product product;

    public OrderItemVO(OrderItem orderItem) {
        orderItemId = orderItem.getOrderItemId();
        orderId = orderItem.getOrderId();
        productId = orderItem.getProductId();
        price = orderItem.getPrice();
        num = orderItem.getNum();
        totalPrice = orderItem.getTotalPrice();
    }
}
