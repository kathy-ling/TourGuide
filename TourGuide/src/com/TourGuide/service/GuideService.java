package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.GuideDao;
import com.TourGuide.model.GuideInfo;
import com.TourGuide.model.GuideOtherInfo;

@Service
public class GuideService {
	@Autowired
	private GuideDao guideDao;

	public List<GuideInfo> getGuidersByPage(int i, int j) {
		return guideDao.GetGuiderInfoByPage(i, j);
	}
	
	public  int  GetGuideCount()
	{
		return guideDao.GetGuideCount();
	}
	
	public List<GuideInfo> GetGuiderinfoBystring(String cID) {
		return guideDao.GetGuiderinfoBystring(cID);
	}
	
	public boolean isAdd(GuideInfo guideInfo) {
		return guideDao.isAdd(guideInfo);
	}

	public void DeleteGuideInfoById_Service(String id) {
		guideDao.DeleteGuideInfoById_Dao(id);
	}
	
	public boolean EditGuideInfo_Service(GuideInfo guideInfo) {
		return guideDao.EditGuideInfo_Dao(guideInfo);
	}

	public List<GuideOtherInfo> LookGuideInfo_Service(String phone) {
		return guideDao.LookGuideInfo_Dao(phone);
	}
	
	public boolean CheckGuideInfo_Service(String phone) {
		return guideDao.CheckGuideInfo_Dao(phone);
	}
	
	public boolean ForbidGuideInfo_Service(String phone) {
		return guideDao.ForbidGuideInfo_Dao(phone);
	}
	
	public boolean RelieveGuideInfo_Service(String phone) {
		return guideDao.RelieveGuideInfo_Dao(phone);
	}
	
	public List<GuideOtherInfo> getGuideOtherInfoByPage_Service(int i, int j) {
		return guideDao.GetGuideOtherInfoByPage_Dao(i, j);
	}
}
