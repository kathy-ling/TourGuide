package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.GuideFeeDao;

@Service
public class GuideFeeService {

	@Autowired
	private GuideFeeDao guideFeeDao;
	
	public List<Map<String, Object>> GetGuideFee(int currentPage,int rows)
	{
		return guideFeeDao.GetGuideFee(currentPage, rows);
	}
	
	public int  GetGuideFeeCount()
	{
		return guideFeeDao.GetGuideFeeCount();
	}
	
	public List<Map<String, Object>> GetguideFeeByID(String guideID)
	{
		
		return guideFeeDao.GetguideFeeByID(guideID);
	}
}
