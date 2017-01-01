package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.ScenicSpotDao;
import com.TourGuide.model.ScenicsSpotInfo;

@Service
public class ScenicSpotService {

	@Autowired
	public ScenicSpotDao scenicSpotDao;
	
	/*
	 * 根据用户的位置（省份），获取对应省份的热门景点
	 */
	public List<ScenicsSpotInfo> getScenicByLocation(String province){
		return scenicSpotDao.getScenicByLocation(province);
	}
	
	
	/*
	 * 根据用户的当前所在的省份，获取该省份的所有景点
	 */
	public List<ScenicsSpotInfo> getAllScenicByLocation(String location){
		return scenicSpotDao.getAllScenicByLocation(location);
	}
	
	
	/*
	 * 1、根据景区的名称进行搜索,查看该景区的详细信息
	 * 2、根据搜索的特定的景区的地址，进行相关的景区推荐，暂定推荐数为4个
	 */
	public List<ScenicsSpotInfo> getScenicByNameAndRelates(String name){
		return scenicSpotDao.getScenicByNameAndRelates(name);
	}
	
	
//	/*
//	 * 根据景区的名称，进行搜索，查看景区的详细信息
//	 */
//	public ScenicsSpotInfo getScenicByName(String name){
//		return scenicSpotDao.getScenicByName(name);
//	}
//	
//	/*
//	 * 
//	 */
//	public List<ScenicsSpotInfo> getRelatedScenicByLocation(String location){
//		return scenicSpotDao.getRelatedScenicByLocation(location);
//	}
}
