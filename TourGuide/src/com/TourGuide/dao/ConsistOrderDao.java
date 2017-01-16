package com.TourGuide.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.TourGuide.common.DateConvert;
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
	public boolean ReleaseConsistOrder(String consistOrderID, String orderID, String scenicID,
			String produceTime, String visitTime, int visitNum, String visitorPhone, 
			int totalMoney, int purchaseTicket, String orderState, int isConsisted, int maxNum,
			int fullPrice, int discoutPrice, int halfPrice, int totalFee, int totalTicket, int fee){
		
		boolean bool = false;
		
		String sqlString2 = "insert into t_consistresult (orderID,visitNum,maxNum,"
				+ "visitTime,scenicID) values (?,?,?,?,?)";
		int j = jdbcTemplate.update(sqlString2, new Object[]{orderID, visitNum, 
				maxNum, visitTime, scenicID});
		
		String sqlString = "insert into t_consistOrder (consistOrderID,orderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,totalMoney,purchaseTicket,orderState,isConsisted,maxNum,"
				+ "fullPrice,discoutPrice,halfPrice,totalGuideFee,totalTicket,guideFee) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{consistOrderID, orderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted,maxNum, fullPrice,
				discoutPrice, halfPrice, totalFee, totalTicket, fee});
		
		if(i == 1 && j == 1){
			bool = true;
		}
		return bool;
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
		
		boolean bool = false;
		
		//将拼单信息插入拼单表
		String sqlString = "insert into t_consistOrder (orderID,consistOrderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,totalMoney,purchaseTicket,orderState,isConsisted,maxNum,"
				+ "fullPrice,discoutPrice,halfPrice,totalGuideFee,totalTicket,guideFee) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{orderID, consistOrderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted,maxNum,fullPrice,
				discoutPrice, halfPrice, totalFee, totalTicket, fee});
		
		//更新拼单状态，已拼单
		String sqlString1 = "update t_consistorder set isConsisted=1 where orderID=?";
		int j = jdbcTemplate.update(sqlString1, new Object[]{orderID});
		
		//更新拼单结果，当前人数
		String sqlString2 = "update t_consistresult set "
				+ "visitNum=?,maxNum=?,visitTime=?,scenicID=? where orderID=?";
		int k = jdbcTemplate.update(sqlString2, new Object[]{currentNum, maxNum,
				visitTime, scenicID, orderID});

		if(i != 0 && j != 0 && k!=0){
			bool = true;
		}
		return bool;
	}

	
	
	
	/**
	 * 查询当前可拼单的订单
	 * @param scenicID  景区编号
	 * @param date   当前的时间
	 */
	public List<ConsistOrder> getAvailableConsistOrder(String scenicID, String date){
		
		List<ConsistOrder> listResult = new ArrayList<>();
		
		//在拼单结果中根据人数和参观时间进行筛选
		String sqlSearch = "select orderID,visitTime,visitNum,maxNum from t_consistresult"
				+ " where visitNum < maxNum and scenicID='"+scenicID+"' and visitTime>'"+date+"'";
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlSearch);
		
		
		for (int i = 0; i <list.size(); i++){
			ConsistOrder order = new ConsistOrder();
			Timestamp timestamp = (Timestamp) list.get(i).get("visitTime");
			String dateTime = DateConvert.timeStamp2DateTime(timestamp);
			order.setVisitTime(dateTime);
			order.setOrderID((String)list.get(i).get("orderID"));
			order.setVisitNum((int)list.get(i).get("visitNum"));
			order.setMaxNum((int)list.get(i).get("maxNum"));			
			listResult.add(order);
		}
				
		return listResult;
	}



	/**
	 * 根据订单编号，查询该订单中的所有拼单的编号
	 * @param orderID  订单编号
	 * @return
	 */
	public List<String> getConsistOrderIDsWithOrderID(String orderID){
		
		List<String> consistOrderIDs = new ArrayList<>();
		
		String sqlSearch = "select consistOrderID from t_consistorder where orderID='"+orderID+"'";
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlSearch);
		
		for (int j = 0; j <list2.size(); j++){
			String id = (String)list2.get(j).get("consistOrderID");
			consistOrderIDs.add(id);
		}
		
		return consistOrderIDs;
	}
	
	
	
	
	/**
	 * 根据订单编号，查询每个拼单结果的详细信息
	 * @param OrderID  订单编号
	 * @return 订单编号、参观时间、当前人数、最大人数、景区编号
	 */
	public List<Map<String , Object>> getDetailConsistResult(String OrderID){
		
		String sqlSearch = "select * from t_consistresult where orderID='"+OrderID+"'";
		
		List<Map<String , Object>> list = jdbcTemplate.queryForList(sqlSearch);
		
		return list;
	}
}
