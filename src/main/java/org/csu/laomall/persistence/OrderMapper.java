package org.csu.laomall.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.laomall.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Order> {

}

