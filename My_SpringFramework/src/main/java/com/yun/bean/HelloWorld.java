package com.yun.bean;

import com.yun.springframework.stereotype.MyPostConStruct;
import com.yun.springframework.stereotype.MyPreDestroy;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-05 14:37
 */

public class HelloWorld {
    @MyPostConStruct
    public void setup(){
        System.out.println("MyPostConstruct");
    }

    @MyPreDestroy
    public void destory(){
        System.out.println("MyPreDestory");
    }
    public HelloWorld(){
        System.out.println("Hello 无参构造函数");
    }

    public void hello(){
        System.out.println("Hello World!");
    }
}
