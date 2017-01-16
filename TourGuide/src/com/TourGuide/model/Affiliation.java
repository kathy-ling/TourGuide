package com.TourGuide.model;

/**
 * 挂靠景区信息（包含历史记录）
 * @author Tian
 *
 */
public class Affiliation {

	private String guidePhone; //导游的手机号
	private String scenicID;   //景区编号
	private String applyDate;   //申请挂靠的日期
	private String quitDate;  //取消挂靠的日期
	
	public String getGuidePhone() {
		return guidePhone;
	}
	public void setGuidePhone(String guidePhone) {
		this.guidePhone = guidePhone;
	}
	public String getScenicID() {
		return scenicID;
	}
	public void setScenicID(String scenicID) {
		this.scenicID = scenicID;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getQuitDate() {
		return quitDate;
	}
	public void setQuitDate(String quitDate) {
		this.quitDate = quitDate;
	}
}
