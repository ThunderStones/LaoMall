package org.csu.laomall.controller.controller;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.service.LogService;
import org.csu.laomall.vo.LogVO;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/logs")
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/all")
    @PassToken
    public CommonResponse<List<LogVO>> getAllLog() {
        List<LogVO> logs = logService.getAllLog();
        return CommonResponse.createForSuccess(logs);
    }

    @GetMapping("/user")
    public CommonResponse<List<LogVO>> getLogByUsername(HttpServletRequest request) {
        String userId = ((UserVO) request.getAttribute("user")).getUserId();
        List<LogVO> logs = logService.getLogByUsername(userId);
        return CommonResponse.createForSuccess(logs);
    }

}
