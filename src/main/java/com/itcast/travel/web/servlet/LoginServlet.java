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

@WebServlet( "/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]>map =  request.getParameterMap();
        User user = new User();
               try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImp();
               ResultInfo info = null;
         //验证码验证
      /*  String code = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        request.removeAttribute("CHECKCODE_SERVER");
        String check = request.getParameter("check");
        System.out.println(check);
        if(check==null&&!code.equalsIgnoreCase(check)){
            info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            response.setContentType("text/html;charset=utf-8");
            ObjectMapper mapper = new ObjectMapper();
            String msg = mapper.writeValueAsString(info);
            response.getWriter().write(msg);

        }
        else {*/
                info =service.login(user);
                request.getSession().setAttribute("name",info.getData());
                response.setContentType("application/json;charset=utf-8");
                ObjectMapper mapper = new ObjectMapper();
                String msg = mapper.writeValueAsString(info);
                response.getWriter().write(msg);

        }

  //  }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
