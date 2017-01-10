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
	
	
	/**
	 * 讲解员提交相应的信息，申请认证
	 * @param phone  手机号
	 * @param name  姓名
	 * @param sex  性别
	 * @param language  讲解语言
	 * @param selfIntro   自我介绍
	 * @param image  头像
	 * @param age    年龄
	 * @return
	 */
	public boolean getGuideAuthentication(String phone, String name,String sex, 
			String language, String selfIntro, String image, int age){
		
		return guideDao.getGuideAuthentication(phone, name, sex,
				language, selfIntro, image, age);
	}
	
	
	/**
	 * 查询最受欢迎的讲解员
	 * 查询条件：级别、历史带团人数、是否认证、是否禁用（先按级别排序，再按带团人数排序）
	 * @return  讲解员的基本信息及级别
	 * phone,image,name,sex,age,language,selfIntro,guideLevel
	 */
	public List<Map<String, Object>> getPopularGuides(){
		return guideDao.getPopularGuides();
	}
	

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
