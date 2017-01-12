package com.TourGuide.model;

/**
 * 
 * @author wxt
 *
 */
public class GuideFee {
	
	private String guideID; //讲解员ID
	private String spring;	//春季
	private String summer;  //夏季
	private String autumn;	//秋季
	private String winter;	//冬季
	public String getGuideID() {
		return guideID;
	}
	public void setGuideID(String guideID) {
		this.guideID = guideID;
	}
	public String getSpring() {
		return spring;
	}
	public void setSpring(String spring) {
		this.spring = spring;
	}
	public String getSummer() {
		return summer;
	}
	public void setSummer(String summer) {
		this.summer = summer;
	}
	public String getAutumn() {
		return autumn;
	}
	public void setAutumn(String autumn) {
		this.autumn = autumn;
	}
	public String getWinter() {
		return winter;
	}
	public void setWinter(String winter) {
		this.winter = winter;
	}

}
