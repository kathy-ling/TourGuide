package com.TourGuide.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AdminDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/*
	 * 管理员登陆检测
	 * 参数：用户名，密码
	 * 2017-1-1 11:32:07
	 * */
	public boolean isValid(String username,String password)
	{
		String sql="select * from t_admin where username=? and password=?";
		int i=jdbcTemplate.queryForList(sql,new Object[]{username,password}).size();
		if (i>0) {
			return true;
		} else {
			return false;
		}
		
	}

}
