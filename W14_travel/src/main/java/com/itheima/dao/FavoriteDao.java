package com.itheima.dao;

import com.itheima.domain.Favorite;
import com.itheima.domain.Route;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface FavoriteDao {
    Favorite isFavorite(String rid, Integer uid);

    void addFavorite(Favorite favorite, JdbcTemplate jdbcTemplate);

    void updateFavoriteCount(String rid, JdbcTemplate jdbcTemplate);

    int myFavorite(Integer uid);

    List<Route> myAllFavorite(Integer uid, int index, int pageSize);
}
