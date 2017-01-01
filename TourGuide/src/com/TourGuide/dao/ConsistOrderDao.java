package com.TourGuide.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.ConsistOrder;

@Repository
public class ConsistOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final int isConsisted = 0;  //isConsisted,订单未被拼单
	
	
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
		
		boolean bool = false;
		
		String sqlString = "insert into t_consistOrder (consistOrderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,totalMoney,purchaseTicket,orderState,isConsisted,maxNum) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{consistOrderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted,maxNum});
		
		if(i == 1){
			bool = true;
		}
		return bool;
	}
	
	
	/**
	 * 查询当前可拼单的订单
	 * @param scenicID  景区编号
	 * @param maxNum  拼单的最大可拼人数
	 * @param date   当前的时间
	 */
	public List<ConsistOrder> getAvailableConsistOrder(String scenicID, String date){
		
		List<ConsistOrder> listResult = new ArrayList<>();
		
		String sqlString = "select consistOrderID,visitTime,visitNum,maxNum from t_consistorder "
				+ "where scenicID='"+scenicID+"' and isConsisted='"+isConsisted+"' and visitTime>'"+date+"'";
		
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlString);
		
		for (int j = 0; j <list2.size(); j++){
			ConsistOrder consistOrder = new ConsistOrder();
			consistOrder.setConsistOrderID((String)list2.get(j).get("consistOrderID"));
			Timestamp timestamp = (Timestamp) list2.get(j).get("visitTime");
			consistOrder.setVisitTime(new java.sql.Date(timestamp.getTime()));
			consistOrder.setCurrentNum((int)list2.get(j).get("visitNum"));
			consistOrder.setMaxNum((int)list2.get(j).get("maxNum"));
			listResult.add(consistOrder);
		}
		return listResult;
	}
}
