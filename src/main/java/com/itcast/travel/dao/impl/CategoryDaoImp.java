package com.itcast.travel.dao.impl;

import com.itcast.travel.dao.CategoryDao;
import com.itcast.travel.domain.Category;
import com.itcast.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImp implements CategoryDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> list = template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
