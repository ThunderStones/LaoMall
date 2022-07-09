package org.csu.laomall.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csu.laomall.entity.Product;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class ProductVO {
    private Integer productId;
    private String name;
    private String detail;
    private BigDecimal originPrice;
    private BigDecimal price;
    private String tag;
    private int inventory;
    private int sales;
    private Date createTime;
    private String status;
    private int categoryId;
    private String imgUrl;
    private String categoryName;

    public ProductVO(Product product, String categoryName) {
        productId = product.getProductId();
        name = product.getName();
        detail = product.getDetail();
        originPrice = product.getOriginPrice();
        price = product.getPrice();
        tag = product.getTag();
        inventory = product.getInventory();
        sales = product.getSales();
        createTime = product.getCreateTime();
        status = product.getStatus();
        categoryId = product.getCategoryId();
        imgUrl = product.getImgUrl();
        this.categoryName = getCategoryName();
    }

}
