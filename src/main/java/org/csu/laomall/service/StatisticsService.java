package org.csu.laomall.service;

import org.csu.laomall.vo.StatisticsVO;

import java.text.ParseException;

public interface StatisticsService {
    StatisticsVO getStatistics(String date);
    StatisticsVO getStatistics();
}
