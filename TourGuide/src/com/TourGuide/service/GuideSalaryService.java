package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.GuideSalaryDao;

@Service
public class GuideSalaryService {
	
	@Autowired
	private GuideSalaryDao guideSalaryDao;

	/**
	 * 根据讲解员的手机号，查询讲解员的收入记录
	 * @param guidePhone  讲解员的手机号
	 * @return
	 */
	public List<Map<String, Object>> getSalaryRecord(String guidePhone){
		return guideSalaryDao.getSalaryRecord(guidePhone);
	}
	
	
	/**
	 * 统计讲解员接单的总次数和总金额
	 * @param guidePhone
	 * @return
	 */
	public Map<String , Object> getSalaryAmount(String guidePhone){
		return guideSalaryDao.getSalaryAmount(guidePhone);
	}
}
