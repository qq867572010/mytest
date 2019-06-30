package com.itheima.dao;

import com.itheima.domain.*;

import java.util.List;

public interface RouteDao {
    List<Route> hotRoutes();

    List<Route> newRoutes();

    List<Route> themeRoutes();

    int getTotalCount(String cid, String rname);

    List<Route> search(String cid, String rname, int index, int pageSize);

    Route findById(String rid);

    Seller findSellerById(Integer sid);

    Category findCategoryById(Integer cid);

    List<RouteImg> findImg(String rid);

    int getTotalCount2(RankOV ov);

    List<Route> rankRoutes(RankOV ov, int index, int pageSize);
}
