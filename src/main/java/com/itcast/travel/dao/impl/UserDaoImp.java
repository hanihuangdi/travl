package com.itcast.travel.dao.impl;

import com.itcast.travel.dao.UserDao;
import com.itcast.travel.domain.User;
import com.itcast.travel.utils.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImp implements UserDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());

    @Override
    public User findByName(String username) {
        User user=null;
        String sql = "select * from tab_user where username=?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        return user;
    }

    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql

        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    @Override
    public User findByCode(String code) {
        User user=null;
        String sql = "select * from tab_user where code=?";
        try{
        user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);}
        catch (Exception e){
            user=null;
        }

        return user;
    }

    @Override
    public void updateStatus(User user) {
    String sql="update tab_user set status='Y' where uid=?";
    template.update(sql,user.getUid());

    }

    @Override
    public User login(User user) {
        User u=null;
        String sql = "select * from tab_user where username=? and password=? ";
        try {
            u = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        }
        catch(Exception e){
            u=null;
        }
        return u;
    }

    @Override
    public String isStatus(String code) {
        String sql = "select * from tab_user where code =?";
        User user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        return user.getStatus();

    }
}
