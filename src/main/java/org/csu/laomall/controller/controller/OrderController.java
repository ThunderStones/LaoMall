package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Order;
import org.csu.laomall.service.OrderService;
import org.csu.laomall.vo.OrderVO;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public CommonResponse<Order> placeOrder(HttpServletRequest request, @RequestParam int addressId) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        Order order = orderService.placeOrder(userId, addressId);
        if (order == null) {
            return CommonResponse.createForError("购物车为空");
        } else {
            return CommonResponse.createForSuccess(order);
        }

    }

    @PostMapping("/ids")
    public CommonResponse<Order> placeOrder(HttpServletRequest request, @RequestParam int addressId, @RequestBody Integer[] CartIds) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        Order order = orderService.placeOrderWithIds(userId, addressId, CartIds);
        if (order == null) {
            return CommonResponse.createForError("该用户购物车找不到购物车Id对应的记录");
        } else {
            return CommonResponse.createForSuccess(order);
        }
    }



        @GetMapping("/list")
    public CommonResponse<List<Order>> getOrderList(HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        List<Order> orderList = orderService.getOrderListByUserId(userId);
        return CommonResponse.createForSuccess(orderList);
    }

    @GetMapping("/{orderId}")
    public CommonResponse<OrderVO> getOrderDetail(HttpServletRequest request, @PathVariable("orderId") int orderId) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        OrderVO order = orderService.getOrderDetailByOrderId(orderId);
        if (order == null) {
            return CommonResponse.createForError("订单不存在");
        } else if (!order.getUserId().equals(userId)) {
            return CommonResponse.createForError("订单不属于该用户");
        } else {
            return CommonResponse.createForSuccess(order);
        }
    }

    @PostMapping("/{orderId}/payment")
    public CommonResponse<Order> payOrder(HttpServletRequest request, @PathVariable("orderId") int orderId, String payType) {
        if (!payType.equals("alipay") && !payType.equals("wechat") && !payType.equals("other")) {
            return CommonResponse.createForError("支付方式参数错误");
        }
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        Order order = orderService.getOrderByOrderId(orderId);
        if (order == null) {
            return CommonResponse.createForError("订单不存在");
        } else if (!order.getUserId().equals(userId)) {
            return CommonResponse.createForError("订单不属于该用户");
        } else {
            order = orderService.payOrder(orderId, payType);
            return CommonResponse.createForSuccess(order);
        }
    }

    @PostMapping("/{orderId}/receipt")
    public CommonResponse<Order> receiptOrder(HttpServletRequest request, @PathVariable("orderId") int orderId) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        Order order = orderService.getOrderByOrderId(orderId);
        if (order == null) {
            return CommonResponse.createForError("订单不存在");
        } else if (!order.getUserId().equals(userId)) {
            return CommonResponse.createForError("订单不属于该用户");
        } else {
            order = orderService.receiptOrder(order);
            return CommonResponse.createForSuccess(order);
        }
    }

    @PostMapping("/{orderId}/cancellation")
    public CommonResponse<Order> cancellationOrder(HttpServletRequest request, @PathVariable("orderId") int orderId) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        Order order = orderService.getOrderByOrderId(orderId);
        if (order == null) {
            return CommonResponse.createForError("订单不存在");
        } else if (!order.getUserId().equals(userId)) {
            return CommonResponse.createForError("订单不属于该用户");
        } else if (order.getStatus().equals("已取消")) {
            return CommonResponse.createForError("该订单已取消");
        } else if (order.getStatus().equals("已收货")) {
            return CommonResponse.createForError("该订单已收货，不能取消");
        } else {

            order = orderService.cancelOrder(order);
            return CommonResponse.createForSuccess(order);
        }
    }

    @GetMapping("/list/all")
    @PassToken
    public CommonResponse<List<Order>> getOrderList() {
        List<Order> orderList = orderService.getOrderList();
        return CommonResponse.createForSuccess(orderList);
    }
}
