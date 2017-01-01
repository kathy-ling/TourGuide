package com.TourGuide.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.ScenicTickets;

@Repository
public class ScenicTicketsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 根据景区编号，查询该景区的门票信息
	 * @param scenicNo  景区编号
	 * @return  景区的门票信息
	 */
	public ScenicTickets geTicketsByScenicNo(String scenicNo){
		
		ScenicTickets scenicTickets = new ScenicTickets();
		
		String sqlString = "select * from t_scenicTickets where scenicNo='"+scenicNo+"'";
		scenicTickets = jdbcTemplate.queryForObject(sqlString, new BeanPropertyRowMapper(ScenicTickets.class));
		
		return scenicTickets;
	}
}
