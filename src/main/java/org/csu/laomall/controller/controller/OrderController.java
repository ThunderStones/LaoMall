package org.csu.laomall.controller.controller;

import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Order;
import org.csu.laomall.service.OrderService;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public CommonResponse<Order> placeOrder(HttpServletRequest request, @RequestParam(required = true) int addressId) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        Order order = orderService.placeOrder(userId, addressId);
        if (order == null) {
            return CommonResponse.createForError("购物车为空");
        } else {
            return CommonResponse.createForSuccess(order);
        }

    }

}
