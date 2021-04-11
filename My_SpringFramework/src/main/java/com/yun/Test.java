package com.yun;

import com.yun.bean.HelloWorld;
import com.yun.springframework.context.MyAnnotationConfigApplicationContext;
import com.yun.springframework.stereotype.MyApplicationContext;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-05 15:33
 */
public class Test {
    public static void main(String[] args) {
        MyApplicationContext ac=new MyAnnotationConfigApplicationContext(MyAppConfig.class);
        HelloWorld hw=(HelloWorld)ac.getBean("hw");
        hw.hello();
    }
}
