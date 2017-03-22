package com.TourGuide.web.Dao;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.web.model.PromotionInfo;


@Repository
public class PromotionInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 分页得到所有的景区活动信息
	 * @return 所有景区的活动信息
	 * 2017-3-22 21:37:17
	 */
	public List<PromotionInfo> getPromotionInfoBypage(int i,int j) {
		
		DataSource dataSource=jdbcTemplate.getDataSource();
		
	}
	
	
	
	
}
