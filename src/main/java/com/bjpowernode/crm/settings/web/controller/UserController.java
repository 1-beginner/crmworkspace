package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.TransactionInvocationHandler;
import com.sun.javafx.collections.MappingChange;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response){
        System.out.println("—————进入—————");
        String serveltPath = request.getServletPath();
        if("/settings/user/login.do".equals(serveltPath)){
            System.out.println(serveltPath);
            login(request,response);
        }else if(" ".equals(serveltPath)){

        }
    }
    private void login(HttpServletRequest request,HttpServletResponse response){
        System.out.println("进入login");
        //接收前端的数据
        String loginAct=request.getParameter("loginAct");
        String loginPwd=request.getParameter("loginPwd");

        System.out.println("转为md5");
        //将loginPwd转为MD5的形式
        loginPwd= MD5Util.getMD5(loginPwd);
        //接收ip地址
        System.out.println("接收ip地址");
        String ip=request.getRemoteAddr();

        //使用代理创建service对象
        System.out.println("创建service对象");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        System.out.println("即将进入try");
        try {
            System.out.println("进入try");
            User user=us.login(loginAct,loginPwd,ip);
            //将当前的用户信息写入回话作用域
            request.getSession().setAttribute("user",user);
            //执行到此说明无异常，登录成功
            /*
                需要对前端返回一个json对象
                {"success":true}
             */
            System.out.println("无异常");
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("有异常");
            String msg=e.getMessage();
            //执行到此controller抛出异常，登录失败
            /*
                需要对前端返回一个json对象
                {"success":false,"msg":?}
             */
            //1，对信息打包成map，将map解析为json对象
            //2，创建ov类，将ov类解析为json对象
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }

    }
}
