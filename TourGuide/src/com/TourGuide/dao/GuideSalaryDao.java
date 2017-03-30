package com.TourGuide.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuideSalaryDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	


}
