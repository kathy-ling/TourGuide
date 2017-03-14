package com.TourGuide.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.common.MyDateFormat;
import com.TourGuide.model.Promotion;

@Repository
public class PromotionDao {
	

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询首页可以显示的活动个数，从t_system中获取
	 * @return
	 * @throws SQLException
	 */
	public int getpromotionShowNum() throws SQLException{
		
		int num = 0;
		
		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst=conn.prepareCall("call getMainPagePromotionNum(?)");
			cst.registerOutParameter(1, java.sql.Types.BIGINT);
			cst.execute();
			num = (int)cst.getInt(1);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}
	
	
	/**
	 * 获取首页的活动信息
	 * @return 活动的相关信息,活动图片、活动链接
	 */
	public List<Promotion> getPromotions(){
		 
		List<Promotion> promotions = new ArrayList<Promotion>();
		
		int promotionNum = 0;
		try {
			promotionNum = getpromotionShowNum();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		String dateNow = MyDateFormat.form(new Date());
		
		//按照活动开始时间排序，并过滤已经结束的活动，最后根据是否在首页显示，显示若干个（显示个数由系统设定）活动信息
		String sqlSearch = "select * from "
				+ "(select * from t_promotion as a where a.promotionEndTime>? and mainPageShow=1) as p "
				+ "order by p.promotionStartTime asc limit ?";
		
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlSearch, 
				new Object[]{dateNow, promotionNum});
		
		for (int j = 0; j <list2.size(); j++){
			Promotion promotion = new Promotion();
			promotion.setPromotionImage((String)list2.get(j).get("promotionImage"));
			promotion.setPromotionLinks((String)list2.get(j).get("promotionLinks"));
			promotions.add(promotion);
		}
		
		return promotions;
	}
}
