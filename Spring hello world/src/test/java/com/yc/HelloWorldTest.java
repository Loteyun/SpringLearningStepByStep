package com.yc;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldTest extends TestCase {
    private ApplicationContext ac;
    @Override
    @Before
    public void setUp() throws Exception {
        ac=new AnnotationConfigApplicationContext(AppConfig.class);
    }
    @Test
    public void testHello() {
        HelloWorld hw=(HelloWorld) ac.getBean("helloWorld");
        hw.hello();

    }
}