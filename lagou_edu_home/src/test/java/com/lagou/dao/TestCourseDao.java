package com.lagou.dao;

import com.lagou.dao.impl.CourseDaoImpl;
import com.lagou.pojo.Course;
import com.lagou.utils.DateUtils;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestCourseDao {

    CourseDao courseDao = new CourseDaoImpl();
    //测试课程列表查询
    @Test
    public void testFindCourseList() throws SQLException {
        List<Course> courseList = courseDao.findCourseList();
        System.out.println(courseList);
    }

    //测试根据课程名查询方法
    @Test
    public void testFindByCourseNameAndStatus() throws SQLException {
        List<Course> courseList = courseDao.findByCourseNameAndStatus("微服", "");

        for (Course course : courseList) {
            System.out.println(course.getId() +"  "+ course.getCourse_name() + "   "
                    +course.getPrice()+"  " + course.getStatus());
        }
    }


    @Test
    public void testUpdateCourse() {
        //1.根据id查询课程信息
        Course course = courseDao.findCourseById(2);

        course.setCourse_name("سىناق ئۇچۇر");
        String s = DateUtils.getDateFormart();
        course.setUpdate_time(s);
        int row = courseDao.updateCourseSalesInfo(course);
        System.out.println(row);
    }
}
