package org.csu.laomall.vo;

import lombok.Data;
import org.csu.laomall.entity.Product;

import java.math.BigDecimal;

@Data
public class CartItemVO {
    private int id;
    private int productId;
    private int quantity;
    private boolean isInStock;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Product product;
    public void calculateTotalPrice() {
        totalPrice = unitPrice.multiply(new BigDecimal(quantity));
    }
}
