package com.lagou.web.servlet;

import com.lagou.base.Constans;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;
import com.lagou.utils.UUIDUtils;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/courseSalesInfo")
public class CourseSalesInfoServlet extends HttpServlet {
    /**
     * 保存课程营销信息
     * 手机表单的数据，封装到course对象中，将图片上传到服务器
     */

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.创建course对象
        Course course = new Course();

        //2.创建一个map对象
        Map<String, Object> map = new HashMap<>();

        //3.编写文件上传的代码 创建磁盘工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //4.创建文件上传核心对象
        ServletFileUpload fileUpload = new ServletFileUpload(factory);

        //5.解析request对象，获取表单项集合
        List<FileItem> list = fileUpload.parseRequest(req);

        //6.遍历集合 判断哪些是普通表单项，哪些是文件上传
        for (FileItem item : list) {
            boolean formField = item.isFormField();
            if (formField) {
                //是普通表单项，获取表单项中的数据
                String fieldName = item.getFieldName();
                String value = item.getString("UTF-8");
                System.out.println(fieldName + "   " + value);
                map.put(fieldName, value);
            } else {
                //否则是文件上传项
                //获取文件名
                String fileName = item.getName();

                String newFileName = UUIDUtils.getUUID() + "_" + fileName;

                //获取输入流
                InputStream in = item.getInputStream();

                //获取路径
                String realPath = this.getServletContext().getRealPath("/");
                String webappsPath = realPath.substring(0, realPath.indexOf("lagou"));

                //创建输出流
                OutputStream out = new FileOutputStream(webappsPath + "/upload/" + newFileName);

                //拷贝
                IOUtils.copy(in, out);
                out.close();
                in.close();

                map.put("course_img_url", Constans.LOCAL_URL+"/upload/" + newFileName);
            }
        }
        //使用beanUtils,把map中的数据封装到course对象
        BeanUtils.populate(course, map);
        String dateFormart = DateUtils.getDateFormart();

        if (map.get("id") != null) {
            //修改操作
            //不全信息
            course.setUpdate_time(dateFormart);
            CourseService cs = new CourseServiceImpl();
            String result = cs.updateCourseSalesInfo(course);
            resp.getWriter().print(result);

        }else {
            //补全信息

            course.setCreate_time(dateFormart);
            course.setUpdate_time(dateFormart);

            //状态信息
            course.setStatus(1);

            //业务处理
            CourseService cs = new CourseServiceImpl();
            String result = cs.saveCourseSaleInfo(course);
            //响应结果
            resp.getWriter().println(result);
        }





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }
}