package com.lagou.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class baseServlet extends HttpServlet {
    /**
     * doget方法作为一个调度器，根据请求功能的不同，调用对应的方法
     *      规定必须传递一个参数
     *      methodName = "功能名"
     *
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取方法名
        String methodName = request.getParameter("methodName");


        //2.判断执行对应的方法
        if (methodName != null) {
            //通过反射优化代码 提升代码的维护性

            try {
                //1.获取字节码对象  这里的this = testServlet
                Class c = this.getClass();

                //2.根据方法名获取方法对象
                Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

                //3.调用method对象的invoke方法对应的方法对象
                method.invoke(this, request, response);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("请求的功能不存在！");
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
