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

