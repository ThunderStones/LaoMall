package org.csu.laomall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("order_info")
public class Order {
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;
    @TableField("user_id")
    private String userId;
    @TableField("price")
    private BigDecimal price;
    @TableField("status")
    private String status;// 未支付 未发货 已发货 已签收
    @TableField("pay_status")
    private String payStatus;// 未支付 已支付
    @TableField("pay_type")
    private String payType;// 支付宝 微信
    @TableField("address")
    private String address;
    @TableField("pay_time")
    private Date payTime;
    @TableField("other_info")
    private String comment;
    @TableField("create_time")
    private Date createTime;

}
