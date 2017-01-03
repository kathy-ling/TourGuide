package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.VisitorDao;
import com.TourGuide.model.VisitorInfo;

@Service
public class VisitorService {
	
	@Autowired
	private VisitorDao visitorDao;
	
	public List<VisitorInfo> getVisitorInfoByPage(int i,int j) {
		return visitorDao.GetVisitorInfoByPage(i, j);
	}
	
	public  int  GetVisitorCount()
	{
		return visitorDao.GetVisitorCount();
	}
	
	public List<VisitorInfo> SearchVisitorInfoByPhone_Service(String sql) {
		return visitorDao.SearchVisitorInfoByPhone_Dao(sql);
	}
	
	public boolean AddVisitorInfo_Service(VisitorInfo visitorInfo) {
		return visitorDao.AddVisitorInfo_Dao(visitorInfo);
	}
	
	public boolean DeleteVisitorInfo_Service(String s) {
		return visitorDao.DeleteVisitorInfo(s);
	}
	
	public boolean UpdateVisitorInfo_Service(VisitorInfo visitorInfo) {
		return visitorDao.UpdateVisitorInfo(visitorInfo);
	}
}
