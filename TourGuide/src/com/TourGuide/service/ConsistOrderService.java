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
	public boolean ReleaseConsistOrder(String consistOrderID, String scenicID,
			String produceTime, String visitTime, int visitNum, String visitorPhone, 
			int totalMoney, int purchaseTicket, String orderState, int isConsisted, int maxNum){
		
		return consistOrderDao.ReleaseConsistOrder(consistOrderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted, maxNum);
	}
	
	
	
	/**
	 * 查询当前可拼单的订单
	 * @param scenicID  景区编号
	 * @param maxNum  拼单的最大可拼人数
	 * @param date   当前的时间
	 */
	public List<ConsistOrder> getAvailableConsistOrder(String scenicID, String date){
		return consistOrderDao.getAvailableConsistOrder(scenicID, date);
	}

}
