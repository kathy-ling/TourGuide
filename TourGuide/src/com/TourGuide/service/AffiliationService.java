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
	public boolean applyForAffiliation(String guidePhone, String scenicID, String applyDate){
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
	 * 查看当前景区的申请挂靠时间
	 * @param guidePhone  导游手机号
	 * @param scenicID  景区编号
	 * @return
	 */
	public List<Map<String , Object>> getApplyDateofCurrentScenic(String guidePhone, String scenicID){
		return affiliationDao.getApplyDateofCurrentScenic(guidePhone, scenicID);
	}
	
	
	/**
	 * 查看该导游的挂靠景区记录
	 * @param guidePhone  手机号
	 * @return
	 */
	public List<Map<String , Object>> getApplyHistory(String guidePhone){
		return affiliationDao.getApplyHistory(guidePhone);
	}
}
