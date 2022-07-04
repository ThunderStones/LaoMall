package org.csu.laomall.controller.controller;

import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.service.CartService;
import org.csu.laomall.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PutMapping("/items")
    public CommonResponse<String> addItems(@RequestBody List<CartItemVO> cartItems, HttpServletRequest request) {
        return null;
    }
}
