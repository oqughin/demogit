package com.lagou.dao;

import com.lagou.pojo.Course;

import java.sql.SQLException;
import java.util.List;

/**
 * 课程模块DAO层接口
 */
public interface CourseDao {
    //课程信息查询
    public List<Course> findCourseList() throws SQLException;

    //根据课程名称和状态查询
    public List<Course> findByCourseNameAndStatus(String courseName, String status) throws SQLException;

    //保存课程营销信息
    public int savaCourseSaleInfo(Course course);

    //查询课程营销信息
    public Course findCourseById(int id);

    //修改课程营销信息
    public int updateCourseSalesInfo (Course course);

    //修改课程状态
    public int updateCourseStatus(Course course);
}
