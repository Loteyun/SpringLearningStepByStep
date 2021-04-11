package com.yc.dao;

import java.util.Random;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-04 14:55
 */

public class StudentDAOImpl implements StudentDAO {

    @Override
    public int add(String name) {
        System.out.println("jpa 添加学生"+name);
        Random rd=new Random();
        return rd.nextInt();
    }

    @Override
    public void update(String name) {
        System.out.println("jpa 更新学生"+name);
    }
}
