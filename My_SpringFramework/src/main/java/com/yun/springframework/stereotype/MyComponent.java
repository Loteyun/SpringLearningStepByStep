package com.yun.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-05 15:21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {
    String value() default "";
}
