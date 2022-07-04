package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.csu.laomall.entity.Product;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Product> getProductsByCategoryId(int categoryId, int page, int size) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", categoryId);

        Page<Product> pageProduct = new Page<>(page, size);
        IPage<Product> iPage = productMapper.selectPage(pageProduct, queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Product> searchProduct(String keywords, int page, int size) {
        String[] keywordsArray = keywords.split(" ");
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        for (String keyword : keywordsArray) {
            queryWrapper.like("name", keyword).or().like("detail", keyword).or();
        }
        Page<Product> pageProduct = new Page<>(page, size);
        return productMapper.selectPage(pageProduct, queryWrapper).getRecords();

    }
}

