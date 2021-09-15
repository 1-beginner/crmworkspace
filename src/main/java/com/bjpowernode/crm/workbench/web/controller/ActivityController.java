package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.vo.PaginationVO;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.imp.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterGraphics;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入Activity后台");
        String path=request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            System.out.println("进入getUserList");
            getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(path)){
            System.out.println("进入save");
            save(request,response);
        }else if("/workbench/activity/pageList.do".equals(path)){
            System.out.println("pageList.do查询");
            pageList(request,response);
        }else if("/workbench/activity/delete.do".equals(path)){
            System.out.println("进入delete.do");
            delete(request,response);
        }else if("/workbench/activity/getUserListAndActivity.do".equals(path)){
            System.out.println("进入getUserListAndActivity.do");
            getUserListAndActivity(request,response);
        }else if("/workbench/activity/update.do".equals(path)){
            System.out.println("进入update.do");
            update(request,response);
        }else if("/workbench/activity/detail.do".equals(path)){
            System.out.println("进入detail.do");
            detail(request,response);
        }else if("/workbench/activity/showRemark.do".equals(path)){
            System.out.println("进入showRemark.do");
            showRemark(request,response);
        }else if("/workbench/activity/deleteRemark.do".equals(path)){
            System.out.println("进入deleteRemark.do");
            deleteRemark(request,response);
        }else if("/workbench/activity/saveRemark.do".equals(path)){
            System.out.println("进入saveRemark.do");
            saveRemark(request,response);
        }else if("/workbench/activity/editRemark.do".equals(path)){
            System.out.println("进入editRemark.do");
            editRemark(request,response);
        }
    }

    private void editRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        String noteContent=request.getParameter("noteContent");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        map.put("noteContent",noteContent);


        boolean flag=as.editRemark(map);
        PrintJson.printJsonFlag(response,flag);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String remark=request.getParameter("remark");
        String activityId=request.getParameter("activityId");
        //获取当前系统时间
        String createTime=DateTimeUtil.getSysTime();
        //创建id
        String id=UUIDUtil.getUUID();
        //像session域中获得创建人
        User user= (User) request.getSession().getAttribute("user");
        String createBy=user.getName();

        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setActivityId(activityId);
        ar.setNoteContent(remark);
        ar.setEditFlag("0");

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Boolean flag=as.saveRemark(ar);
        PrintJson.printJsonFlag(response,flag);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void showRemark(HttpServletRequest request, HttpServletResponse response) {
        String activityId=request.getParameter("activityId");
        ActivityService as=(ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> AList= (List<ActivityRemark>) as.showRemark(activityId);
        PrintJson.printJsonObj(response,AList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        ActivityService as=(ActivityService)ServiceFactory.getService(new ActivityServiceImpl());
        Activity a=as.detail(id);
        request.setAttribute("a",a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");

        System.out.println(name+owner+startDate);

        String id=request.getParameter("id");
        String editTime= DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();

        ActivityService as=(ActivityService)ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity=new Activity();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        System.out.println(activity);
        System.out.println("id值为："+id);
        Boolean flag=as.update(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,Object> map=new HashMap<>();

        map=as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);


    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入删除功能");
        String ids[]=request.getParameterValues("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Boolean flag=as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入分页查询");
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String pageNoStr=request.getParameter("pageNo");
        String pageSizetStr=request.getParameter("pageSize");

        System.out.println(pageNoStr+pageSizetStr);
        int pageNo=Integer.valueOf(pageNoStr);//页号
        int pageSize=Integer.valueOf(pageSizetStr);//页内条数
        int skimPage=(pageNo-1)*pageSize;//浏览过的条数

        Map<String,Object> map=new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skimPage",skimPage);
        map.put("pageSize",pageSize);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        PaginationVO<Activity> vo=as.pageList(map);

        PrintJson.printJsonObj(response,vo);

    }

    private void getUserList(HttpServletRequest request,HttpServletResponse response){
        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> UList=us.getUserList();
        PrintJson.printJsonObj(response,UList);
    }
    private void save(HttpServletRequest request,HttpServletResponse response){
        String owner=request.getParameter("owner");
        String name=request.getParameter("name");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String cost=request.getParameter("cost");
        String description=request.getParameter("description");

        System.out.println(name+owner+startDate);

        String id=UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();

        ActivityService as=(ActivityService)ServiceFactory.getService(new ActivityServiceImpl());

        Activity activity=new Activity();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        System.out.println(activity);

        Boolean flag=as.save(activity);
        PrintJson.printJsonFlag(response,flag);
    }
}
