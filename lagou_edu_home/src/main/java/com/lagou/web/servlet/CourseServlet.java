package com.lagou.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.lagou.base.baseServlet;
import com.lagou.dao.impl.CourseDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/course")
public class CourseServlet extends baseServlet {

    //查询课程信息列表
    public void findCourseList(HttpServletRequest request, HttpServletResponse response) {
        //1.接受参数

        try {
            //2.业务处理
            CourseService cs = new CourseServiceImpl();
            List<Course> courseList = cs.findCourseList();

            //3.响应结果
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name",
                                                                             "price", "sort_num","status");
            String result = JSON.toJSONString(courseList,filter);

            //4.响应

            response.getWriter().print(result);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据课程名查询课程信息
    public void findByCourseNameAndStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.接收参数
            String courseName = request.getParameter("courseName");
            String status = request.getParameter("status");
            //2.返回结果
            CourseService cs = new CourseServiceImpl();
            List<Course> courseList = cs.findByCourseNameAndStatus(courseName, status);
            //3.响应结果
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name",
                    "price", "sort_num","status");
            String result = JSON.toJSONString(courseList,filter);
            //4.响应
            response.getWriter().print(result);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = request.getParameter("id");
            CourseService cs = new CourseServiceImpl();
            Course course = cs.findCourseById(Integer.parseInt(id));

            String result = JSON.toJSONString(course);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateCourseStatus(HttpServletRequest request, HttpServletResponse response) {

        try {
            String id = request.getParameter("id");
            CourseService cs = new CourseServiceImpl();
            Map<String, Integer> result = cs.updateCourseStatus(Integer.parseInt(id));
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
