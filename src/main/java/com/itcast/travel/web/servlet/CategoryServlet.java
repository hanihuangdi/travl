package com.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.travel.domain.Category;
import com.itcast.travel.service.CategoryService;
import com.itcast.travel.service.impl.CategoryServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet( "/categoryServlet/*")
public class CategoryServlet extends BaseServlet {
    CategoryService service = new CategoryServiceImp();
    //查询所有
    public void findList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list =service.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


}
