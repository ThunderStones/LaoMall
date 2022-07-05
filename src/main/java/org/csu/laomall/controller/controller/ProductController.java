package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Product;
import org.csu.laomall.service.LogService;
import org.csu.laomall.service.ProductService;
import org.csu.laomall.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private LogService logService;

    @GetMapping("/product/{id}")
    @PassToken
    public CommonResponse<Product> getProductById(@PathVariable int id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = null;
        if (token != null) {
            userId = JWTUtil.getUsername(token.replace("Bearer ", ""));
        }
        logService.log(userId, id);
        Product product = productService.getProductById(id);
        if (product == null) {
            return CommonResponse.createForError("没有找到该商品");
        } else {
            return CommonResponse.createForSuccess(product);
        }
    }

    @GetMapping("/products/categoryId/{categoryId}")
    @PassToken
    public CommonResponse<List<Product>> getProductsByCategoryId(@PathVariable int categoryId, Integer page, Integer size) {
        page = page == null ? 1 : page;
        size = size == null ? 20 : size;
        List<Product> products = productService.getProductsByCategoryId(categoryId, page, size);
        if (products == null) {
            return CommonResponse.createForError("没有找到该分类");
        } else {
            return CommonResponse.createForSuccess(products);
        }
    }

    @GetMapping("/product/search/{keywords}")
    @PassToken
    public CommonResponse<List<Product>> searchProduct(@PathVariable String keywords, Integer page, Integer size) {
        page = page == null ? 1 : page;
        size = size == null ? 20 : size;
        List<Product> products = productService.searchProduct(keywords, page, size);
        return CommonResponse.createForSuccess(products);
    }
}



