package com.itheima.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.ResultInfo;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/category", name = "CategoryServlet")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    /*导航页上分类*/
    public void queryAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //3.完成功能
            String categories = service.queryAll();
            info = new ResultInfo(true, categories, "");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统忙，请稍后访问");
        }
        //将info转换成json并返回客户端
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }
}
