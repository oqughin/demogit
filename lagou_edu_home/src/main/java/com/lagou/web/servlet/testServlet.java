package com.lagou.web.servlet;

import com.lagou.base.baseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 当前的servlet对应的是课程管理模块
 */
@WebServlet("/test")
public class testServlet extends baseServlet {


    //添加课程的方法
    public void addCourse(HttpServletRequest request, HttpServletResponse response){
        System.out.println("新建课程");
    }

     //根据课程名查询课程
    public void findByName(HttpServletRequest request, HttpServletResponse response){
        System.out.println("根据课程名查询课程");
    }

    //根据课程状态查询课程
    public void findByStatus(HttpServletRequest request, HttpServletResponse response){
        System.out.println("根据课程状态查询");
    }



}
