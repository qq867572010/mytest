package com.itheima.web.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取action的值
        String action = request.getParameter("action");
        try {
            //使用反射调用action方法
            Class cs = this.getClass();
            Method method = cs.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
