package com.TourGuide.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.Promotion;

@Repository
public class PromotionDao {
	
	private final int promotionNum = 2; //首页活动的个数

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取首页的活动信息
	 * @param num 获取活动的个数
	 * @return 活动的相关信息
	 */
	public List<Promotion> getPromotions(){
		
		List<Promotion> promotions = new ArrayList<Promotion>();
		
		Date date = new Date();
		String dateNow = dateFormat.format(date);
		
		//按照活动开始时间排序，并过滤已经结束的活动，最后选取若干个
//		String sqlSearch = "select * from t_promotion as p order by p.promotionStartTime "
//				+ "asc limit promotionNum=?";
		String sqlSearch = "select * from (select * from t_promotion as p "
				+ "order by p.promotionStartTime asc) as a "
				+ "where a.promotionEndTime>? limit ?";
		
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlSearch, 
				new Object[]{dateNow, promotionNum});
		
		for (int j = 0; j <list2.size(); j++){
			Promotion promotion = new Promotion();
//			promotion.setPromotionNo((String)list2.get(j).get("promotionNo"));
			promotion.setPromotionImage((String)list2.get(j).get("promotionImage"));
			promotion.setPromotionLinks((String)list2.get(j).get("promotionLinks"));
//			promotion.setPromotionStartTime((String)list2.get(j).get("promotionStartTime"));
//			promotion.setPromotionReleaseTime((String)list2.get(j).get("promotionReleaseTime"));
//			promotion.setPromotionEndTime((String)list2.get(j).get("promotionEndTime"));
			promotions.add(promotion);
		}
		
		return promotions;
	}
}
