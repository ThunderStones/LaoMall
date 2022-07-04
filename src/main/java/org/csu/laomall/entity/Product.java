package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@TableName("product")
public class Product {
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;
    @TableField("name")
    private String name;
    @TableField("detail")
    private String detail;
    @TableField("ori_price")
    private BigDecimal originPrice;
    @TableField("price")
    private BigDecimal price;
    @TableField("tag")
    private String tag;
    @TableField("inventory")
    private int inventory;
    @TableField("sales")
    private int sales;
    @TableField("create_time")
    private Date createTime;
    @TableField("status")
    private String status;
    @TableField("category")
    private int categoryId;
}
