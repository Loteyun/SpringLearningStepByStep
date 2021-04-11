package com.yc.dao;

import java.util.Random;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-04 19:51
 */
public class StudentDaoMyBatisIml implements StudentDAO{
    @Override
    public int add(String name) {
       System.out.println("mybatis 添加学生:"+name);
        Random r=new Random();
        return r.nextInt();
    }

    @Override
    public void update(String name) {
        System.out.println("myabatis 更新学生:"+name);
    }
}
