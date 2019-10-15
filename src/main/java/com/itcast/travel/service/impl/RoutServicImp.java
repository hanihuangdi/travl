package com.itcast.travel.service.impl;

import com.itcast.travel.dao.FavoriteDao;
import com.itcast.travel.dao.RoutDao;
import com.itcast.travel.dao.RouteImgDao;
import com.itcast.travel.dao.SellerDao;
import com.itcast.travel.dao.impl.FavoriteDaoImp;
import com.itcast.travel.dao.impl.RoutDaoImp;
import com.itcast.travel.dao.impl.RouteImgImp;
import com.itcast.travel.dao.impl.SellerDaoImp;
import com.itcast.travel.domain.PageBean;
import com.itcast.travel.domain.Route;
import com.itcast.travel.domain.RouteImg;
import com.itcast.travel.domain.Seller;
import com.itcast.travel.service.RoutServic;

import java.util.List;

public class RoutServicImp<RoutImgDao> implements RoutServic {
    RoutDao dao = new RoutDaoImp();
    RouteImgDao imgDao = new RouteImgImp();
    SellerDao sellerDao = new SellerDaoImp();
    FavoriteDao favoriteDao = new FavoriteDaoImp();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pagesize, String rname) {
        PageBean<Route> pg = new PageBean<>();
        pg.setCurrentPage(currentPage);
        pg.setPageSize(pagesize);
        int totalCount = dao.fandTotalCount(cid,rname);
        pg.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pagesize;//开始的记录数
        String str=null;
        List<Route> list = dao.findByPage(cid,start,pagesize,rname);
        pg.setList(list);
        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pagesize == 0 ? totalCount / pagesize :(totalCount / pagesize) + 1 ;

        System.out.println(pg);
        pg.setTotalPage(totalPage);
//        System.out.println(cid);
//        System.out.println(rname);
//        System.out.println(currentPage);
//        System.out.println(pagesize);
//        System.out.println( str);
        return pg;
    }

    @Override
    public Route findRoute(String rid) {
       Route route=  dao.findRoute(Integer.parseInt(rid));
        List<RouteImg> routeImgs = imgDao.findById(route.getRid());
        route.setRouteImgList(routeImgs);
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setSeller(seller);
        int count = favoriteDao.findCount(route.getRid());
        route.setCount(count);
        return route;
    }
}
