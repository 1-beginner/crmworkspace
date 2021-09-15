package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    //获取userDao对象
    private UserDao userDao = (UserDao)SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map=new HashMap<String,String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user=userDao.login(map);
        if(user==null){
            //表示账号密码错误向controller层抛异常
            throw new LoginException("账号密码错误");
        }
        /*
            执行到此表示账号密码无误
         */
        //验证账号时间是否过期
        String expireTime=user.getExpireTime();
        String currentTime=DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime)<0){
            System.out.println("账号过期");
            throw new LoginException("账号已过期");
        }
        //验证锁定状态
        String lockState=user.getLockState();
        if("0".equals(lockState)){
            System.out.println("账号锁定");
            throw new LoginException("账号已锁定");
        }
        //验证ip地址是否有效
        String allowIps=user.getAllowIps();
        if(!allowIps.contains(ip)){
            System.out.println("非公司人员");
           throw new LoginException("非公司人员");
        }

        //成功返回
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> UList=userDao.getUserList();
        return UList;
    }

}
