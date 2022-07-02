package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("order_item")
public class OrderItem {
    @TableId(value = "order_item_id", type = IdType.AUTO)
    private Integer orderItemId;
    @TableField("order_id")
    private int orderId;
    @TableField("product_id")
    private int productId;
    @TableField("price")
    private BigDecimal price;
    @TableField("num")
    private int num;
    @TableField("total_price")
    private BigDecimal totalPrice;
}
