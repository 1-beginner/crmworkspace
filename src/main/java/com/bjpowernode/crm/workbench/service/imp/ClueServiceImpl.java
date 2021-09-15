package com.bjpowernode.crm.workbench.service.imp;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao caDao=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    @Override
    public boolean save(Clue clue) {
        boolean flag=true;
        int count=clueDao.save(clue);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public List<Clue> getClueList() {
        List<Clue> clueList=clueDao.getClueList();
        return clueList;
    }

    @Override
    public Clue getClueDetail(String id) {
        Clue clue=clueDao.getClueDetail(id);
        return clue;
    }

    @Override
    public List<Activity> getActivityByClueId(String id) {
        List<Activity> aList=caDao.getActivityByClueId(id);
        return aList;
    }

    @Override
    public boolean unrelation(String id) {
        boolean flag=true;
        int count=caDao.unrelation(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }


}
