package org.csu.laomall.controller.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.AlipayConst;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Order;
import org.csu.laomall.service.OrderService;
import org.csu.laomall.vo.OrderVO;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @PostMapping ("/alipayCallback")
    @PassToken
    public CommonResponse<Order> alipayCallback(HttpServletRequest request) {
        System.out.println("11111111111111111111111111111111111111111111");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }
        System.out.println(Integer.parseInt(params.get("out_trade_no")));
        System.out.println("222222222222222222222222222222222222222222");
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConst.ALIPAY_PUBLIC_KEY, AlipayConst.CHARSET, AlipayConst.SIGN_TYPE);
        } catch (AlipayApiException e) {
            return CommonResponse.createForError("验证签名失败");
        }
        if (signVerified) {
            Order order = orderService.payOrder(Integer.parseInt(params.get("out_trade_no")), "alipay");
            return CommonResponse.createForSuccess(order);
        } else {
            return CommonResponse.createForError("验签失败");
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

    @GetMapping("/alipay/{orderId}")
    @PassToken
    public CommonResponse<String> getAlipayUrl(@PathVariable("orderId") int orderId, @RequestParam String returnUrl) {
        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConst.GATEWAY_URL,
                AlipayConst.APP_ID,
                AlipayConst.APP_PRIVATE_KEY,
                AlipayConst.FORMAT,
                AlipayConst.CHARSET,
                AlipayConst.ALIPAY_PUBLIC_KEY,
                AlipayConst.SIGN_TYPE);

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(AlipayConst.NOTIFY_URL);

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + orderId + "\"," +
                "\"total_amount\":\"0.01\"," +
                "\"subject\":\"订单支付\"," +
                "\"body\":\"订单支付\"," +
                "\"timeout_express\":\"10m\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println(form);
        return CommonResponse.createForSuccess(form);
    }
}
