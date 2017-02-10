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
	
	
	/**
	 * 分页得到未禁用游客的基本信息
	 * @param i
	 * @param j
	 * @return
	 */
	public List<VisitorInfo> getVisitorInfoByPage(int i,int j) {
		return visitorDao.GetVisitorInfoByPage(i, j);
	}
	
	/**
	 * 得到未禁用游客的数目
	 * @return
	 */
	public  int  GetVisitorCount()
	{
		return visitorDao.GetVisitorCount();
	}
	
	/**
	 * 分页得到未禁用游客的基本信息
	 * @param currentPage
	 * @param rows
	 * @return
	 * 2017-2-9 16:28:54
	 */
	public List<VisitorInfo> GetVisitorInfoDisabled(int currentPage,int rows)
	{
		return visitorDao.GetVisitorInfoDisabled(currentPage, rows);
	}
	
	/**
	 * 通过手机号查询未禁用游客的基本信息
	 * @param phone
	 * @return
	 * 2017-2-9 16:11:52
	 */
	public List<VisitorInfo> SearchVisitorInfoByPhone_Service(String phone) {
		return visitorDao.SearchVisitorInfoByPhone(phone);
	}
	
	
	public List<VisitorInfo> SearchVisitorDisByPhone(String phone) 
	{
		return visitorDao.SearchVisitorDisByPhone(phone);
	}
	
	
	/**
	 * 获取黑名单游客的数目
	 * @return
	 * 2017-2-9 16:25:31
	 */
	public  int  GetVisitorDisabledCount() 
	{
		return visitorDao.GetVisitorDisabledCount();
	}
	
	
	/**
	 * 通过手机号将游客移入黑名单
	 * @param phone
	 * @return
	 * 2017-2-9 16:13:52
	 */
	public boolean ForbidVisitorInfo_Service(String phone) {
		return visitorDao.ForbidVisitorInfo_Dao(phone);
	}
	
	
	/**
	 * 通过手机号将游客移出黑名单
	 * @param phone
	 * @return
	 * 2017-2-9 16:14:34
	 */
	public boolean RelieveVisitorInfo_Service(String phone) {
		return visitorDao.RelieveVisitorInfo_Dao(phone);
	}
	
	
	
	
}

