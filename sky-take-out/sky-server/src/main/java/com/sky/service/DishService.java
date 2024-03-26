package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult pageList(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);

    DishVO getById(Long id);

    void updateWithFlavor(DishDTO dishDTO);
}
