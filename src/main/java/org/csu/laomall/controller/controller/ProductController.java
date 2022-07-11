package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.entity.Product;
import org.csu.laomall.service.LogService;
import org.csu.laomall.service.ProductService;
import org.csu.laomall.util.JWTUtil;
import org.csu.laomall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private LogService logService;

    @GetMapping("/product/{id}")
    @PassToken
    public CommonResponse<ProductVO> getProductById(@PathVariable int id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = null;
        if (token != null) {
            userId = JWTUtil.getUsername(token.replace("Bearer ", ""));
        }
        logService.log(userId, id);
        ProductVO product = productService.getProductVOById(id);
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
        if (keywords == null) {
            return CommonResponse.createForError("搜索关键字不能为空");
        }
        page = page == null ? 1 : page;
        size = size == null ? 20 : size;
        List<Product> products = productService.searchProduct(keywords, page, size, false);
        return CommonResponse.createForSuccess(products);
    }

    @GetMapping("/category/{categoryId}/search/{keywords}")
    @PassToken
    public CommonResponse<List<Product>> searchProductWithinCatetory(@PathVariable int categoryId,@PathVariable String keywords, Integer page, Integer size) {
        if (keywords == null) {
            return CommonResponse.createForError("搜索关键字不能为空");
        }
        page = page == null ? 1 : page;
        size = size == null ? 20 : size;
        List<Product> products = productService.searchProductWithinCategory(categoryId ,keywords, page, size);
        return CommonResponse.createForSuccess(products);
    }

    @GetMapping("/product/search/all/{keywords}")
    @PassToken
    public CommonResponse<List<Product>> searchAllProduct(@PathVariable String keywords, Integer page, Integer size) {
        if (keywords == null) {
            return CommonResponse.createForError("搜索关键字不能为空");
        }
        page = page == null ? 1 : page;
        size = size == null ? 20 : size;
        List<Product> products = productService.searchProduct(keywords, page, size, true);
        return CommonResponse.createForSuccess(products);
    }



    @GetMapping("/product/all")
    @PassToken
    public CommonResponse<List<Product>> getAllProduct(Integer page, Integer size) {
        List<Product> products = productService.getAllProduct(page, size);
        return CommonResponse.createForSuccess(products);
    }

    @PutMapping("/product/{id}/down")
    public CommonResponse<Product> downProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return CommonResponse.createForError("没有找到该商品");
        } else {
            product.setStatus("已下架");

            return CommonResponse.createForSuccess(productService.updateProduct(product));
        }
    }

    @PutMapping("/product/{id}/up")
    public CommonResponse<Product> upProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return CommonResponse.createForError("没有找到该商品");
        } else {
            product.setStatus("正常");

            return CommonResponse.createForSuccess(productService.updateProduct(product));
        }
    }

    @DeleteMapping("/product/{id}")
    public CommonResponse<Product> deleteProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return CommonResponse.createForError("没有找到该商品");
        } else {
            return CommonResponse.createForSuccess(productService.deleteProduct(product));
        }
    }


    @PostMapping("/product")
    public CommonResponse<ProductVO> addProduct(@RequestBody ProductVO productVO) {
        productVO.setOriginPrice(productVO.getPrice());
        productVO.setCreateTime(new Date());
        productVO.setSales(0);
        productVO.setStatus("正常");
        ProductVO product = productService.addProduct(productVO);
        if (product == null) {
            return CommonResponse.createForError("添加商品失败");
        } else {
            return CommonResponse.createForSuccess(product);
        }
    }

}



