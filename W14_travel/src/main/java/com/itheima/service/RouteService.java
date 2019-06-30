package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.RankOV;
import com.itheima.domain.Route;

import java.util.List;
import java.util.Map;

public interface RouteService {
    Map<String,List<Route>> usefulRoute();

    PageBean<Route> search(String cid, String rname, int pageNumber, int pageSize);

    Route routeDetail(String rid);

    PageBean<Route> rankRoutes(RankOV ov, int pageNumber, int pageSize);
}
