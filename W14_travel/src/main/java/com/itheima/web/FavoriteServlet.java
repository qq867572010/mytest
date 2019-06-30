package com.itheima.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.PageBean;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.FavoriteService;
import com.itheima.service.RouteService;
import com.itheima.service.impl.FavoriteServiceImpl;
import com.itheima.service.impl.RouteServiceImpl;
import com.itheima.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/favorite", name = "FavoriteServlet")
public class FavoriteServlet extends BaseServlet {

    private FavoriteService service = new FavoriteServiceImpl();
    private RouteService routeService = new RouteServiceImpl();

    /**
     * 判断路线是否被收藏：
     * 1. 未登录：未收藏
     * 2. 已登录，未收藏：未收藏
     * 3. 已登录，已收藏：已收藏
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user != null) {
                //已登录
                //1.接收参数
                String rid = request.getParameter("rid");
                //3.完成功能
                boolean isFavorite = service.isFavorite(rid, user);
                //4.处理结果
                info = new ResultInfo(true, isFavorite, "");
            } else {
                //未登录，未收藏
                info = new ResultInfo(true, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统忙，请稍后");
        }

        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*添加收藏*/
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user != null) {
                //已登录
                //1.接收参数
                String rid = request.getParameter("rid");
                //3.完成功能
                boolean bn = service.addFavorite(rid, user);
                //4.处理结果
                if (bn) {
                    //已登录，收藏成功
                    //查询收藏数量
                    Route route = routeService.routeDetail(rid);
                    info = new ResultInfo(true, route, "");
                } else {
                    //已登录，收藏失败
                    info = new ResultInfo(true, -2);
                }
            } else {
                //未登录，未收藏
                info = new ResultInfo(true, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统忙，请稍后");
        }

        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }

    /*查看我的收藏*/
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = null;
        try {
            //1.接收信息，登录用户信息，pageNumber
            String pageNumberStr = request.getParameter("pageNumber");
            User user = (User) request.getSession().getAttribute("loginUser");
            if (user != null) {
                //用户已登录
                int pageNumber = 1;
                int pageSize = 12;
                //判断并转换类型
                if (pageNumberStr != null && !"".equals(pageNumberStr)) {
                    pageNumber = Integer.parseInt(pageNumberStr);
                }
                //2.完成功能
                PageBean<Route> pageBean = service.myFavorite(user, pageNumber, pageSize);
                //3.处理结果
                info = new ResultInfo(true,pageBean);
            } else {
                //用户未登录
                info = new ResultInfo(true,-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            info = new ResultInfo(false, "系统忙，请稍后");
        }

        //转换并返回
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().print(json);
    }
}
