package com.TourGuide.web.model;


/**
 * 
 * @author tian
 *
 *	活动信息表：景区编号，景区名称，活动标题，活动图片，活动链接
 *	活动开始时间，活动结束时间，活动发布时间，判断是否在首页显示，
 *	是否通过管理员进行美工发布，活动内容
 */
public class PromotionInfo {
	
	private String scenicNo;
	private String scenicName;
	private String proTitle;
	private String proImage;
	private String proLink;
	private String proStartTime;
	private String ProEndTime;
	private String ProProduceTime;
	private int isMainShow;
	private int isAdmin;
	private String ProContext;
	public String getScenicNo() {
		return scenicNo;
	}
	public void setScenicNo(String scenicNo) {
		this.scenicNo = scenicNo;
	}
	public String getScenicName() {
		return scenicName;
	}
	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}
	public String getProTitle() {
		return proTitle;
	}
	public void setProTitle(String proTitle) {
		this.proTitle = proTitle;
	}
	public String getProImage() {
		return proImage;
	}
	public void setProImage(String proImage) {
		this.proImage = proImage;
	}
	public String getProLink() {
		return proLink;
	}
	public void setProLink(String proLink) {
		this.proLink = proLink;
	}
	public String getProStartTime() {
		return proStartTime;
	}
	public void setProStartTime(String proStartTime) {
		this.proStartTime = proStartTime;
	}
	public String getProEndTime() {
		return ProEndTime;
	}
	public void setProEndTime(String proEndTime) {
		ProEndTime = proEndTime;
	}
	public String getProProduceTime() {
		return ProProduceTime;
	}
	public void setProProduceTime(String proProduceTime) {
		ProProduceTime = proProduceTime;
	}
	public int getIsMainShow() {
		return isMainShow;
	}
	public void setIsMainShow(int isMainShow) {
		this.isMainShow = isMainShow;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getProContext() {
		return ProContext;
	}
	public void setProContext(String proContext) {
		ProContext = proContext;
	}
	
	

}
