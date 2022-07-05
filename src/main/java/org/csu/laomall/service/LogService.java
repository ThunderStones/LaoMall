package org.csu.laomall.service;

import org.csu.laomall.entity.Log;
import org.csu.laomall.vo.LogVO;

import java.util.List;

public interface LogService{
    void log(String username, int productId);

    List<LogVO> getAllLog();

    List<LogVO> getLogByUsername(String username);
}
