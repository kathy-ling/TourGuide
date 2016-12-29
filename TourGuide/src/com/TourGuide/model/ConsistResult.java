package com.TourGuide.model;

/**
 * 拼单的最终结果
 * @author Tian
 * 订单编号与拼单ID是一对多的关系，
 * 多个拼单编号对应同一个订单编号
 */
public class ConsistResult {

	private String orderID; //订单编号
	private String consistOrderID; //拼单ID
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getConsistOrderID() {
		return consistOrderID;
	}
	public void setConsistOrderID(String consistOrderID) {
		this.consistOrderID = consistOrderID;
	}
}
