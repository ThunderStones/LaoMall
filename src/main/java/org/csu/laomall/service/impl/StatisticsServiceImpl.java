package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Log;
import org.csu.laomall.entity.Order;
import org.csu.laomall.entity.Product;
import org.csu.laomall.entity.User;
import org.csu.laomall.persistence.LogMapper;
import org.csu.laomall.persistence.OrderMapper;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.persistence.UserMapper;
import org.csu.laomall.service.StatisticsService;
import org.csu.laomall.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public StatisticsVO getStatistics(String date) {
        StatisticsVO statisticsVO = new StatisticsVO();
        Date date1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        switch (date) {
            case "today":
                try {
                    date1 = new Date(simpleDateFormat.parse(simpleDateFormat.format(new Date().getTime())).getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "yesterday":
                date1 = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
                break;
            case "week":
                date1 = new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000);
                break;
            case "month":
                date1 = new Date(new Date().getTime() - 30L * 24 * 60 * 60 * 1000);
                break;
        }
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("create_date", date1);
        statisticsVO.setAccessCount(logMapper.selectCount(queryWrapper));
        statisticsVO.setNewUserCount(userMapper.selectCount(new QueryWrapper<User>().gt("register_date", date1)));
        statisticsVO.setProductCount(productMapper.selectCount(new QueryWrapper<Product>().gt("create_time", date1)));
        statisticsVO.setOrderCount(orderMapper.selectCount(new QueryWrapper<Order>().gt("create_time", date1)));
        return statisticsVO;
    }

    @Override
    public StatisticsVO getStatistics() {
        StatisticsVO statisticsVO = new StatisticsVO();
        statisticsVO.setAccessCount(logMapper.selectCount(null));
        statisticsVO.setNewUserCount(userMapper.selectCount(null));
        statisticsVO.setProductCount(productMapper.selectCount(null));
        statisticsVO.setOrderCount(orderMapper.selectCount(null));
        return statisticsVO;
    }
}

