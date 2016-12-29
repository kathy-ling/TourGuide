package com.TourGuide.model;

/**
 * 景点信息
 */
public class ScenicsSpotInfo {

	private String scenicNo; //景区编号，主键
	private String scenicImagePath; //景区图片
	private String scenicName; //景点名称
	private String scenicIntro; //景点介绍
	private int totalVisits; //景区历史参观人数
	private String openingHours; //景区开放时间
	private String scenicLocation; //景区位置
	private String scenicLevel; //景区级别
	private String chargePerson; //负责人
	
	public String getScenicNo() {
		return scenicNo;
	}
	public void setScenicNo(String scenicNo) {
		this.scenicNo = scenicNo;
	}
	public String getScenicImagePath() {
		return scenicImagePath;
	}
	public void setScenicImagePath(String scenicImagePath) {
		this.scenicImagePath = scenicImagePath;
	}
	public String getScenicName() {
		return scenicName;
	}
	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}
	public String getScenicIntro() {
		return scenicIntro;
	}
	public void setScenicIntro(String scenicIntro) {
		this.scenicIntro = scenicIntro;
	}
	public int getTotalVisits() {
		return totalVisits;
	}
	public void setTotalVisits(int totalVisits) {
		this.totalVisits = totalVisits;
	}
	public String getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	public String getScenicLocation() {
		return scenicLocation;
	}
	public void setScenicLocation(String scenicLocation) {
		this.scenicLocation = scenicLocation;
	}
	public String getScenicLevel() {
		return scenicLevel;
	}
	public void setScenicLevel(String scenicLevel) {
		this.scenicLevel = scenicLevel;
	}
	public String getChargePerson() {
		return chargePerson;
	}
	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}
	
}
