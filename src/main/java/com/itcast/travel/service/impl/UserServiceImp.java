package com.itcast.travel.service.impl;

import com.itcast.travel.dao.UserDao;
import com.itcast.travel.dao.impl.UserDaoImp;
import com.itcast.travel.domain.ResultInfo;
import com.itcast.travel.domain.User;
import com.itcast.travel.service.UserService;
import com.itcast.travel.utils.MailUtils;
import com.itcast.travel.utils.UuidUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

public class UserServiceImp implements UserService {
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
    UserDao dao= new UserDaoImp();
    //注册
    public Boolean regist(User user) {
        //查询用户名是否存在
        User user1 = dao.findByName(user.getUsername());
        Boolean flag;
        //不存在
        if(user1==null){
            flag=true;

            //设置状态码，code,发送邮箱
           user.setStatus("N");
           user.setCode(UuidUtil.getUuid());
            dao.save(user);
          String content = "<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活黑马旅游网</a>";
            MailUtils.sendMail(user.getEmail(),content,"邮件激活");
        }
       else{
          flag=false;
        }




        return  flag;
    }

    @Override
    public Boolean active(String code) {
      User user =  dao.findByCode(code);
      if(user !=null){
          dao.updateStatus(user);
          return true;
      }
      else{
          return  false;
      }
    }

    @Override
    public ResultInfo login(User user) {
        ResultInfo info =new ResultInfo();
        User u = dao.login(user);
        if(u==null){
            info.setErrorMsg("账号或密码错误");
            info.setFlag(false);
        }
        else{

            if(u.getStatus().equals("Y")){
                info.setFlag(true);
                info.setData(u);
                System.out.println("已经激活");
            }
            else{
                System.out.println("未激活");
                info.setErrorMsg("账号未激活请激活后登录");
                info.setFlag(false);
            }
        }
        return info;
    }
}
