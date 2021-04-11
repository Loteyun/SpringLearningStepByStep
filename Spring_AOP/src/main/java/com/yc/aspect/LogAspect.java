package com.yc.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-09 20:25
 */

@Aspect //切面类：你要增强的功能写到这里
@Component//IOC注解，以实现spring托管功能
public class LogAspect {
    //切入点申明 pointcut signture
    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))")
    private void add(){

    }
    @Pointcut("execution(* com.yc.biz.StudentBizImpl.update*(..))")
    private void update(){

    }
    @Pointcut("add()|| update()")
    private void addAndupdate(){

    }
    @Before("com.yc.aspect.LogAspect.add()")
    public void log(){
        System.out.println("=============前置增强的日志=============");
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dstr =sdf.format(d);
    }

}
