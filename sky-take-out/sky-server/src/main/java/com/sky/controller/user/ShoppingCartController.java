package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "C端-购物车接口")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车，商品信息为：{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车列表
     * @return
     */
    @GetMapping("/list")
    public Result getList(){
        List<ShoppingCart> list=new ArrayList<>();
        list=shoppingCartService.showShoppingCart();
        return Result.success(list);
    }

    /**
     * 清空购物车商品
     * @return
     */
   @DeleteMapping("/clean")
   @ApiOperation("清空购物车商品")
    public  Result clearList(){
     shoppingCartService.cleanShoppingCart();
    return Result.success();
    }


    @PostMapping("/sub")
    public  Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
       shoppingCartService.subShoppingCart(shoppingCartDTO);
       return Result.success();
    }


}
