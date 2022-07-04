package org.csu.laomall.service;

import org.csu.laomall.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(int productId);

    List<Product> getProductsByCategoryId(int categoryId);

    List<Product> searchProduct(String keyword);
}
