package com.yun.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-05 14:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MyComponentScan {
    String[] basePackages() default{};
}
