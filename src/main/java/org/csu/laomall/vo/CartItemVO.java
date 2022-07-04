package org.csu.laomall.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemVO {
    private int id;
    private int productId;
    private int quantity;
    private boolean isInStock;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public void calculateTotalPrice() {
        totalPrice = unitPrice.multiply(new BigDecimal(quantity));
    }
}
