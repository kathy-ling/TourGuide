package com.TourGuide.model;

/**
 * 预约订单的信息
 * @author Tian
 *
 */
public class BookOrder {
	
	private String bookOrderID; //预约订单的编号
	private String produceTime; //下单时间
	private String visitTime; //参观时间
	private String endTime; //订单结束时间
	private String visitorPhone; //预约的游客的手机号
	private String guidePhone; //被预约的导游的手机号
	private String orderState; //订单状态
	private String scenicID; //景区编号
	private String priceRange; //价位区间
	private String otherCommand; //其他需求
	private int visitNum; //此次参观的人数
	private int totalMoney; //订单总额（讲解费+门票）
	private int purchaseTicket; //是否代购门票
	private int disabled; //是否禁用
	
	public String getBookOrderID() {
		return bookOrderID;
	}
	public void setBookOrderID(String bookOrderID) {
		this.bookOrderID = bookOrderID;
	}
	public String getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(String produceTime) {
		this.produceTime = produceTime;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getScenicID() {
		return scenicID;
	}
	public void setScenicID(String scenicID) {
		this.scenicID = scenicID;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public String getOtherCommand() {
		return otherCommand;
	}
	public void setOtherCommand(String otherCommand) {
		this.otherCommand = otherCommand;
	}
	public int getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}
	public int getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getPurchaseTicket() {
		return purchaseTicket;
	}
	public void setPurchaseTicket(int purchaseTicket) {
		this.purchaseTicket = purchaseTicket;
	}
	public int getDisabled() {
		return disabled;
	}
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
}
