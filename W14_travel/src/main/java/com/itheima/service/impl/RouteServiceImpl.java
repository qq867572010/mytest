package com.itheima.service.impl;

import com.itheima.dao.RouteDao;
import com.itheima.dao.impl.RouteDaoImpl;
import com.itheima.domain.*;
import com.itheima.service.RouteService;
import com.itheima.util.PageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements RouteService {

    private RouteDao dao = new RouteDaoImpl();

    /*黑马精选：旅游选项*/
    @Override
    public Map<String, List<Route>> usefulRoute() {
        //人气
        List<Route> hotRoutes = dao.hotRoutes();
        //最新
        List<Route> newRoutes = dao.newRoutes();
        //主题
        List<Route> themeRoutes = dao.themeRoutes();

        Map<String,List<Route>> map = new HashMap<>();
        map.put("hotRoutes",hotRoutes);
        map.put("newRoutes",newRoutes);
        map.put("themeRoutes",themeRoutes);

        return map;
    }

    /*分页导航*/
    @Override
    public PageBean<Route> search(String cid, String rname, int pageNumber, int pageSize) {
        PageBean<Route> pageBean = new PageBean<>();

        //1.当前页码
        pageBean.setPageNumber(pageNumber);
        //2.每页多少条
        pageBean.setPageSize(pageSize);
        //3.总共多少数据
        int totalCount = dao.getTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //4.分了多少页
        int pageCount = PageUtils.calcPageCount(totalCount, pageSize);
        pageBean.setPageCount(pageCount);
        //5.当前页的数据集合
        int index = PageUtils.calcSqlLimitIndex(pageNumber, pageSize);
        List<Route> routeList = dao.search(cid,rname,index,pageSize);
        pageBean.setData(routeList);
        //6.页码条从几开始显示
        int[] pagination = PageUtils.pagination(pageNumber, pageCount);
        pageBean.setStart(pagination[0]);
        //7.页码条从几结束
        pageBean.setEnd(pagination[1]);

        return pageBean;
    }

    /*路线列表页面*/
    @Override
    public Route routeDetail(String rid) {
        //1.查询路线信息
        Route route = dao.findById(rid);
        //2.查询商家信息
        Seller seller = dao.findSellerById(route.getSid());
        route.setSeller(seller);
        //3.查询分类信息
        Category category = dao.findCategoryById(route.getCid());
        route.setCategory(category);
        //4.查询路线的图片合集
        List<RouteImg> routeImg = dao.findImg(rid);
        route.setRouteImgList(routeImg);

        return route;
    }

    /*收藏排行榜*/
    @Override
    public PageBean<Route> rankRoutes(RankOV ov, int pageNumber, int pageSize) {
        PageBean<Route> pageBean = new PageBean<>();

        /*当前页码 */
        pageBean.setPageNumber(pageNumber);
        /*每页多少条*/
        pageBean.setPageSize(pageSize);
        /*总共多少数据*/
        int totalCount = dao.getTotalCount2(ov);
        pageBean.setTotalCount(totalCount);
        /*分了多少页*/
        int pageCount = PageUtils.calcPageCount(totalCount, pageSize);
        pageBean.setPageCount(pageCount);
        /*页码条从几开始显示*/
        int[] pagination = PageUtils.pagination(pageNumber, pageCount);
        pageBean.setStart(pagination[0]);
        /*页码条显示到几结束*/
        pageBean.setEnd(pagination[1]);
        /*当前页的数据集合*/
        int index = PageUtils.calcSqlLimitIndex(pageNumber,pageSize);
        List<Route> data = dao.rankRoutes(ov,index,pageSize);
        pageBean.setData(data);
        return pageBean;
    }
}
