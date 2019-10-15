package com.itcast.travel.dao.impl;

import com.itcast.travel.dao.FavoriteDao;
import com.itcast.travel.domain.Favorite;
import com.itcast.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImp implements FavoriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
    @Override
    public Favorite findOne(int rid, int uid) {
        Favorite favorite;
        String sql= "select * from tab_favorite where rid = ? and uid =? ";
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }
        catch (Exception e){
            favorite =null;
        }
        return favorite;
    }

    @Override
    public int findCount(int rid) {
        int count;
        String sql = "select * from tab_favorite where rid = ? ";
        try{
      count = template.queryForObject(sql,new BeanPropertyRowMapper<>(Integer.class),rid);}
        catch(Exception e){
            return 0;
        }
        return count;
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?) ";
        template.update(sql,rid,new Date(),uid);
    }
}
