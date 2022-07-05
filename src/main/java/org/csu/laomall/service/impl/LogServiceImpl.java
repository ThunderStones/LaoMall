package org.csu.laomall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.laomall.entity.Log;
import org.csu.laomall.persistence.LogMapper;
import org.csu.laomall.persistence.ProductMapper;
import org.csu.laomall.service.LogService;
import org.csu.laomall.vo.LogVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void log(String username, int productId) {
        Log log = new Log();
        log.setUserId(username);
        log.setProductId(productId);
        log.setLogDate(new Date());
        logMapper.insert(log);
    }

    @Override
    public List<LogVO> getAllLog() {
        List<Log> logs = logMapper.selectList(null);
        return getLogVOS(logs);
    }

    @Override
    public List<LogVO> getLogByUsername(String username) {
        List<Log> logs = logMapper.selectList(new QueryWrapper<Log>().eq("user_id", username));
        return getLogVOS(logs);
    }

    @NotNull
    private List<LogVO> getLogVOS(List<Log> logs) {
        List<LogVO> logVOs = new ArrayList<>();
        for (Log log : logs) {
            LogVO logVO = new LogVO(log);
            logVO.setProduct(productMapper.selectById(log.getProductId()));
            logVOs.add(logVO);
        }
        return logVOs;
    }
}
