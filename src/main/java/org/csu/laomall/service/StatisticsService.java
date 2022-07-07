package org.csu.laomall.service;

import org.csu.laomall.entity.Product;
import org.csu.laomall.vo.StatisticsVO;

import java.text.ParseException;
import java.util.List;

public interface StatisticsService {
    StatisticsVO getStatistics(String date);
    StatisticsVO getStatistics();

    List<Product> getSales();
}
