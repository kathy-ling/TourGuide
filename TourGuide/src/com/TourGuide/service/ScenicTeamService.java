package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.ScenicSpotDao;
import com.TourGuide.dao.ScenicTeamDao;
import com.TourGuide.model.ScenicTeam;

@Service
public class ScenicTeamService {

	@Autowired
	private ScenicTeamDao scenicTeamDao;
	
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	public List<ScenicTeam> getScenicTeam(int i, int j)
	{
		return scenicTeamDao.getScenicTeam(i, j);
	}
	
	public int  getScenicTeamCount() {
		
		return scenicSpotDao.GetScenicCount();
	}
	
	public List<ScenicTeam> getScenicTeamByscenicNo(String scenicNo)
	{
		return scenicTeamDao.getScenicTeamByscenicNo(scenicNo);
	}
	
	public int UpdateScenicTeam(String scenicNo,int fee,int maxNum,String date)
	{
		return scenicTeamDao.UpdateScenicTeam(scenicNo, fee, maxNum,date);
	}
}
