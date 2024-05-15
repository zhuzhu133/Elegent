package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.reportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 *  数据统计相关接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/report")
@Api(tags = "统计报表相关接口")
public class ReportController {

    @Autowired
    private reportService reportService;
    /**
     * 营业额数据统计
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("营业额数据统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
         TurnoverReportVO turnoverReportVO= reportService.getTurnover(begin,end);
        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("营业额数据统计")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
     UserReportVO userReportVO = reportService.userStatistics(begin,end);
        return Result.success(userReportVO);
    }
}
