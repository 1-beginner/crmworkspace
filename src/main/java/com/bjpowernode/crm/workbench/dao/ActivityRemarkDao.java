package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemark(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark ar);

    int editRemark(Map<String, String> map);
}
