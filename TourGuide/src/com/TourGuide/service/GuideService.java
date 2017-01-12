package com.TourGuide.service;

import java.util.List;
import java.util.Map;

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
	
	public List<Map<String , Object>> GetGuiderinfoBystring(String cID) {
		return guideDao.GetGuiderinfoBystring(cID);
	}
	
	public boolean isAdd(GuideInfo guideInfo) {
		return guideDao.isAdd(guideInfo);
	}

	public void DeleteGuideInfoById_Service(String id) {
		guideDao.DeleteGuideInfoById_Dao(id);
	}
	
	public boolean EditGuideInfo_Service(String level,String historyNum,
			String guideNum,String fee,String phone) {
		return guideDao.EditGuideInfo_Dao(level, historyNum, guideNum, fee, phone);
	}

	public List<GuideOtherInfo> LookGuideInfo_Service(String phone) {
		return guideDao.LookGuideInfo_Dao(phone);
	}
	
	public boolean CheckGuideInfo_Dao(String phone,int historyNum,
			int singleMax,int guideFee,String guideLevel,String scenicBelong,
			int workAge,String certificateID){
		return guideDao.CheckGuideInfo_Dao(phone, historyNum, singleMax, guideFee, guideLevel, 
				scenicBelong, workAge, certificateID);
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
	
	public List<Map<String, Object>> GetGuideofYes(int currentPage,int rows)
	{
		return guideDao.GetGuideofYes(currentPage, rows);
	}
	
	public List<Map<String, Object>> GetGuideofNo(int currentPage,int rows)
	{
		return guideDao.GetGuideofNo(currentPage, rows);
	}
	
	
	public int  GetGuideofYesCount()
	{
		
		return guideDao.GetGuideofYesCount();
	}
	
	public int  GetGuideofNoCount()
	{
		
		return guideDao.GetGuideofNoCount();
	}

	public List<Map<String, Object>> GetGuiderinfoByPhone(String phone) {
		
		return guideDao.GetGuiderinfoByPhone(phone);
	}
}
