package org.csu.laomall.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.laomall.entity.CartItem;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemMapper extends BaseMapper<CartItem> {

}

