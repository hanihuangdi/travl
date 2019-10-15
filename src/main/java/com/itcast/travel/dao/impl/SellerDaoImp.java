package com.itcast.travel.dao.impl;

import com.itcast.travel.dao.SellerDao;
import com.itcast.travel.domain.Seller;
import com.itcast.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImp implements SellerDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
    @Override
    public Seller findSeller(int sid) {

        String sql = "select * from tab_seller where sid = ?";
        return  template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
