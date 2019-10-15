package com.itcast.travel.dao;

import com.itcast.travel.domain.Route;

import java.util.List;

public interface RoutDao {
    int fandTotalCount(int cid, String rname);

    public List<Route> findByPage(int cid, int start, int pagesize, String rname);

    Route findRoute(int parseInt);
}
