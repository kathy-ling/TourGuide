package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.AffiliationDao;

@Service
public class AffiliationService {

	@Autowired
	private AffiliationDao affiliationDao;
	
	/**
	 * 导游选中景区后，申请挂靠
	 * @param guidePhone  导游手机号
	 * @param scenicID  景区编号
	 * @param applyDate  申请日期
	 * @return
	 */
	public int applyForAffiliation(String guidePhone, String scenicID, String applyDate){
		return affiliationDao.applyForAffiliation(guidePhone, scenicID, applyDate);
	}
	
	
	/**
	 * 取消导游所挂靠的景区
	 * @param guidePhone  导游手机号
	 * @param scenicID   景区编号
	 * @param quitDate  取消挂靠的日期
	 * @return
	 */
	public boolean cancleAffiliation(String guidePhone, String scenicID, String quitDate){
		return affiliationDao.cancleAffiliation(guidePhone, scenicID, quitDate);
	}
	
	
	
	/**
	 * 查看该导游的挂靠景区记录
	 * @param guidePhone  手机号
	 * @return
	 */
	public List<Map<String , Object>> getHistoryAffiliation(String guidePhone){
		return affiliationDao.getHistoryAffiliation(guidePhone);
	}
	
	
	/**
	 * 查看该导游的当前挂靠景区
	 * @param guidePhone  手机号
	 * @return
	 */
	public Map<String , Object> getCurrentAffiliation(String guidePhone){
		return affiliationDao.getCurrentAffiliation(guidePhone);
	}
	
	
	/**
	 * 查看该导游当前de挂靠申请
	 * @param guidePhone
	 * @return
	 */
	public Map<String , Object> getCurrentApply(String guidePhone){
		return affiliationDao.getCurrentApply(guidePhone);
	}
}
