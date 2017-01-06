package com.TourGuide.dao;

import java.sql.Timestamp;
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
	
	/**
	 * 根据用户的手机号，查询用户的所有订单
	 * 全部订单包括：拼单（用户自己发起的拼单、与他人进行拼单）
	 * 			  预约单（选择讲解员进行预约、自己发布预约订单）
	 * @param phone  用户手机号
	 * 
	 * 订单编号、订单生成时间、参观时间、结束时间、订单状态、总金额、参观人数、其他需求、
	 * 景区ID（景区名称、图片、门票、讲解费）、
	 * 讲解员Phone（性别、讲解语言、姓名、电话）
	 */
	public void getAllOrdersWithVisitorPhone(String phone){
		
		ScenicSpotDao scenicSpotDao = new ScenicSpotDao();
		ScenicTicketsDao scenicTicketsDao = new ScenicTicketsDao();
		
		//从拼单表中查找相关的拼单
		String sqlString = "select consistOrderID,produceTime,visitTime,endTime,"
				+ "guidePhone,scenicID,totalMoney,orderState,otherCommand,visitNum,"
				+ "purchaseTicket from t_consistorder where visitorPhone='"+phone+"'";
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlString);
		
		for(int i=0; i<list.size(); i++){
			Map<String, String> map = new HashMap<String, String>();					
			Timestamp timestamp1 = (Timestamp) list.get(i).get("produceTime");
			String produceTime = DateConvert.timeStamp2DateTime(timestamp1);			
			Timestamp timestamp2 = (Timestamp) list.get(i).get("visitTime");
			String visitTime = DateConvert.timeStamp2DateTime(timestamp2);
			Timestamp timestamp3 = (Timestamp) list.get(i).get("endTime");
			String endTime = DateConvert.timeStamp2DateTime(timestamp3);
			String guidePhone = (String)list.get(i).get("guidePhone");
			String scenicID = (String)list.get(i).get("scenicID");
			ScenicTickets scenicTickets = scenicTicketsDao.geTicketsByScenicNo(scenicID);
			
			map.put("consistOrderID", (String)list.get(i).get("consistOrderID"));
			map.put("produceTime", produceTime);
			map.put("visitTime", visitTime);
			map.put("endTime", endTime);
			map.put("guidePhone", guidePhone);
			map.put("scenicID", scenicID);
			map.putAll(scenicSpotDao.getScenicInfoByscenicID(scenicID)); //景区名称、图片
			map.put("halfPrice", scenicTickets.getHalfPrice()+"");
			map.put("fullPrice", scenicTickets.getFullPrice()+"");
			map.put("discoutPrice", scenicTickets.getDiscoutPrice()+"");
			map.put("totalMoney", (String)list.get(i).get("totalMoney"));
			map.put("orderState", (String)list.get(i).get("orderState"));
			map.put("otherCommand", (String)list.get(i).get("otherCommand"));
			map.put("purchaseTicket", (String)list.get(i).get("purchaseTicket"));
//			map.put("", );
//			map.put("", );
			
		}
	}

}
