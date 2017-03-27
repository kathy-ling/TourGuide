package com.TourGuide.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;












import javax.sql.DataSource;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.common.DateConvert;
import com.TourGuide.common.MyDateFormat;
import com.TourGuide.model.ConsistOrder;
import com.TourGuide.model.ConsistResult;

@Repository
public class ConsistOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final int isConsisted = 0;  //isConsisted,订单未被拼单
	
	
	/**
	 * 将游客自己发布的拼单订单存入数据库
	 * @param consistOrderID  拼单编号
	 * @param orderID  
	 * @param scenicID  景区编号
	 * @param produceTime  订单生产时间
	 * @param visitTime 游客参观的时间
	 * @param visitNum  参观的人数
	 * @param visitorPhone  游客的手机号
	 * @param orderState  订单状态
	 * @param isConsisted  是否已经拼单
	 * @param maxNum  最大可拼单人数
	 * @param totalFee  讲解费总额
	 * @param fee 每个人的讲解费
	 * @return
	 */
	public boolean ReleaseConsistOrder(String consistOrderID, String orderID, String scenicID,
			String produceTime, String visitTime, int visitNum, String visitorPhone, 
			String contact, String orderState, int isConsisted, int maxNum, int totalFee, int fee){
		
		boolean bool = false;
		
		String sqlString2 = "insert into t_consistresult (orderID,visitNum,maxNum,"
				+ "visitTime,scenicID) values (?,?,?,?,?)";
		int j = jdbcTemplate.update(sqlString2, new Object[]{orderID, visitNum, 
				maxNum, visitTime, scenicID});
		
		String sqlString = "insert into t_consistOrder (consistOrderID,orderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,contact,orderState,"
				+ "isConsisted,maxNum,totalGuideFee,guideFee)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{consistOrderID, orderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone,contact,
				orderState, isConsisted,maxNum, totalFee, fee});
		
		/**
		 * 假设付款成功
		 */
		String payTime = MyDateFormat.form(new Date());
		String sqlUpdate = "update t_consistOrder set hadPay=1,payTime='"+payTime+"' "
				+ "where consistOrderID='"+consistOrderID+"'";
		int k = jdbcTemplate.update(sqlUpdate);
		
		if(i!=0 && j!=0 && k!=0){
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
			String produceTime, String visitTime, int visitNum, String visitorPhone, String contact,
			int currentNum, String orderState, int isConsisted, int maxNum, int totalFee, int fee){
		
		boolean bool = false;
		String payTime = MyDateFormat.form(new Date());;
		
		//将拼单信息插入拼单表
		String sqlString = "insert into t_consistOrder (orderID,consistOrderID,scenicID,produceTime,"
				+ "visitTime,visitNum,visitorPhone,contact,orderState,isConsisted,maxNum,"
				+ "totalGuideFee,guideFee) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{orderID, consistOrderID, scenicID, 
				produceTime, visitTime, visitNum, visitorPhone, contact,
				orderState, isConsisted,maxNum,totalFee, fee});
		
		//更新拼单状态，已拼单
		String sqlString1 = "update t_consistorder set isConsisted=1 where orderID=?";
		int j = jdbcTemplate.update(sqlString1, new Object[]{orderID});
		
		//更新拼单结果，当前人数
		String sqlString2 = "update t_consistresult set "
				+ "visitNum=?,maxNum=?,visitTime=?,scenicID=? where orderID=?";
		int k = jdbcTemplate.update(sqlString2, new Object[]{currentNum, maxNum,
				visitTime, scenicID, orderID});
		
		String sqlUpdate = "update t_consistorder set payTime='"+payTime+"',hadPay=1 "
				+ "where orderID='"+orderID+"' ";
		int h = jdbcTemplate.update(sqlUpdate);
		
		if(i != 0 && j!=0 && k!=0 && h!=0){
			bool = true;
		}
		return bool;
	}

	
	
	
	/**
	 * 查询数据库中的当前可拼单的订单
	 * @param scenicID  景区编号
	 * @param date   当前的时间
	 */
	public List<Map<String , Object>> getAllAvailableConsistOrder(){
		
		List<Map<String , Object>> list = new ArrayList<>(); 			 		
 		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst=conn.prepareCall("call getAvailableConsistOrder()");
			ResultSet rst=cst.executeQuery();
			
			while (rst.next()) {
				Map<String , Object> map = new HashMap<String, Object>();
				
				int currentNum = rst.getInt(3);
				int maxNum = rst.getInt(4);
				//还可以拼单的人数
				int num = maxNum - currentNum;
				map.put("orderID", rst.getString(1));
				map.put("visitTime", rst.getString(2));
				map.put("currentNum", currentNum);
				map.put("num", num);
				map.put("scenicID", rst.getString(5));
				map.put("scenicName", rst.getString(6));
				map.put("guideFee", rst.getInt(7));
				list.add(map);
			}							
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
				
		return list;
	}
	
	
	/**
	 * 根据条件，筛选可拼单的订单
	 * @param scenicName   景区名称
	 * @param date  日期
	 * @param visitNum  参观人数
	 * @return
	 */
	public List<Map<String , Object>> getConsistOrderWithSelector(String scenicName, 
			String date, int visitNum){
		
		int def = -1;
		List<Map<String , Object>> listResult = new ArrayList<>(); 		
		List<Map<String , Object>> list = getAllAvailableConsistOrder();
		
		for(int i=0; i<list.size(); i++){
			listResult.add(list.get(i));
		}
		
		for(int i=0; i<list.size(); i++){
			
			String lname = (String) list.get(i).get("scenicName");
			String ldate = (String) list.get(i).get("visitTime");
			int num = (Integer) list.get(i).get("num");

			
			if(!scenicName.equals("null") && !scenicName.equals(lname)){
				listResult.remove(list.get(i));
			}
			
			if((visitNum != def) && (visitNum > num)){
				listResult.remove(list.get(i));
			}
			
			//true: dateFrom <= dateTo
			if(!date.equals("null") && DateConvert.dateCompare(ldate, date)){
				listResult.remove(list.get(i));
			}
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
	 * 根据订单编号，查询每个拼单结果的详细信息,t_consistresult
	 * @param OrderID  订单编号
	 * @return 订单编号、参观时间、当前人数、最大人数、景区编号
	 */
	public ConsistResult getDetailConsistResult(String OrderID){
		
		ConsistResult consistResult = new ConsistResult(); 			 		
 		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst=conn.prepareCall("call getDetailConsistResult(?)");
			cst.setString(1, OrderID);
			ResultSet rst=cst.executeQuery();
		
			while (rst.next()) {
				consistResult.setOrderID(rst.getString(1));
				consistResult.setMaxNum(rst.getInt(2));
				consistResult.setVisitTime(rst.getString(3));
				consistResult.setScenicID(rst.getString(4));
				consistResult.setVisitNum(rst.getInt(5));
			}							
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		
		return consistResult;
	}
}
