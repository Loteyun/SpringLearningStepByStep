package com.yc;

import org.springframework.stereotype.Component;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-04 14:59
 */
@Component
public class HelloWorld {
    public  HelloWorld(){
        System.out.println("无参构造方法");
    }
    public void hello(){
        System.out.println("Hello World");
    }
}
