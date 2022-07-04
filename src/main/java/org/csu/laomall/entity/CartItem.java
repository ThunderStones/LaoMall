package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cart_item")
public class CartItem {
    @TableId(value = "cart_item_id", type = IdType.AUTO)
    private Integer cartItemId;
    @TableField("user_id")
    private String userId;
    @TableField("product_id")
    private int productId;
    @TableField("quantity")
    private int quantity;
    @TableField("update_time")
    private Date updateTime;
}
