package com.itcast.travel.service;

import com.itcast.travel.domain.PageBean;
import com.itcast.travel.domain.Route;

public interface RoutServic {
    PageBean<Route> pageQuery(int cid, int currentPage, int pagesize, String rname);

    Route findRoute(String cid);
}
