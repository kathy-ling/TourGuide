package com.TourGuide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.ConsistOrderDao;
import com.TourGuide.model.ConsistOrder;

@Service
public class ConsistOrderService {
	
	@Autowired
	public ConsistOrderDao consistOrderDao;
	
	/**
	 * 将游客自己发布的拼单订单存入数据库
	 * @param consistOrderID 拼单编号
	 * @param scenicID  景区编号
	 * @param produceTime  订单生产时间
	 * @param visitTime 游客参观的时间
	 * @param visitNum  参观的人数
	 * @param visitorPhone  游客的手机号
	 * @param totalMoney  该拼单的总金额
	 * @param purchaseTicket   是否代购门票
	 * @param orderState  拼单状态
	 * @param isConsisted  是否已经拼单
	 * @param maxNum 最大可拼单人数
	 * @return  发布拼单是否成功，成功：1  失败：0
	 */
	public boolean ReleaseConsistOrder(String consistOrderID, String orderID, String scenicID,
			String produceTime, String visitTime, int visitNum, String visitorPhone, 
			int totalMoney, int purchaseTicket, String orderState, int isConsisted, int maxNum,
			int fullPrice, int discoutPrice, int halfPrice, int totalFee, int totalTicket, int fee){
		
		return consistOrderDao.ReleaseConsistOrder(consistOrderID, orderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted, maxNum, fullPrice,
				discoutPrice, halfPrice, totalFee, totalTicket, fee);
	}
	
	
	
	/**
	 * 查询当前可拼单的订单
	 * @param scenicID  景区编号
	 * @param date   当前的时间
	 */
	public List<ConsistOrder> getAvailableConsistOrder(String scenicID, String date){
		return consistOrderDao.getAvailableConsistOrder(scenicID, date);
	}
	
	
	/**
	 * 从可拼单列表中选择订单，进行拼单
	 * @param orderID   订单编号，一个订单编号对应多个拼单编号
	 * @param consistOrderID  拼单编号
	 * @param scenicID    景区编号
	 * @param produceTime  订单生成时间
	 * @param visitTime  参观时间
	 * @param visitNum   参观人数
	 * @param visitorPhone  游客的手机号
	 * @param totalMoney  此拼单的金额
	 * @param purchaseTicket  是否代购门票
	 * @param orderState  拼单的状态
	 * @param isConsisted   是否被拼单
	 * @param maxNum
	 */
	public boolean consistWithconsistOrderID(String orderID, String consistOrderID, String scenicID,
			String produceTime, String visitTime, int visitNum, String visitorPhone, int totalMoney, 
			int currentNum, int purchaseTicket, String orderState, int isConsisted, int maxNum,
			int fullPrice, int discoutPrice, int halfPrice, int totalFee, int totalTicket, int fee){
		
		return consistOrderDao.consistWithconsistOrderID(orderID, consistOrderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, totalMoney, currentNum,
				purchaseTicket, orderState, isConsisted, maxNum, fullPrice,
				discoutPrice, halfPrice, totalFee, totalTicket, fee);
	}
	
	
	/**
	 * 根据订单编号，查询该订单中的所有拼单的编号
	 * @param orderID  订单编号
	 * @return
	 */
	public List<String> getConsistOrderIDsWithOrderID(String orderID){
		return consistOrderDao.getConsistOrderIDsWithOrderID(orderID);
	}
	
	
	/**
	 * 根据订单编号，查询每个拼单结果的详细信息
	 * @param OrderID  订单编号
	 * @return 订单编号、参观时间、当前人数、最大人数、景区编号
	 */
	public List<Map<String , Object>> getDetailConsistResult(String OrderID){
		return consistOrderDao.getDetailConsistResult(OrderID);
	}


}
