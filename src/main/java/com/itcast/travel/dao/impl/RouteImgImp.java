package com.itcast.travel.dao.impl;

import com.itcast.travel.dao.RouteImgDao;
import com.itcast.travel.domain.RouteImg;
import com.itcast.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgImp implements RouteImgDao {
    JdbcTemplate templete = new JdbcTemplate(JDBCUtils.getDatasource());

    @Override
    public List<RouteImg> findById(int rid) {
        String sql = "select * from tab_route_img where rid = ? ";
        List<RouteImg> routeImgs = templete.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
        return routeImgs;
    }
}
