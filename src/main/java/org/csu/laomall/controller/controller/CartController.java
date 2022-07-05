package org.csu.laomall.controller.controller;

import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.service.CartService;
import org.csu.laomall.vo.CartItemVO;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PutMapping("/items")
    public CommonResponse<List<CartItemVO>> addItems(@RequestBody CartItemVO[] itemIds, HttpServletRequest request) {
        System.out.println(Arrays.toString(itemIds));
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        for (CartItemVO item : itemIds) {
            cartService.addItem(userId, item.getProductId(), item.getQuantity());

        }
        return CommonResponse.createForSuccess(cartService.getCartItemByUserId(userId));
    }

    @GetMapping("/items")
    public CommonResponse<List<CartItemVO>> getCartItems(HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        return CommonResponse.createForSuccess(cartService.getCartItemByUserId(userId));
    }

    @DeleteMapping("/items")
    public CommonResponse<List<CartItemVO>> removeItems(@RequestBody int[] itemIds, HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        for (int item : itemIds) {
            cartService.removeItem(userId, item);
        }
        return CommonResponse.createForSuccess(cartService.getCartItemByUserId(userId));
    }
}
