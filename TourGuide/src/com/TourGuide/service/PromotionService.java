package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.PromotionDao;
import com.TourGuide.model.Promotion;

@Service
public class PromotionService {
	
	@Autowired
	public PromotionDao promotionDao;
	
	/*
	 * 获取首页的活动信息
	 */
	public List<Map<String, Object>> getPromotions(){
		return promotionDao.getPromotions();
	}

}
