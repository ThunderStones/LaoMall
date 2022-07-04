package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Product;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(int productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        return productMapper.selectList(new QueryWrapper<Product>().eq("category", categoryId));
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return null;
    }
}

