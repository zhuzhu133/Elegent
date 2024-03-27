package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

//自定义Bean名字userShopController
@RestController("userShopController")
@Slf4j
@Api(tags = "店铺相关接口")
@RequestMapping("/user/shop/")
public class ShopController {


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 获取营业状态
     * @return
     */
    @ApiOperation("获取店铺的营业状态")
    @GetMapping("/status")
    public  Result getStatus(){
       Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
