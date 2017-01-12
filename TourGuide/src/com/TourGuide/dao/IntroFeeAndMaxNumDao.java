package com.TourGuide.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.catalina.webresources.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.IntroFeeAndMaxNum;

@Repository
public class IntroFeeAndMaxNumDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询某天该景区的拼单讲解费
	 * @param date  日期
	 * @param scenicNo  景区编号
	 * @return
	 */
	public int getIntroFee(String date, String scenicNo){
		
		final IntroFeeAndMaxNum introFeeAndMaxNum = new IntroFeeAndMaxNum();
		int fee = 0;
		
		String sql = "select fee from t_introfeeandmaxnum where scenicNo='"+scenicNo+"' and date='"+date+"'";
		
		jdbcTemplate.query(sql,  new RowCallbackHandler() {
					
					@Override
					public void processRow(ResultSet res) throws SQLException {
						introFeeAndMaxNum.setFee(res.getInt(1));
					}
		});
		
		fee = introFeeAndMaxNum.getFee();
		return fee;
	}
	
	
	/**
	 * 查询某天该景区的拼单的最大可拼人数
	 * @param date  日期
	 * @param scenicNo   景区编号
	 * @return  
	 */
	public int getMaxNum(String date, String scenicNo){
		
		int maxNum = 0;
		
		String sql = "select maxNum from t_introfeeandmaxnum where scenicNo='"+scenicNo+"' and date='"+date+"'";
		maxNum = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return maxNum;
	}
}
