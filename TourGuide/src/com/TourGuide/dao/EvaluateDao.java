package com.TourGuide.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.ValueStyler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.VisitorInfo;

@Repository
public class EvaluateDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private VisitorDao visitorDao;
	
	/**
	 * 发表评论
	 * @param orderID   订单编号
	 * @param evaluateContext  评价内容
	 * @param evaluateTime  评价时间
	 * @param guidePhone  游客的手机号
	 * @param visitorPhone  讲解员的手机号
	 * @param isAnonymous  是否匿名（1-匿名）
	 * @return
	 */
	public boolean commentByVisitor(String orderID, String evaluateContext,
			String evaluateTime, int isAnonymous, int star1, int star2, int star3){
		
		boolean bool = false;
		String guidePhone = null;
		String visitorPhone = null;
		String nickName = "匿名用户";
		
		List<Map<String, Object>> list = orderDao.getPhoneByOrderID(orderID);
		
		if(list != null){
			guidePhone = (String)list.get(0).get("guidePhone");			
			visitorPhone = (String)list.get(0).get("visitorPhone");
		}
		
		//不是匿名评论，则显示用户的昵称
		if(isAnonymous != 1){
			VisitorInfo visitorInfo = visitorDao.getVisitorInfoWithPhone(visitorPhone);
			nickName = visitorInfo.getNickName();
		}
			
		String sqlString = "insert into t_evaluate (orderID,evaluateContext,evaluateTime,"
				+ "guidePhone,visitorPhone,nickName,isAnonymous,star1,star2,star3) "
				+ "values (?,?,?,?,?,?,?,?,?,?)";
		
		int i = jdbcTemplate.update(sqlString, new Object[]{orderID, evaluateContext, 
				evaluateTime, guidePhone, visitorPhone, nickName, isAnonymous,star1,star2,star3});
		
		if(i != 0){
			bool = true;
		}
		
		return bool;
	}
	
	
	
	/**
	 * 查看某个讲解员相关的历史评论
	 * @param guidePhone  讲解员的手机号
	 * @return 评价时间、评价内容、游客的昵称（若是匿名，则为“匿名用户”）
	 */
	public List<Map<String, Object>> getComments(String guidePhone){
		
		List<Map<String , Object>> list = new ArrayList<>(); 		
 		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst=conn.prepareCall("call getComments(?)");
			cst.setString(1, guidePhone);
			ResultSet rst=cst.executeQuery();
			
			while (rst.next()) {
				Map<String , Object> map = new HashMap<String, Object>();
				map.put("evaluateTime", rst.getString(1));
				map.put("evaluateContext", rst.getString(2));
				map.put("nickName", rst.getString(3));				
				
				list.add(map);
			}			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
 		return list;
		
//		List<Map<String, Object>> list = new ArrayList<>();
//		
//		String sqlString = "select evaluateTime,evaluateContext,nickName "
//				+ "from t_evaluate where guidePhone='"+guidePhone+"'";
//		list = jdbcTemplate.queryForList(sqlString);
//		
//		return list;
	}
}
