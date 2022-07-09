package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.csu.laomall.entity.Category;
import org.csu.laomall.entity.Product;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.service.CategoryService;
import org.csu.laomall.service.ProductService;
import org.csu.laomall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;

    @Override
    public Product getProductById(int productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId, int page, int size) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", categoryId);
        queryWrapper.eq("status", "正常");
        Page<Product> pageProduct = new Page<>(page, size);
        IPage<Product> iPage = productMapper.selectPage(pageProduct, queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Product> searchProduct(String keywords, int page, int size, boolean isAdmin) {
        String[] keywordsArray = keywords.split(" ");
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        for (String keyword : keywordsArray) {
            queryWrapper.or(productQueryWrapper -> productQueryWrapper.like("name", keyword).or().like("detail", keyword));
        }
        if (isAdmin) {
            queryWrapper.ne("status", "删除");
        } else {
            queryWrapper.eq("status", "正常");
        }

        Page<Product> pageProduct = new Page<>(page, size);
        List<Product> products = productMapper.selectPage(pageProduct, queryWrapper).getRecords();
        // 判断keywords 是int
        if (keywords.matches("^[0-9]*$")) {
            int productId = Integer.parseInt(keywords);
            Product product = productMapper.selectById(productId);
            if (product != null) {
                List<Product> products1 = new ArrayList<>();
                products1.add(product);
                products1.addAll(products);
                return products1;
            }
        }
        return products;
    }

    @Override
    public List<Product> getAllProduct(Integer page, Integer size) {

        Page<Product> pageProduct = new Page<>(page, size);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", "删除");
        IPage<Product> iPage = productMapper.selectPage(pageProduct, queryWrapper);
        return iPage.getRecords();
    }

    @Override
    public Product updateProduct(Product product) {
        productMapper.updateById(product);
        return productMapper.selectById(product.getProductId());
    }

    @Override
    public Product deleteProduct(Product product) {
        product.setStatus("删除");
        productMapper.updateById(product);
        return productMapper.selectById(product.getProductId());
    }

    @Override
    @Transactional
    public ProductVO addProduct(ProductVO productVO) {
        Category category1 = categoryService.getCategoryByName(productVO.getCategoryName());
        if (category1 == null) {
            category1 = new Category();
            category1.setName(productVO.getCategoryName());
            category1.setLevel(1);
            category1.setParentId(0);
            categoryService.addCategory(category1);
        }
        Product product = productvo2product(productVO);

        product.setCategoryId(category1.getCategoryId());
        productMapper.insert(product);
        productVO.setProductId(product.getProductId());
        productVO.setCategoryId(category1.getCategoryId());
        return productVO;
    }

    @Override
    public ProductVO getProductVOById(int id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            return null;
        }
        Category category = categoryService.getCategoryById(product.getCategoryId());
        ProductVO productVO = new ProductVO(product, category.getName());
        return productVO;
    }

    @Override
    public List<Product> searchProduct(String keywords, Integer page, Integer size, boolean b, int categoryId) {
        String[] keywordsArray = keywords.split(" ");
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        if (!b) {
            queryWrapper.eq("status", "下架").or().eq("status", "正常");
        } else {
            queryWrapper.eq("status", "正常");
        }
        for (String keyword : keywordsArray) {
            queryWrapper.like("name", keyword).or().like("detail", keyword).or();
        }
        if (categoryId != 0) {
            queryWrapper.eq("category", categoryId);
        }
        Page<Product> pageProduct = new Page<>(page, size);
        return productMapper.selectPage(pageProduct, queryWrapper).getRecords();
    }

    private Product productvo2product(ProductVO productVO) {
        Product product = new Product();
        product.setName(productVO.getName());
        product.setDetail(productVO.getDetail());
        product.setPrice(productVO.getPrice());
        product.setOriginPrice(productVO.getOriginPrice());
        product.setTag(productVO.getTag());
        product.setInventory(productVO.getInventory());
        product.setSales(0);
        product.setCreateTime(productVO.getCreateTime());
        product.setStatus("正常");
        product.setImgUrl(productVO.getImgUrl());
        return product;
    }

    private ProductVO product2productVO(Product product) {
        Category category = categoryService.getCategoryById(product.getCategoryId());
        return new ProductVO(product, category.getName());
    }
}

