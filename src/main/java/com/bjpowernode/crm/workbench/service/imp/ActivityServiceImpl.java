package com.bjpowernode.crm.workbench.service.imp;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.vo.PaginationVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao=(ActivityDao) SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao=(ActivityRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public Boolean save(Activity activity) {
        Boolean flag=true;
        int count=activityDao.save(activity);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        System.out.println("进入了service");
        //得到total
        int total=activityDao.getTotalByCondition(map);
        //得到dataList
        List<Activity> dataList=activityDao.getActivityListByCondition(map);
        //将total与dataList打包成vo类
        PaginationVO<Activity> vo=new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        System.out.println("返回了vo");
        return vo;
    }

    @Override
    public Boolean delete(String[] ids) {
        Boolean flag=true;
        //查询出需要删除的备注条数
        int count1=activityRemarkDao.getCountByAids(ids);
        //删除备注，返回受到影响的条数()
        int count2=activityRemarkDao.deleteByAids(ids);

        System.out.println("删除备注的个数count1"+count1+"受到影响的个数count2"+count2);
        if(count1!=count2){
            flag=false;
        }

        //删除活动
        int count3=activityDao.delete(ids);
        System.out.println("删除活动的个数"+count3);
        if(count3!=ids.length){
            flag=false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
       List<User> UList=userDao.getUserList();

        Map<String,Object> map=new HashMap<>();
        map.put("UList",UList);
        Activity activity=activityDao.getActivity(id);
        map.put("a",activity);

        return map;
    }

    @Override
    public Boolean update(Activity activity) {
        Boolean flag=true;
        int count=activityDao.update(activity);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
       Activity a=activityDao.getDetail(id);
       return a;
    }

    @Override
    public List<ActivityRemark> showRemark(String activityId) {
        List<ActivityRemark> AList=activityRemarkDao.getRemark(activityId);
        return AList;
    }

    @Override
    public Boolean deleteRemark(String id) {
        Boolean flag=true;
        int count=activityRemarkDao.deleteRemark(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean saveRemark(ActivityRemark ar) {
        Boolean flag=true;
        int count=activityRemarkDao.saveRemark(ar);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean editRemark(Map<String, String> map) {
        boolean flag=true;
        int count=activityRemarkDao.editRemark(map);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

}
