package com.TourGuide.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 将游客自己发布的预约订单存入数据库
	 * @param bookOrderID  订单编号，使用JDK的UUID产生
	 * @param scenicID  游客所预约的景区对应的景区编号
	 * @param produceTime  该订单的生成时间
	 * @param visitTime  游客参观的时间
	 * @param visitNum   参观的人数
	 * @param language  讲解员的讲解语言
	 * @param guideSex   讲解员的性别
	 * @param visitorPhone   游客的手机号
	 * @param visitorName   游客的姓名
	 * @param priceRange   游客可接受的价位区间
	 * @param purchaseTicket  是否代购门票
	 * @param otherCommand    其他需求
	 * @param releaseByVisitor  标志该订单是由游客自己发布的
	 * @param orderState 订单的状态
	 * @return 发布订单是否成功，成功：1  失败：0
	 */
	public boolean ReleaseBookOrder(String bookOrderID, String scenicID, String produceTime,String visitTime, 
			int visitNum, String language, String guideSex, String visitorPhone,String visitorName, 
			String priceRange, int purchaseTicket, String otherCommand, int releaseByVisitor, String orderState){
		
		boolean bool = false;
		
		String sqlString = "insert into t_bookorder (bookOrderID,scenicID,produceTime,"
				+ "visitTime,visitNum,language,guideSex,visitorPhone,visitorName,"
				+ "priceRange,purchaseTicket,otherCommand,releaseByVisitor,orderState) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{bookOrderID, scenicID, 
				produceTime, visitTime,visitNum, language, guideSex, visitorPhone, 
				visitorName, priceRange, purchaseTicket, otherCommand, releaseByVisitor, orderState});
		
		if(i == 1){
			bool = true;
		}
		return bool;
	}
}
