package com.yc.biz;

import com.yc.dao.StudentDAO;

/**
 * @program: SpringTest
 * @description:
 * @author: Loteyun
 * @create: 2021-04-04 19:54
 */
public class StudentBizImpl {
    private StudentDAO studentDao;

    public StudentBizImpl(StudentDAO studentDAO){
        this.studentDao=studentDAO;
    }

    public StudentBizImpl() {

    }
    public void setStudentBizImpl(StudentDAO studentDAO){
        this.studentDao=studentDAO;
    }

    public int add(String name){
        System.out.println("======业务层======");
        System.out.println("用户名是否重名");
        int result=studentDao.add(name);
        System.out.println("======业务操作借宿======");
        return result;
    }
    public void update(String name){
        System.out.println("======业务层======");
        System.out.println("用户名是否重名");
        studentDao.update(name);
        System.out.println("======业务操作借宿======");
    }


}
