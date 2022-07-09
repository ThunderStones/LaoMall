package org.csu.laomall.service;

import org.csu.laomall.entity.Product;
import org.csu.laomall.vo.ProductVO;

import java.util.List;

public interface ProductService {
    Product getProductById(int productId);

    List<Product> getProductsByCategoryId(int categoryId, int page, int size);

    List<Product> searchProduct(String keywords,int page,int size, boolean isAdmin);

    List<Product> getAllProduct(Integer page, Integer size);

    Product updateProduct(Product product);

    Product deleteProduct(Product product);

    ProductVO addProduct(ProductVO productVO);

    ProductVO getProductVOById(int id);

    List<Product> searchProduct(String keywords, Integer page, Integer size, boolean b, int categoryId);
}
