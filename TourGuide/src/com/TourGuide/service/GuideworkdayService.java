package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.GuideWorkdayDao;
import com.TourGuide.model.Guideworkday;

@Service
public class GuideworkdayService {
	
	@Autowired
	private GuideWorkdayDao guideWorkdayDao;
	
	
	public List<Map<String , Object>> GetGuideworkday(int currentPage,int rows)
	{
		return guideWorkdayDao.GetGuideworkday(currentPage,rows);
	}
	
	public int  getguideWorkdayCount() {
		return guideWorkdayDao.getguideWorkdayCount();
	}
	
	public int UpdateGuideWorkday(Guideworkday guideworkday) {
		
		return guideWorkdayDao.UpdateGuideWorkday(guideworkday);
	}
	
	public List<Map<String, Object>> QueryGuideworkdayByPhone(String phone)
	{
		return guideWorkdayDao.QueryGuideworkdayByPhone(phone);
	}

}
