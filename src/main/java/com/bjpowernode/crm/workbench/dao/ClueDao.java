package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueDao {


    int save(Clue clue);

    List<Clue> getClueList();

    Clue getClueDetail(String id);

}
