package org.csu.laomall.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.csu.laomall.entity.Order;
import org.csu.laomall.entity.OrderItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    private Integer orderId;
    private String userId;
    private BigDecimal price;
    private String status;// 未支付 未发货 已发货 已签收
    private String payStatus;// 未支付 已支付
    private String payType;// 支付宝 微信
    private String address;
    private Date payTime;
    private String comment;
    private Date createTime;
    private Date receiptTime;
    private List<OrderItemVO> orderItemList;

    public OrderVO(Order order, List<OrderItemVO> orderItemList) {
        orderId = order.getOrderId();
        userId = order.getUserId();
        price = order.getPrice();
        status = order.getStatus();
        payStatus = order.getPayStatus();
        payType = order.getPayType();
        address = order.getAddress();
        payTime = order.getPayTime();
        comment = order.getComment();
        createTime = order.getCreateTime();
        receiptTime = order.getReceiptTime();
        this.orderItemList = orderItemList;

    }
}
