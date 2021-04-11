package com.yun.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-10 15:46
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyService {
}
