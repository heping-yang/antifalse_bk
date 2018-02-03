package com.yinqiao.af.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yinqiao.af.mapper.AnnouncementMapper;
import com.yinqiao.af.model.Announcement;
import com.yinqiao.af.service.IAnnouncementService;

@Service("announcementService")
public class AnnouncementServiceImpl implements IAnnouncementService {

	@Autowired
	private AnnouncementMapper announcementMapper;
	
	public int deleteByPrimaryKey(String msgid) {
		return announcementMapper.deleteByPrimaryKey(msgid);
	}

	public int insert(Announcement record) {
		return announcementMapper.insert(record);
	}

	public Announcement selectByPrimaryKey(String msgid) {
		return announcementMapper.selectByPrimaryKey(msgid);
	}

	public List<Announcement> selectAll() {
		return announcementMapper.selectAll();
	}

	public int updateByPrimaryKey(Announcement record) {
		return announcementMapper.updateByPrimaryKey(record);
	}

}
