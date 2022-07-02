package org.csu.laomall.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.laomall.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMapper extends BaseMapper<Product> {

}

