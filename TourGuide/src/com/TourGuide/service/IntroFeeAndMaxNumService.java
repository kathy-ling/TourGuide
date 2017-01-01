package com.TourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.IntroFeeAndMaxNumDao;

@Service
public class IntroFeeAndMaxNumService {

	@Autowired
	public IntroFeeAndMaxNumDao introFeeDao;
	
	/**
	 * 查询某天该景区的拼单讲解费
	 * @param date  日期
	 * @param scenicNo  景区编号
	 * @return
	 */
	public int getIntroFee(String date, String scenicNo){
		return introFeeDao.getIntroFee(date, scenicNo);
	}
	
	
	/**
	 * 查询某天该景区的拼单的最大可拼人数
	 * @param date  日期
	 * @param scenicNo   景区编号
	 * @return  
	 */
	public int getMaxNum(String date, String scenicNo){
		return introFeeDao.getMaxNum(date, scenicNo);
	}
}
