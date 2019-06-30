package com.itheima.dao.impl;

import com.itheima.dao.RouteDao;
import com.itheima.domain.*;
import com.itheima.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    /*人气*/
    @Override
    public List<Route> hotRoutes() {
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route WHERE rflag = 1 ORDER BY COUNT DESC LIMIT 0 ,4", new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /*最新*/
    @Override
    public List<Route> newRoutes() {
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route WHERE rflag = 1 ORDER BY rdate DESC LIMIT 0 ,4", new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /*主题*/
    @Override
    public List<Route> themeRoutes() {
        List<Route> list = jdbcTemplate.query("SELECT * FROM tab_route WHERE rflag = 1 AND isThemeTour = 1 LIMIT 0 ,4", new BeanPropertyRowMapper<>(Route.class));
        return list;
    }

    /*分页导航*/
    //cid和rname可能有可能没有，需要判断
    @Override
    public int getTotalCount(String cid, String rname) {
        String sql = "select count(*) from tab_route  WHERE rflag = 1 ";
        List<String> params = new ArrayList<>();
        if (cid != null && !"".equals(cid)) {
            sql += " and cid = ? ";
            params.add(cid);
        }
        if (rname != null && !"".equals(rname)) {
            sql += " AND rname LIKE ?";
            params.add("%" + rname + "%");
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
    }

    /*当前页的数据集合*/
    //cid和rname可能有可能没有，需要判断
    @Override
    public List<Route> search(String cid, String rname, int index, int pageSize) {
        String sql = "SELECT * FROM tab_route WHERE rflag = 1 ";
        List<Object> params = new ArrayList<>();
        if (cid != null && !"".equals(cid)) {
            sql += " and cid = ? ";
            params.add(cid);
        }
        if (rname != null && !"".equals(rname)) {
            sql += " AND rname LIKE ?";
            params.add("%" + rname + "%");
        }
        sql += "limit ?,?";
        params.add(index);
        params.add(pageSize);

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    /*查询路线信息*/
    @Override
    public Route findById(String rid) {
        Route route = jdbcTemplate.queryForObject("SELECT * FROM tab_route WHERE rid = ?", new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }

    /*查询商家信息*/
    @Override
    public Seller findSellerById(Integer sid) {
        Seller seller = jdbcTemplate.queryForObject("SELECT * FROM tab_seller WHERE sid = ?", new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }

    /*查询分类信息*/
    @Override
    public Category findCategoryById(Integer cid) {
        Category category = jdbcTemplate.queryForObject("SELECT * FROM tab_category WHERE cid = ?", new BeanPropertyRowMapper<>(Category.class), cid);
        return category;
    }

    /*查询路线的图片合集*/
    @Override
    public List<RouteImg> findImg(String rid) {
        List<RouteImg> routeImg = jdbcTemplate.query("SELECT * FROM tab_route_img WHERE rid = ?", new BeanPropertyRowMapper<>(RouteImg.class), rid);
        return routeImg;
    }

    /*总共收藏多少数据*/
    @Override
    public int getTotalCount2(RankOV ov) {
        String sql = "SELECT COUNT(*) FROM tab_route WHERE rflag = 1 ";
        List<Object> params = new ArrayList<>();
        if (ov.getRname() != null && !"".equals(ov.getRname())) {
            sql += " AND rname LIKE ? ";
            params.add("%"+ov.getRname()+"%");
        }
        if (ov.getMinprice() != null && !"".equals(ov.getMinprice())) {
            sql += " AND price >= ? ";
            params.add(ov.getMinprice());
        }
        if(ov.getMaxprice() != null && !"".equals(ov.getMaxprice())){
            sql += " AND price <= ?";
            params.add(ov.getMaxprice());
        }
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, params.toArray());
        return count;
    }

    /*当前收藏页的数据集合*/
    @Override
    public List<Route> rankRoutes(RankOV ov, int index, int pageSize) {
        String sql = "SELECT * FROM tab_route WHERE rflag = 1 ";
        List<Object> params = new ArrayList<>();
        if (ov.getRname() != null && !"".equals(ov.getRname())) {
            sql += " AND rname LIKE ? ";
            params.add("%"+ov.getRname()+"%");
        }
        if (ov.getMinprice() != null && !"".equals(ov.getMinprice())) {
            sql += " AND price >= ? ";
            params.add(ov.getMinprice());
        }
        if(ov.getMaxprice() != null && !"".equals(ov.getMaxprice())){
            sql += " AND price <= ?";
            params.add(ov.getMaxprice());
        }
        sql += " ORDER BY COUNT DESC LIMIT ?,?";
        params.add(index);
        params.add(pageSize);
        List<Route> routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        return routeList;
    }

}
