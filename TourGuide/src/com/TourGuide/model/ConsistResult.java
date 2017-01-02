package com.TourGuide.model;

/**
 * 拼单的最终结果
 * @author Tian
 * 订单编号与拼单ID是一对多的关系，
 * 多个拼单编号对应同一个订单编号
 */
public class ConsistResult {

	private String orderID; //订单编号
	private int currentNum; //当前的总人数
	private int maxNum;  //最大可拼单人数
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getCurrentNum() {
		return currentNum;
	}
	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
}
