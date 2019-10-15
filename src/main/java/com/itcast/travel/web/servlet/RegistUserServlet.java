package com.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.travel.domain.ResultInfo;
import com.itcast.travel.domain.User;
import com.itcast.travel.service.UserService;
import com.itcast.travel.service.impl.UserServiceImp;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数集合
       Map<String,String[]> map =  req.getParameterMap();
       User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //创建service执行方法
        UserService userService = new UserServiceImp();
        Boolean flag;
        //创建结果对象接受信息
        ResultInfo info = new ResultInfo();

        String code = (String)req.getSession().getAttribute("CHECKCODE_SERVER");
        req.removeAttribute("CHECKCODE_SERVER");
        System.out.println(req.getParameter("check"));
        if(!code.equalsIgnoreCase(req.getParameter("check"))){
            flag = false;
            info.setFlag(flag);
            info.setErrorMsg("errro");
        }
        else {
            flag =  userService.regist(user);
            if (flag) {
                //注册成功
                info.setFlag(flag);
            } else {
                //注册失败
                info.setFlag(flag);
                info.setErrorMsg("zhuceshibai");
            }
        }
       //使用JACKSON，序列化数据
        ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(info);
            //设置json数据类型
        resp.setContentType("application/json;charset=utf-8");

            resp.getWriter().write(result);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }
}

