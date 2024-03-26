package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，实现切面类的实现类
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点   @annotation(……) ：根据注解匹配    execution(……)：根据方法的签名来匹配
     */

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation( com.sky.annotation.AutoFill)")
    public void autoPointcut(){}

    /**
     * 前置通知
     */
    @Before("autoPointcut()")
    public  void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始填充公共字段");

        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill= signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType =autoFill.value();
        //获取当前被拦截方法的参数  --- 实体对象
        Object[] args = joinPoint.getArgs();
        if (args==null||args.length==0){
            return;
        }
        Object entity=args[0];

        //准备赋值的数据 -- 时间 id
        LocalDateTime now=LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据当前不同的操作类型，为对应的属性通过反射赋值
        if (operationType==OperationType.INSERT){
            //为四个公共字段赋值
            Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
            //通过反射为对象属性赋值
            setCreateTime.invoke(entity,now);
            setCreateUser.invoke(entity,currentId);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);

        } else if (operationType==OperationType.UPDATE) {
            ///为2个公共字段赋值

            Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
            //通过反射为对象属性赋值
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }
    }
}

