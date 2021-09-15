package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueService {
    boolean save(Clue clue);

    List<Clue> getClueList();

    Clue getClueDetail(String id);


    List<Activity> getActivityByClueId(String id);

    boolean unrelation(String id);
}
