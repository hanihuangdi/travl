package com.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.travel.domain.ResultInfo;
import com.itcast.travel.domain.User;
import com.itcast.travel.service.UserService;
import com.itcast.travel.service.impl.UserServiceImp;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//实现抽取
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //注册功能
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        if(!code.equalsIgnoreCase(req.getParameter("check"))){
            flag = false;
            info.setFlag(flag);
            info.setErrorMsg("验证码错误");
        }
        else {
            flag =  userService.regist(user);
            if (flag) {
                //注册成功
                info.setFlag(flag);
            } else {
                //注册失败
                info.setFlag(flag);
                info.setErrorMsg("注册失败，请联系管理员");
            }
        }
        //使用JACKSON，序列化数据
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(info);
        //设置json数据类型
        resp.setContentType("application/json;charset=utf-8");

        resp.getWriter().write(result);
    }
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImp();
        ResultInfo info = null;
        //验证码验证
        String code = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        if (check == null || !code.equalsIgnoreCase(check)) {
            info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }
        else {

            info = service.login(user);
            request.getSession().setAttribute("user", info.getData());
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String msg = mapper.writeValueAsString(info);
        response.getWriter().write(msg);
    }
    //用户激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        String msg=null;
        UserService service = new UserServiceImp();
        //判断是否存在
        if(code==null){
            //不存在
            msg="激活失败，请联系管理员";
        }
        else{
            //存在
            Boolean flag =  service.active(code);
            if(flag){
                msg="激活成功请<a href="+request.getContextPath()+"/login.html>登录</a>";
            }
            else{
                msg="激活失败请联系管理员";
            }

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
    //用户退出
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
    //用户名登录显示
    public void findname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user= (User)request.getSession().getAttribute("user");
       // String name = user.getName();
       // if(name!=null){
            System.out.println(user);
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper mapper=new ObjectMapper();
            mapper.writeValue(response.getOutputStream(),user);

       // }
    }

}
