package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.MenuDao;

@Service
public class MenuService {
	
	@Autowired
	private MenuDao menuDao;
	
	public Map<String, Object> getMenuByrole(String account)
	{
		return menuDao.getMenuByrole(account);
	}
	
	public List<Map<String, Object>> getAuthority()
	{
		return menuDao.getAuthority();
	}

}
