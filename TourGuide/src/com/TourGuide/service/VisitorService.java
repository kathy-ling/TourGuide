package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.VisitorDao;
import com.TourGuide.model.VisitorInfo;
import com.TourGuide.model.VisitorLoginInfo;

@Service
public class VisitorService {
	
	@Autowired
	private VisitorDao visitorDao;
	
	/**
	 * 用户注册
	 * @param nickName 用户昵称
	 * @param sex  性别
	 * @param name  用户姓名
	 * @param phone  手机号
	 * @param passwd  用户密码
	 * @param image   用户头像
	 * @return
	 */
	public boolean visitorRegister(String nickName, String sex,
			String name, String phone, String passwd, String image){
		return visitorDao.visitorRegister(nickName, sex, name, phone, passwd, image);
	}
	
	
	/**
	 * 根据游客的手机号，查询个人详细信息
	 * @param phone  手机号
	 * @return 手机号、姓名、性别、昵称、头像
	 */
	public VisitorInfo getVisitorInfoWithPhone(String phone){
		return visitorDao.getVisitorInfoWithPhone(phone);
	}
	
	
	
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
	
	public boolean ForbidVisitorInfo_Service(String phone) {
		return visitorDao.ForbidVisitorInfo_Dao(phone);
	}
	
	public boolean RelieveVisitorInfo_Service(String phone) {
		return visitorDao.RelieveVisitorInfo_Dao(phone);
	}
	
	public List<VisitorLoginInfo> getVisitorLoginInfoByPage_Service(int i, int j) {
		return visitorDao.GetVisitorLoginInfoByPage_Dao(i, j);
	}
}

