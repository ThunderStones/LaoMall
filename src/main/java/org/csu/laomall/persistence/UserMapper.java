package org.csu.laomall.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.laomall.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
