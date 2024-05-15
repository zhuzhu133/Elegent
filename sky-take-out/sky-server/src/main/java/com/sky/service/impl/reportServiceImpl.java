package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.reportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class reportServiceImpl implements reportService {


    @Autowired
    private UserMapper userMapper;


    @Autowired
    private OrderMapper orderMapper;
    @Override
    public TurnoverReportVO getTurnover(LocalDate beginTime, LocalDate endTime) {
        TurnoverReportVO turnoverReportVO=new TurnoverReportVO();
        //dataList存放从begin到end的日期
        List<LocalDate> dataList=new ArrayList<>();
        //添加日期
        dataList.add(beginTime);
        while(!beginTime.equals(endTime)){
            beginTime=beginTime.plusDays(1);
            dataList.add(beginTime);
        }
        //转换类型
        String s= StringUtils.join(dataList,",");
        turnoverReportVO.setDateList(s);

        //turnoverList存放每一天对应的营业额
        List<Double> turnoverList = new ArrayList<>();
        //查询每一天的营业额
        for (LocalDate data:dataList) {
            //营业额：已完成的

            //begin表示data这一天的0点
            LocalDateTime begin=LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime end=LocalDateTime.of(data, LocalTime.MAX);
            Map map = new HashMap();
            map.put("status", Orders.COMPLETED);
            map.put("begin",begin);
            map.put("end", end);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        String s1= StringUtils.join(turnoverList,",");
        turnoverReportVO.setTurnoverList(s1);
        return turnoverReportVO;
    }

    @Override
    public UserReportVO userStatistics(LocalDate beginTime, LocalDate endTime) {
        UserReportVO userReportVO=new UserReportVO();
        //dataList存放从begin到end的日期
        List<LocalDate> dataList=new ArrayList<>();
        //添加日期
        dataList.add(beginTime);
        while(!beginTime.equals(endTime)){
            beginTime=beginTime.plusDays(1);
            dataList.add(beginTime);
        }
        //转换类型
        String s= StringUtils.join(dataList,",");

        userReportVO.setDateList(s);

        List<Integer> newUserList = new ArrayList<>(); //新增用户数
        List<Integer> totalUserList = new ArrayList<>(); //总用户数

        for (LocalDate data:dataList) {
            LocalDateTime begin=LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime end=LocalDateTime.of(data, LocalTime.MAX);
            //新增用户数量 select count(id) from user where create_time > ? and create_time < ?
            Integer newUser = getUserCount(begin, end);
            //总用户数量 select count(id) from user where  create_time < ?
            Integer totalUser = getUserCount(null, end);

            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }
        userReportVO.setNewUserList(StringUtils.join(newUserList,","));
        userReportVO.setTotalUserList(StringUtils.join(totalUserList,","));
        return userReportVO;
    }

    /**
     * 根据时间区间统计用户数量
     * @param beginTime
     * @param endTime
     * @return
     */
    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        Map map = new HashMap();
        map.put("begin",beginTime);
        map.put("end", endTime);
        return userMapper.countByMap(map);
    }
}
