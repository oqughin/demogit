package com.lagou.dao.impl;

import com.lagou.dao.CourseDao;
import com.lagou.pojo.Course;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程模块DAO接口的实现类
 */

public class CourseDaoImpl implements CourseDao {
    @Override
    public List<Course> findCourseList() throws SQLException {

            //1.创建queryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            //2.编写SQL 判断是否删除 取出is_del值为零的数据
            String sql = "SELECT id,course_name,price,sort_num,status FROM course WHERE is_del = ?";

            //3.执行查询
            List<Course> courseList = qr.query(sql, new BeanListHandler<Course>(Course.class), 0);

            return courseList;

    }

    //根据课程名称和状态查询
    @Override
    public List<Course> findByCourseNameAndStatus(String courseName, String status) {
        List<Course> courseList = null;
        try {
            //1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            //2.编写SQL 当前的查询时多项不定项查询
            //2.1 创建StringBuffer对象，将SQL字符串添加进缓冲区
            StringBuffer sb = new StringBuffer("SELECT id,course_name,price,sort_num,status FROM course WHERE 1=1 and is_del = ? ");

            //创建list集合保存参数
            List<Object> params = new ArrayList<>();
            params.add(0);

            //判断传入的的参数是否为空
            if (status != null && !"".equals(status)) {
                sb.append(" and status = ?");
                Integer i = Integer.valueOf(status);
                params.add(i);
            }

            if (courseName != null && !"".equals(courseName)) {
                sb.append(" and course_name Like ?");
                courseName = "%" + courseName + "%";
                params.add(courseName);
            }

            //执行查询
            courseList = qr.query(sb.toString(), new BeanListHandler<Course>(Course.class), params.toArray());
            //返回结果
            return courseList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


    }

    //保存课程营销信息
    @Override
    public int savaCourseSaleInfo(Course course) {
        //1.创建Query Runner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        //2.编写SQL语句
        String sql = "INSERT INTO course(\n" +
                "course_name,\n" +
                "brief,\n" +
                "teacher_name,\n" +
                "teacher_info,\n" +
                "preview_first_field,\n" +
                "preview_second_field,\n" +
                "discounts,\n" +
                "price,\n" +
                "price_tag,\n" +
                "share_image_title,\n" +
                "share_title,\n" +
                "share_description,\n" +
                "course_description,\n" +
                "course_img_url,\n" +
                "STATUS,\n" +
                "create_time,\n" +
                "update_time\n" +
                ")VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        List<Object> params = new ArrayList<Object>();
        Object[] param = {course.getCourse_name(),course.getBrief(),course.getTeacher_name(),course.getTeacher_info(),
                course.getPreview_first_field(),course.getPreview_second_field(),course.getDiscounts(),course.getPrice(),
                course.getPrice_tag(),course.getShare_image_title(),course.getShare_title(),course.getShare_description(),
                course.getCourse_description(),course.getCourse_img_url(),course.getStatus(),course.getCreate_time(),course.getUpdate_time()};
        //4.执行插入操作
        try {
            int row = qr.update(sql, param);
            return row;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }


    }

    //查询课程营销信息
    @Override
    public Course findCourseById(int id) {
        //1.创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        //2.编写Sql语句
        String sql = "select id ,\n" +
                "course_name,\n" +
                "brief,\n" +
                "teacher_name,\n" +
                "teacher_info,\n" +
                "preview_first_field,\n" +
                "preview_second_field,\n" +
                "discounts,price,\n" +
                "price_tag,\n" +
                "course_img_url,\n" +
                "share_title,\n" +
                "share_image_title,\n" +
                "share_description,\n" +
                "course_description,\n" +
                "status\n" +
                "from course where id = ? ;";


        //3.执行查询
        try {
            Course course = qr.query(sql, new BeanHandler<Course>(Course.class), id);
            return course;

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }



    }

    //修改课程营销信息
    @Override
    public int updateCourseSalesInfo(Course course) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "UPDATE course SET \n" +
                    "course_name = ?,\n" +
                    "brief = ?,\n" +
                    "teacher_name = ?,\n" +
                    "teacher_info = ?,\n" +
                    "preview_first_field = ?,\n" +
                    "preview_second_field = ?,\n" +
                    "discounts = ?,\n" +
                    "price = ?,\n" +
                    "price_tag = ?,\n" +
                    "share_image_title = ?,\n" +
                    "share_title = ?,\n" +
                    "share_description = ?,\n" +
                    "course_description = ?,\n" +
                    "course_img_url = ?,\n" +
                    "update_time = ? \n" +
                    "WHERE id = ?";


            Object[] params = {course.getCourse_name(),course.getBrief(),course.getTeacher_name(),course.getTeacher_info(),
                    course.getPreview_first_field(),course.getPreview_second_field(),course.getDiscounts(),course.getPrice(),
                    course.getPrice_tag(),course.getShare_image_title(),course.getShare_title(),course.getShare_description(),course.getCourse_description(),
                    course.getCourse_img_url(),course.getUpdate_time(),course.getId()};


            int row = qr.update(sql, params);
            return row;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }

    }

    @Override
    public int updateCourseStatus(Course course) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course set status = ? , update_time = ?  where id = ?";
            Object[] params = {course.getStatus(), course.getUpdate_time(), course.getId()};
            int row = qr.update(sql, params);
            return row;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }
}
