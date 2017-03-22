package com.TourGuide.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.common.DateConvert;
import com.TourGuide.model.ScenicTickets;

@Repository
public class OrderDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	ScenicTicketsDao scenicTicketsDao;
	
	@Autowired
	ScenicSpotDao scenicSpotDao;
	
	@Autowired
	GuideDao guideInfoDao;
	
	@Autowired
	IntroFeeAndMaxNumDao introFeeAndMaxNumDao;
	
	
	/**
	 * 根据用户的手机号，查询用户的所有订单
	 * 全部订单包括：拼单（用户自己发起的拼单、与他人进行拼单）
	 * 			  预约单（选择讲解员进行预约、自己发布预约订单）
	 * @param phone  用户手机号
	 * @return 订单编号、参观时间、参观人数、景区名称、景区名称、订单状态、总金额
	 */
	public List<Map<String , Object>> getAllOrders(String phone){
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst=conn.prepareCall("call getAllOrdersbyVisitorPhone(?)");
			cst.setString(1, phone);
			ResultSet rst=cst.executeQuery();
			
			while (rst.next()) {
				Map<String , Object> map = new HashMap<String, Object>();
				map.put("OrderID", rst.getString(1));
				map.put("visitTime", rst.getString(2));
				map.put("visitNum", rst.getInt(3));
				map.put("scenicID", rst.getString(4));
				map.put("scenicName", rst.getString(5));
				map.put("totalMoney", rst.getInt(6));
				map.put("orderState", rst.getString(7));
				listResult.add(map);
			}							
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		
		return listResult;
	}
	
	
	
	
	/**
	 * 根据订单编号，查询订单的详细信息,包括拼单表和预约表
	 * @param orderID  订单编号
	 * @return
	 */
	public List<Map<String, Object>> getDetailOrderInfo(String orderID){
		
		List<Map<String, Object>> listResult = new ArrayList<>();
		
		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst=conn.prepareCall("call getDetailOrderInfoByOrderID(?)");
			cst.setString(1, orderID);
			ResultSet rst=cst.executeQuery();
			
			while (rst.next()) {
				Map<String , Object> map = new HashMap<String, Object>();
				map.put("produceTime", rst.getString(1));
				map.put("payTime", rst.getString(2));
				map.put("takeOrderTime", rst.getString(3));
				map.put("visitTime", rst.getString(4));
				map.put("endTime", rst.getString(5));
				map.put("visitorPhone", rst.getString(6));
				map.put("visitNum", rst.getInt(7));
				map.put("scenicID", rst.getString(8));
				map.put("scenicName", rst.getString(9));
				map.put("money", rst.getInt(10));
				map.put("guideID", rst.getString(11));
				map.put("guidePhone", rst.getString(12));
				map.put("orderState", rst.getString(13));
				map.put("imagePath", rst.getString(14));
				listResult.add(map);
			}							
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		
//		switch (orderState) {
//		case "待接单":
//			sqlString = "select produceTime,visitTime,scenicID,visitNum,"
//					+ "fullPrice,discoutPrice,halfPrice,totalTicket,guideFee,totalGuideFee,totalMoney "
//					+ "from t_bookorder where bookOrderID='"+orderID+"'";
//			listResult = jdbcTemplate.queryForList(sqlString);
//			
//			if(listResult == null){
//				sqlString = "select produceTime,visitTime,scenicID,visitNum,"
//						+ "fullPrice,discoutPrice,halfPrice,totalTicket,guideFee,totalGuideFee,totalMoney "
//						+ "from t_consistorder where consistOrderID='"+orderID+"'";
//				listResult = jdbcTemplate.queryForList(sqlString);
//			}
//			break;
//		case "待付款":
//			
//		case "待游览":
//			sqlString = "select produceTime,visitTime,scenicID,visitNum,guidePhone,"
//					+ "fullPrice,discoutPrice,halfPrice,totalTicket,guideFee,totalGuideFee,totalMoney "
//					+ "from t_bookorder where bookOrderID='"+orderID+"'";
//			listResult = jdbcTemplate.queryForList(sqlString);
//			
//			if(listResult == null){
//				sqlString = "select produceTime,visitTime,scenicID,visitNum,guidePhone,"
//						+ "fullPrice,discoutPrice,halfPrice,totalTicket,guideFee,totalGuideFee,totalMoney "
//						+ "from t_consistorder where consistOrderID='"+orderID+"'";
//				listResult = jdbcTemplate.queryForList(sqlString);
//			}
//			break;
//		case "待评价":
//			sqlString = "select produceTime,visitTime,scenicID,visitNum,guidePhone,endTime,"
//					+ "fullPrice,discoutPrice,halfPrice,totalTicket,guideFee,totalGuideFee,totalMoney "
//					+ "from t_bookorder where bookOrderID='"+orderID+"'";
//			listResult = jdbcTemplate.queryForList(sqlString);
//			
//			if(listResult == null){
//				sqlString = "select produceTime,visitTime,scenicID,visitNum,guidePhone,endTime,"
//						+ "fullPrice,discoutPrice,halfPrice,totalTicket,guideFee,totalGuideFee,totalMoney "
//						+ "from t_consistorder where consistOrderID='"+orderID+"'";
//				listResult = jdbcTemplate.queryForList(sqlString);
//			}
//			break;
//
//		default:
//			break;
//		}
		
		return listResult;
	}
	
	
	/**
	 * 根据订单的编号，查询游客的手机和讲解员的手机号
	 * @param orderID  订单号（拼单、预约订单）
	 * @return
	 */
	public List<Map<String, Object>> getPhoneByOrderID(String orderID){
		
		List<Map<String, Object>> listResult = new ArrayList<>();
		String sqlString = null;
		
		sqlString = "select guidePhone,visitorPhone from t_bookorder where bookOrderID='"+orderID+"'";
		listResult = jdbcTemplate.queryForList(sqlString);
		
		if(listResult == null){
			sqlString = "select guidePhone,visitorPhone from t_consistorder where"
					+ " consistOrderID='"+orderID+"'";
			listResult = jdbcTemplate.queryForList(sqlString);
		}
		
		return listResult;
	}
	
	
