package com.yun.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-08 20:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyResource{
    String name();
}
