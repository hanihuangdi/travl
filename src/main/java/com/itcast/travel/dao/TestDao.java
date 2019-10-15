package com.itcast.travel.dao;

import com.itcast.travel.domain.User;
import org.apache.ibatis.annotations.*;

public interface TestDao {
    @Select("select * from tab_user where uid = #{uid} ")
    User findAll(int uid);
    @Delete("delete from tab_user where uid= #{uid} ")
    void del(int uid);


}
