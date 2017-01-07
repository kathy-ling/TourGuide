package com.TourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.VisitorloginDao;

@Service
public class VisitorloginService {
	
	@Autowired
	private VisitorloginDao visitorloginDao;
	
	public int Valid_Service(String username,String password)
	{
		return visitorloginDao.Valid(username, password);
		
	}

}
