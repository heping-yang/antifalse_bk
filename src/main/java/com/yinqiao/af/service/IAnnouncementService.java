package com.yinqiao.af.service;

import java.util.List;

import com.yinqiao.af.model.Announcement;

public interface IAnnouncementService {

    int deleteByPrimaryKey(String msgid);

    int insert(Announcement record);

    Announcement selectByPrimaryKey(String msgid);

    List<Announcement> selectAll();

    int updateByPrimaryKey(Announcement record);
}
