package com.lagou.service;

import com.lagou.pojo.Course;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 课程管理模块Service层接口
 */
public interface CourseService {
    public List<Course> findCourseList() throws SQLException;

    public List<Course> findByCourseNameAndStatus(String courseName,String status) throws SQLException;

    public String saveCourseSaleInfo(Course course);

    public Course findCourseById(int id);

    public String updateCourseSalesInfo(Course course);

    public Map<String,Integer> updateCourseStatus(int id);
}
