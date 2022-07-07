package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.service.StatisticsService;
import org.csu.laomall.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("")
    @PassToken
    public CommonResponse<StatisticsVO> getStatistics() {
        StatisticsVO statisticsVO = statisticsService.getStatistics();
        if (statisticsVO == null) {
            return CommonResponse.createForError("没有找到统计数据");
        } else {
            return CommonResponse.createForSuccess(statisticsVO);
        }
    }
    @GetMapping("/{time}")
    @PassToken
    public CommonResponse<StatisticsVO> getStatistics(@PathVariable String time) {
        if ("today".equals(time) || "yesterday".equals(time) || "week".equals(time) || "month".equals(time)) {
            StatisticsVO statisticsVO = statisticsService.getStatistics(time);
            if (statisticsVO == null) {
                return CommonResponse.createForError("没有找到统计数据");
            } else {
                return CommonResponse.createForSuccess(statisticsVO);
            }
        } else {
            return CommonResponse.createForError("时间格式错误");
        }
    }
}
