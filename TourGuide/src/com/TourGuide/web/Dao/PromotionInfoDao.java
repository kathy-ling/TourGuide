package com.TourGuide.web.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PromotionInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
}