package com.yun;

import com.yun.bean.HelloWorld;
import com.yun.springframework.stereotype.MyBean;
import com.yun.springframework.stereotype.MyComponentScan;
import com.yun.springframework.stereotype.MyConfiguration;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-05 15:31
 */
@MyConfiguration
@MyComponentScan(basePackages = {"com.yun.bean","com.yun.biz"})
public class MyAppConfig {
    @MyBean
    public HelloWorld hw(){
        return new HelloWorld();
    }
    @MyBean
    public HelloWorld hw2(){
        return new HelloWorld();
    }
}
