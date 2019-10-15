package com.itcast.travel.dao;

import com.itcast.travel.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.servlet.annotation.WebServlet;

public interface UserDao {
    @Select("select * from tab_user where username=#{username}")
    public User findByName(String username);

    @Insert("insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) " +
            "values(#{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    public void save(User user);

    @Select("select * from tab_user where code=#{code}")
    User findByCode(String code);

    @Update("upadate tab_user set status='Y' where uid=#{uid}")
    void updateStatus(User user);

    @Select("select * from tab_user where username=#{username} and password=#{password}")
    User login(User user);

    @Select("select * from tab_user where code = #{code}")
    String isStatus(String code);
}
