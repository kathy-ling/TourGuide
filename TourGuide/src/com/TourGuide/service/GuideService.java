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
	 * @return  0-失败  1-成功  -1-账号已存在
	 */
	public int getGuideAuthentication(String phone, String name,String sex, 
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
	

	
	/**
	 * 查询可被预约的讲解员
	 * 查询条件：讲解员的工作时间、单次最大带团人数、所属景区、是否认证、是否禁用、级别
	 * @param visitDate  游客的参观日期
	 * @param visitNum  参观的人数
	 * @param scenicID  景区编号
	 * @return 可被预约的讲解员的基本信息（按星级排序）
	 * phone,image,name,sex,age,language,selfIntro,guideLevel
	 */
	public List<Map<String, Object>> getAvailableGuides(String visitDate, 
			int visitNum, String scenicID){
		return guideDao.getAvailableGuides(visitDate, visitNum, scenicID);
	}
	
	
	/**
	 * 按用户的筛选条件，查询相应的讲解员信息
	 * @param visitDate  参观日期
	 * @param visitNum  参观人数
	 * @param scenicID  景区编号
	 * @param sex  筛选讲解员的性别   （男，女）
	 * @param age   年龄 （20-30，30-40，40-50）
	 * @param languange   讲解语言 （0-中文、1-英文）
	 * @param level  级别   （1，2，3，4，5，6，7）
	 * @return  符合条件的讲解员的信息
	 * phone,image,name,sex,age,language,selfIntro,guideLevel
	 */
	public List<Map<String, Object>> getAvailableGuidesWithSelector(String visitDate, 
			int visitNum, String scenicID, 
			String sex, String age, String languange, String level){
		return guideDao.getAvailableGuidesWithSelector(visitDate, visitNum, scenicID,
				sex, age, languange, level);
	}

	
	/**
	 * 根据手机号，查询导游的详细信息
	 * @param phone 手机号
	 * @return  导游的详细信息
	 * phone,image,name,sex,age,language,selfIntro,historyNum,guideFee,guideLevel
	 */
	public List<Map<String, Object>> getDetailGuideInfoByPhone(String phone){
		return guideDao.getDetailGuideInfoByPhone(phone);
	}
	
	
	/**
	 * 该导游在visitDate这天，是否被预约了
	 * @param guidePhone  导游的手机号
	 * @param visitDate  日期
	 * @return 1-被预约了    0-未被预约
	 */
	public int WhetherBooked(String guidePhone, String visitDate){
		return guideDao.WhetherBooked(guidePhone, visitDate);
	}
	
	
	
	
//	public List<GuideInfo> getGuidersByPage(int i, int j) {
//		return guideDao.GetGuiderInfoByPage(i, j);
//	}
	

	public  int  GetGuideCount()
	{
		return guideDao.GetGuideCount();
	}
	
	public List<Map<String , Object>> GetGuiderinfoBystring(String phone) {
		return guideDao.GetGuiderinfoBystring(phone);
	}
	
	public boolean isAdd(GuideInfo guideInfo) {
		return guideDao.isAdd(guideInfo);
	}

	public int DeleteGuideInfoById_Service(String phone) {
		return guideDao.DeleteGuideInfoById_Dao(phone);
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
