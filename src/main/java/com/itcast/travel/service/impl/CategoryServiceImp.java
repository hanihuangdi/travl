package com.itcast.travel.service.impl;

import com.itcast.travel.dao.CategoryDao;
import com.itcast.travel.dao.impl.CategoryDaoImp;
import com.itcast.travel.domain.Category;
import com.itcast.travel.service.CategoryService;
import com.itcast.travel.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImp implements CategoryService {
    CategoryDao dao = new CategoryDaoImp();
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        if (categorys == null || categorys.size() == 0) {
           // System.out.println("从数据库查询....");
            //3.如果为空,需要从数据库查询,在将数据存入redis
            //3.1 从数据库查询
            cs = dao.findAll();
            for (int i = 0; i < cs.size(); i++) {

                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }

        } else {
           // System.out.println("从redis中查询.....");


            //4.如果不为空,将set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);

            }


        }


        return cs;
    }
}
