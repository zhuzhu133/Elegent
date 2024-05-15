package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface reportService {


    TurnoverReportVO getTurnover(LocalDate beginTime, LocalDate endTime);

    UserReportVO userStatistics(LocalDate begin, LocalDate end);
}
