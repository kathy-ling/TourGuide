package com.TourGuide.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 按一定格式，将订单的信息返回
	 * @param list  从数据库中查询到的订单信息
	 * @param orderID   订单编号类型：bookOrderID、consistOrderID
	 * @return
	 */
	public List<Map<String, String>> getSimpleOders(List<Map<String , Object>> list, String orderID){
		
		List<Map<String, String>> listResult = new ArrayList<>();
		
		for(int j=0; j<list.size(); j++){
			Map<String, String> map = new HashMap<String, String>();
			String scenicID = (String)list.get(j).get("scenicID");
			Timestamp timestamp = (Timestamp) list.get(j).get("visitTime");
			String visitTime = DateConvert.timeStamp2DateTime(timestamp);
			
			//订单总金额
			if(list.get(j).get("totalMoney") != null){
				map.put("totalMoney", list.get(j).get("totalMoney")+"");
			}else{
				map.put("totalMoney", "null");
			}
						
			map.put("visitTime", visitTime);   //参观时间
			map.put("OrderID", (String)list.get(j).get(orderID));     //订单编号
			map.put("visitNum", (int)list.get(j).get("visitNum")+"");		 //参观人数	
			map.putAll(scenicSpotDao.getSomeScenicInfoByscenicID(scenicID)); //景区名称、图片
			map.put("orderState", (String)list.get(j).get("orderState"));   //订单状态
			listResult.add(map);
		}
		
		return listResult;
	}
	
	
	
	/**
	 * 根据用户的手机号，查询用户的所有订单
	 * 全部订单包括：拼单（用户自己发起的拼单、与他人进行拼单）
	 * 			  预约单（选择讲解员进行预约、自己发布预约订单）
	 * @param phone  用户手机号
	 * @return 订单编号、参观时间、参观人数、景区名称、图片（暂时不用）、订单状态、总金额
	 */
	public List<Map<String, String>> getAllOrders(String phone){
		
		List<Map<String, String>> listResult = new ArrayList<>();
		
		//从拼单表中查找相关的拼单
		String sqlString = "select consistOrderID,visitTime,visitNum,scenicID,totalMoney,orderState"
				+ " from t_consistorder where visitorPhone='"+phone+"'";
		List<Map<String , Object>> list = jdbcTemplate.queryForList(sqlString);
		
		listResult = getSimpleOders(list, "consistOrderID");
		
		//从预约表中查找相关的订单
		String sqlString2 = "select bookOrderID,visitTime,totalMoney,orderState,scenicID,visitNum "
				+ "from t_bookorder where visitorPhone='"+phone+"'";
		List<Map<String , Object>> list2 = jdbcTemplate.queryForList(sqlString2);
		
		listResult.addAll(getSimpleOders(list2, "bookOrderID"));
		
		return listResult;
	}
	
	
	
	
	/**
	 * 根据用户的手机号，查询特定订单状态的订单
	 * 订单状态：待接单、待付款、待游览、待评价
	 * @param phone  手机号
	 * @param orderState   订单状态
	 * @return
	 */
	public List<Map<String, String>> getOrdersWithState(String phone, String orderState){
		
		List<Map<String, String>> listResult = new ArrayList<>();
		
		//从拼单表中查找相关的拼单
		String sqlString = "select consistOrderID,visitTime,visitNum,scenicID,totalMoney,orderState"
				+ " from t_consistorder where visitorPhone='"+phone+"' and orderState='"+orderState+"'";
		List<Map<String , Object>> list = jdbcTemplate.queryForList(sqlString);
		
		listResult = getSimpleOders(list, "consistOrderID");
		
		//从预约表中查找相关的订单
		String sqlString2 = "select bookOrderID,visitTime,totalMoney,orderState,scenicID,visitNum "
				+ "from t_bookorder where visitorPhone='"+phone+"' and orderState='"+orderState+"'";
		List<Map<String , Object>> list2 = jdbcTemplate.queryForList(sqlString2);
		
		listResult.addAll(getSimpleOders(list2, "bookOrderID"));
		
		return listResult;
	}
	
	
	
	/**
	 * 根据订单编号和订单状态，查询订单的详细信息
	 * @param orderID  订单号
	 * @param orderState  订单状态
	 * @return
	 * 订单编号、订单生成时间、参观时间、结束时间、订单状态、总金额、参观人数、其他需求、
	 * 景区ID（景区名称、图片）、
	 * 讲解员Phone（性别、讲解语言、姓名）
	 */
//	public List<Map<String, String>> getAllOrdersDetailWithOrderID(String orderID, String orderState){
//		
//		List<Map<String, String>> listResult = new ArrayList<>();
//		
//		//从拼单表中查找相关的拼单
//		String sqlString = "select consistOrderID,visitTime,visitNum,scenicID,totalMoney，orderState "
//				+ "from t_consistorder where consistOrderID='"+orderID+"' and orderState='"+orderState+"'";
//		
//		switch (orderState) {
//		case "待接单":
//			
//			break;
//		case "待付款":
//					
//			break;
//		case "待游览":
//			
//			break;
//		case "待评价":
//			
//			break;
//
//		default:
//			break;
//		}
//		
//		//从拼单表中查找相关的拼单
//		String sqlString = "select consistOrderID,produceTime,visitTime,endTime,"
//				+ "guidePhone,scenicID,totalMoney,orderState,otherCommand,visitNum"
//				+ " from t_consistorder where consistOrderID='"+orderID+"'";
//		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlString);
//		
//		for(int i=0; i<list.size(); i++){
//			Map<String, String> map = new HashMap<String, String>();					
//			Timestamp timestamp1 = (Timestamp) list.get(i).get("produceTime");
//			String produceTime = DateConvert.timeStamp2DateTime(timestamp1);			
//			Timestamp timestamp2 = (Timestamp) list.get(i).get("visitTime");
//			String visitTime = DateConvert.timeStamp2DateTime(timestamp2);
//			Timestamp timestamp3 = (Timestamp) list.get(i).get("endTime");
//			String endTime = null;
//			if(timestamp3 != null){
//				endTime = DateConvert.timeStamp2DateTime(timestamp3);
//			} 
//			String guidePhone = (String)list.get(i).get("guidePhone");
//			String scenicID = (String)list.get(i).get("scenicID");
//			
//			map.put("consistOrderID", (String)list.get(i).get("consistOrderID"));
//			map.put("produceTime", produceTime);
//			map.put("visitTime", visitTime);
//			map.put("endTime", endTime);
//			map.put("guidePhone", guidePhone);
//			if(guidePhone != null){
//				map.putAll(guideInfoDao.getSomeGuideInfoByPhone(guidePhone)); //性别、讲解语言、姓名
//			}			
//			map.putAll(scenicSpotDao.getSomeScenicInfoByscenicID(scenicID)); //景区名称、图片			
//			map.put("totalMoney", (int)list.get(i).get("totalMoney")+"");
//			map.put("visitNum", (int)list.get(i).get("visitNum")+"");
//			map.put("orderState", (String)list.get(i).get("orderState"));
//			map.put("otherCommand", (String)list.get(i).get("otherCommand"));
//			
//			listResult.add(map);
//		}
//		return listResult;
//	}
	
	
	
	


}
