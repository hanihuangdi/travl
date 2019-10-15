package com.itcast;

import com.itcast.travel.dao.TestDao;
import com.itcast.travel.dao.UserDao;
import com.itcast.travel.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import java.io.IOException;
import java.io.InputStream;

public class TestDemo {
    InputStream in;
    SqlSession sqlsession;
    @Before
    public void init(){
        try {
            in = Resources.getResourceAsStream("Mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(in);
        sqlsession = sqlSessionFactory.openSession();
    }
    @After
    public void destory(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlsession.commit();
        sqlsession.close();
    }
    @Test
    public void test1() throws IOException {
        TestDao dao = sqlsession.getMapper(TestDao.class);
        User user = dao.findAll(5);
        System.out.println(user);

    }

    @Test
    public void test2(){
        UserDao dao = sqlsession.getMapper(UserDao.class);
        User user = dao.findByName("zhangsan");
        System.out.println(user);
    }
}

