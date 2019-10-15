package com.itcast.travel.dao;

import com.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> findById(int rid);
}
