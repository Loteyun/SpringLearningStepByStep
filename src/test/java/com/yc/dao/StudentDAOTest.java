package com.yc.dao;

import com.yc.biz.StudentBizImpl;
import junit.framework.TestCase;

public class StudentDAOTest extends TestCase {
    private StudentDAO studentDAO;
    private StudentBizImpl studentBizImpl;

    @Override
    public void setUp(){
        studentDAO=new StudentDAOImpl();
        studentBizImpl =new StudentBizImpl();
        studentBizImpl.setStudentBizImpl(studentDAO);
    }

    public void testAdd() {
        studentDAO.add("张三");
    }

    public void testUpdate() {
        studentDAO.update("张三");
    }
    public void testBizAdd(){
        studentBizImpl.add("张三");
    }
}