//	/**
//	 * 根据用户的手机号，查询特定订单状态的订单
//	 * 订单状态：待接单、待付款、待游览、待评价
//	 * @param phone  手机号
//	 * @param orderState   订单状态
//	 * @return
//	 */
//	public List<Map<String, String>> getOrdersWithState(String phone, String orderState){
//		
//		List<Map<String, String>> listResult = new ArrayList<>();
//		
//		//从拼单表中查找相关的拼单
//		String sqlString = "select consistOrderID,visitTime,visitNum,scenicID,totalMoney,orderState"
//				+ " from t_consistorder where visitorPhone='"+phone+"' and orderState='"+orderState+"'";
//		List<Map<String , Object>> list = jdbcTemplate.queryForList(sqlString);
//		
//		listResult = getSimpleOders(list, "consistOrderID");
//		
//		//从预约表中查找相关的订单
//		String sqlString2 = "select bookOrderID,visitTime,totalMoney,orderState,scenicID,visitNum "
//				+ "from t_bookorder where visitorPhone='"+phone+"' and orderState='"+orderState+"'";
//		List<Map<String , Object>> list2 = jdbcTemplate.queryForList(sqlString2);
//		
//		listResult.addAll(getSimpleOders(list2, "bookOrderID"));
//		
//		return listResult;
//	}
	
	
//	/**
//	 * 按一定格式，将订单的信息返回
//	 * @param list  从数据库中查询到的订单信息
//	 * @param orderID   订单编号类型：bookOrderID、consistOrderID
//	 * @return
//	 */
//	public List<Map<String, String>> getSimpleOders(List<Map<String , Object>> list, String orderID){
//		
//		List<Map<String, String>> listResult = new ArrayList<>();
//		
//		for(int j=0; j<list.size(); j++){
//			Map<String, String> map = new HashMap<String, String>();
//			String scenicID = (String)list.get(j).get("scenicID");
//			Timestamp timestamp = (Timestamp) list.get(j).get("visitTime");
//			String visitTime = DateConvert.timeStamp2DateTime(timestamp);
//			
//			//订单总金额
//			if(list.get(j).get("totalMoney") != null){
//				map.put("totalMoney", list.get(j).get("totalMoney")+"");
//			}else{
//				map.put("totalMoney", "null");
//			}
//						
//			map.put("visitTime", visitTime);   //参观时间
//			map.put("OrderID", (String)list.get(j).get(orderID));     //订单编号
//			map.put("visitNum", (int)list.get(j).get("visitNum")+"");		 //参观人数
//			List<Map<String , Object>> list2 = scenicSpotDao.getSomeScenicInfoByscenicID(scenicID);
//			String scenicName = (String)list2.get(0).get("scenicName");
//			String scenicImagePath = (String)list2.get(0).get("scenicImagePath");
//			map.put("scenicName", scenicName); //景区名称、图片
//			map.put("scenicImagePath", scenicImagePath);
//			map.put("orderState", (String)list.get(j).get("orderState"));   //订单状态
//			listResult.add(map);
//		}
//		
//		return listResult;
//	}
//	
//	
//	


}
