package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.PageBean;
import com.itheima.domain.RankOV;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.Route;
import com.itheima.service.RouteService;
import com.itheima.service.impl.RouteServiceImpl;
import com.itheima.util.BeanUtils;
import com.itheima.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/route", name = "RouteServlet")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();

    /*黑马精选：旅游选项*/
    public void usefulRoutes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1.接收参数
            //2.封装实体
            //3.完成功能
            Map<String, List<Route>> map = service.usefulRoute();
            info = new ResultInfo(true, map);
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统忙，请稍后");
        }
        //4.处理结果
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*分页导航*/
    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1.接收参数
            String cid = request.getParameter("cid");
            String rname = request.getParameter("rname");
            String pageNumberstr = request.getParameter("pageNumber");
            //设置参数
            int pageSize = 8;
            int pageNumber = 1;
            //判断
            if (pageNumberstr != null && !"".equals(pageNumberstr)) {
                pageNumber = Integer.parseInt(pageNumberstr);
            }
            //2.封装实体
            //3.完成功能
            PageBean<Route> pageBean = service.search(cid, rname, pageNumber, pageSize);
            //4.处理结果
            info = new ResultInfo(true, pageBean, "");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统繁忙，请稍后访问");
        }

        //info转换json
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*路线列表页面*/
    public void routeDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1.获取参数
            String rid = request.getParameter("rid");
            //2.封装实体
            //3.完成功能
            Route route = service.routeDetail(rid);
            //4.处理结果
            info = new ResultInfo(true, route, "");
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统忙，请稍后");
        }

        //转换并返回参数
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*收藏排行榜*/
    public void rankRoutes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1.获取参数
            String pageNumberStr = request.getParameter("pageNumber");
            int pageSize = 8;
            int pageNumber = 1;
            if (pageNumberStr != null && !"".equals(pageNumberStr)) {
                pageNumber = Integer.parseInt(pageNumberStr);
            }
            Map<String, String[]> map = request.getParameterMap();
            //2.封装实体
            RankOV ov = BeanUtils.populate(map, RankOV.class);
            //3.完成功能
            PageBean<Route> pageBean = service.rankRoutes(ov,pageNumber,pageSize);
            //4.处理结果
            info = new ResultInfo(true,pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false,"系统忙，请稍后");
        }

        //转换并返回
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }
}
