package com.itheima.dao.impl;

import com.itheima.dao.FavoriteDao;
import com.itheima.domain.Favorite;
import com.itheima.domain.Route;
import com.itheima.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    /*判断路线是否被收藏*/
    @Override
    public Favorite isFavorite(String rid, Integer uid) {
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject("SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?", new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            System.out.println("找不到数据Favorite[rid=" + rid + ",uid=" + uid + "]");
        }
        return favorite;
    }

    /*添加收藏数据*/
    @Override
    public void addFavorite(Favorite favorite, JdbcTemplate template) {
        template.update("INSERT INTO tab_favorite VALUES (?,?,?)", favorite.getRoute().getRid(), favorite.getDate(), favorite.getUser().getUid());
    }

    /*修改收藏数量*/
    @Override
    public void updateFavoriteCount(String rid, JdbcTemplate template) {
        template.update("UPDATE tab_route SET COUNT = COUNT + 1 WHERE rid = ?",rid);
    }

    /*总共多少数据*/
    @Override
    public int myFavorite(Integer uid) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tab_favorite WHERE uid = ?", Integer.class, uid);
        return count;
    }

    /*当前页的数据集合*/
    @Override
    public List<Route> myAllFavorite(Integer uid, int index, int pageSize) {
        List<Route> routeList = jdbcTemplate.query("SELECT r.* FROM tab_favorite f , tab_route r WHERE f.`rid`=r.`rid` AND uid = ? LIMIT ?,?", new BeanPropertyRowMapper<>(Route.class), uid, index, pageSize);
        return routeList;
    }
}
