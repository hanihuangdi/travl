package com.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.travel.domain.PageBean;
import com.itcast.travel.domain.Route;
import com.itcast.travel.domain.User;
import com.itcast.travel.service.FavoriteService;
import com.itcast.travel.service.RoutServic;
import com.itcast.travel.service.impl.FavoriteServiceImp;
import com.itcast.travel.service.impl.RoutServicImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rount/*")
public class RountServlet extends BaseServlet {
    RoutServic service = new RoutServicImp();
    FavoriteService favoriteService = new FavoriteServiceImp();
    public void listquery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String currentPagestr = request.getParameter("currentPage");
        String pageSizestr = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");
        String rname = request.getParameter("rname");

        //处理参数
        int cid=0;
        if(cidstr!=null&& cidstr.length()>0){
            cid = Integer.parseInt(cidstr);
        }
        int pagesize=0;
        if(pageSizestr!=null && pageSizestr.length()>0){
            pagesize  = Integer.parseInt(pageSizestr);
        }
        else{
            pagesize = 5;
        }
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if(currentPagestr != null && currentPagestr.length() > 0){
            currentPage = Integer.parseInt(currentPagestr);
        }else{
            currentPage = 1;
        }
        PageBean<Route> pageBean=null;
        if(rname!=null&&!"null".equalsIgnoreCase(rname)){
            pageBean = service.pageQuery(cid,currentPage,pagesize,rname);}
        else{
            pageBean = service.pageQuery(cid,currentPage,pagesize,null);
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),pageBean);

    }
    //加载详情页
    public void findRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = service.findRoute(rid);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),route);

    }
    //判断是否收藏
    public void isfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            uid=0;
        }
        else{
            uid=user.getUid();
        }
       Boolean flag =  favoriteService.isfavorite(rid,uid);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),flag);
    }
    //添加收藏
    public void addfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User)request.getSession().getAttribute("user");
        System.out.println(  request.getSession().getAttribute("user"));
        int uid;
        if(user==null){
            return;
        }
        else{
            uid=user.getUid();
        }
        favoriteService.addfavorite(rid,uid);
    }
}
