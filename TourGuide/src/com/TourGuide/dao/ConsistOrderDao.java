package com.TourGuide.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.naming.java.javaURLContextFactory;
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
			int totalMoney, int purchaseTicket, String orderState, int isConsisted, int maxNum){
		
		boolean bool = false;
		
		String sqlString2 = "insert into t_consistresult (orderID,visitNum) values (?,?)";
		int j = jdbcTemplate.update(sqlString2, new Object[]{orderID, visitNum});
		
		String sqlString = "insert into t_consistOrder (consistOrderID,orderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,totalMoney,purchaseTicket,orderState,isConsisted,maxNum) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{consistOrderID, orderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted,maxNum});
		
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
			int currentNum, int purchaseTicket, String orderState, int isConsisted, int maxNum){
		
		boolean bool = false;
		
		//将拼单信息插入拼单表
		String sqlString = "insert into t_consistOrder (orderID,consistOrderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,totalMoney,purchaseTicket,orderState,isConsisted,maxNum) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{orderID, consistOrderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, 
				totalMoney, purchaseTicket, orderState, isConsisted,maxNum});
		
		//更新拼单状态，已拼单
		String sqlString1 = "update t_consistorder set isConsisted=1 where orderID=?";
		int j = jdbcTemplate.update(sqlString1, new Object[]{orderID});
		
		String sqlString2 = "update t_consistresult set currentNum=?,maxNum=? where orderID=?";
		int k = jdbcTemplate.update(sqlString2, new Object[]{currentNum, maxNum, orderID});

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
		List<String> consistOrderIDs = new ArrayList<>();
		/**
		 * 还有一种情况未写
		 */
//		String sqlSearch = "select orderID from t_consistresult where ? < ?";
//		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlSearch);
//		for (int i = 0; i <list.size(); i++){
//			String orderID = (String)list.get(i).get("orderID");
//			
//		}
		
		//根据拼单状态和参观时间在拼单表中筛选当前景区的可拼订单
		String sqlString = "select orderID,visitTime,visitNum,maxNum from t_consistorder "
				+ "where scenicID='"+scenicID+"' and isConsisted='"+isConsisted+"' and visitTime>'"+date+"'";		
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlString);
		
		for (int j = 0; j <list2.size(); j++){
			ConsistOrder consistOrder = new ConsistOrder();
			consistOrder.setOrderID((String)list2.get(j).get("orderID"));
			Timestamp timestamp = (Timestamp) list2.get(j).get("visitTime");
			String dateTime = DateConvert.timeStamp2DateTime(timestamp);
			consistOrder.setVisitTime(dateTime);
			consistOrder.setCurrentNum((int)list2.get(j).get("visitNum"));
			consistOrder.setMaxNum((int)list2.get(j).get("maxNum"));
			listResult.add(consistOrder);
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
	 * 根据拼单编号，查询每个拼单的详细信息
	 * @param consistOrderID  拼单编号
	 * @return
	 */
	public ConsistOrder getDetailConsistOrder(String consistOrderID){
		
		final ConsistOrder consistOrder = new ConsistOrder();
		
		String sqlSearch = "select * from t_consistorder where consistOrderID='"+consistOrderID+"'";
		
		jdbcTemplate.query(sqlSearch,  new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet res) throws SQLException {
				consistOrder.setConsistOrderID(res.getString(1));
				consistOrder.setOrderID(res.getString(2));
				consistOrder.setProduceTime(res.getString(3));
				consistOrder.setVisitTime(res.getString(4));
				consistOrder.setEndTime(res.getString(5));
				consistOrder.setVisitorPhone(res.getString(6));
				consistOrder.setGuidePhone(res.getString(7));
				consistOrder.setScenicID(res.getString(8));
				consistOrder.setTotalMoney(res.getInt(9));
				consistOrder.setOrderState(res.getString(10));
				consistOrder.setOtherCommand(res.getString(11));
				consistOrder.setVisitNum(res.getInt(12));
				consistOrder.setMaxNum(res.getInt(13));
				consistOrder.setPurchaseTicket(res.getInt(14));
				consistOrder.setIsConsisted(res.getInt(15));
			}
		});
		
		return consistOrder;
	}
}