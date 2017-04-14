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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuideSalaryDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 根据讲解员的手机号，查询讲解员的收入记录
	 * @param guidePhone  讲解员的手机号
	 * @return
	 */
	public List<Map<String, Object>> getSalaryRecord(String guidePhone){
		
		List<Map<String, Object>> list = new ArrayList<>();		
		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst = conn.prepareCall("call getSalaryRecord(?)");
			cst.setString(1, guidePhone);
			ResultSet rst = cst.executeQuery();
			
			while (rst.next()) {
				Map<String , Object> map = new HashMap<String, Object>();
				map.put("orderId", rst.getString(1));
				map.put("guidePhone", rst.getString(2));
				map.put("visitNum", rst.getInt(3));
				map.put("totalMoney", rst.getInt(4));
				map.put("time", rst.getString(5));
				map.put("scenicName", rst.getString(6));				
				list.add(map);
			}							
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		
		return list;
	}
	
	
	/**
	 * 统计讲解员接单的总次数和总金额
	 * @param guidePhone
	 * @return
	 */
	public Map<String , Object> getSalaryAmount(String guidePhone){
		
		Map<String , Object> map = new HashMap<String, Object>();
		DataSource dataSource =jdbcTemplate.getDataSource();
		 
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement cst = conn.prepareCall("call getSalaryAmount(?)");
			cst.setString(1, guidePhone);
			ResultSet rst = cst.executeQuery();
			
			while (rst.next()) {								
				map.put("totalMoney", rst.getInt(1));
				map.put("totalOrders", rst.getInt(2));				
			}							
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		return map;
	}

}
