package com.TourGuide.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public class GuideInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 根据导游的手机号，查询导游的信息：姓名、性别、讲解语言
	 * @param phone 手机号
	 * @return 姓名、性别、讲解语言
	 */
	public Map<String, String> getSomeGuideInfoByPhone(String phone){
		
		final Map<String, String> map = new HashMap<String, String>();
		String sqlString = "select name,sex,language from t_guideinfo "
				+ "where phone='"+phone+"'";
		
		jdbcTemplate.query(sqlString,  new RowCallbackHandler() {
					
					@Override
					public void processRow(ResultSet res) throws SQLException {
						map.put("name", res.getString(1));
						map.put("sex", res.getString(2));
						map.put("language", res.getString(3));
					}
		});
		return map;
	}
}
