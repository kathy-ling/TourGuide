package com.TourGuide.model;

import java.sql.Date;

/**
 * 拼单的订单详细信息
 * @author Tian
 *
 */
public class ConsistOrder {
	
	private String consistOrderID; //拼单的编号
	private Date produceTime; //订单产生时间
	private Date visitTime; //参观时间
	private Date endTime; //订单结束时间
	private String visitorPhone; //预约的游客的手机号
	private String guidePhone; //导游的手机号
	private String scenicID; //景点编号
	private String orderState; //订单状态
	private String otherCommand; //其他需求
	private int totalMoney; //订单费用
	private int visitNum; //参观人数 
	private int maxNum; //每单可带的最大人数
	private int purchaseTicket; //是否代购门票
	
	public String getConsistOrderID() {
		return consistOrderID;
	}
	public void setConsistOrderID(String consistOrderID) {
		this.consistOrderID = consistOrderID;
	}
	public Date getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(Date produceTime) {
		this.produceTime = produceTime;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}
	public String getVisitorPhone() {
		return visitorPhone;
	}
	public void setVisitorPhone(String visitorPhone) {
		this.visitorPhone = visitorPhone;
	}
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
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOtherCommand() {
		return otherCommand;
	}
	public void setOtherCommand(String otherCommand) {
		this.otherCommand = otherCommand;
	}
	public int getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getCurrentNum() {
		return visitNum;
	}
	public void setCurrentNum(int visitNum) {
		this.visitNum = visitNum;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public int getPurchaseTicket() {
		return purchaseTicket;
	}
	public void setPurchaseTicket(int purchaseTicket) {
		this.purchaseTicket = purchaseTicket;
	}
}
