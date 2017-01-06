package com.TourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.AdminDao;


@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	/*
	 * 管理员登录验证
	 * */
	public boolean isValid(String username,String password)
	{
		return adminDao.isValid(username, password);
	}
}
