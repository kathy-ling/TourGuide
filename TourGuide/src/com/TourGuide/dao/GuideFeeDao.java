package com.TourGuide.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuideFeeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 对讲解员的收入信息获取并进行分页
	 * @param currentPage
	 * @param rows
	 * @return
	 * 2017-1-13 21:14:29
	 */
	public List<Map<String, Object>> GetGuideFee(int currentPage,int rows)
	{
		int k=(currentPage-1)*rows;
		String sql="SELECT  t_guideinfo.name,t_guidefee.* "
				+ "from t_guideinfo,t_guidefee  where t_guideinfo.phone = t_guidefee.guideID"
				+ " LIMIT "+k+" ,"+rows+"";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);	
		return list;
	}
	
	/**
	 * 得到讲解员的信息数目
	 * @return
	 * 2017-1-13 21:14:59
	 */
	public int  GetGuideFeeCount()
	{
		String sql="SELECT  t_guideinfo.name,t_guidefee.* "
				+ "from t_guideinfo,t_guidefee  where t_guideinfo.phone = t_guidefee.guideID";
		int i=jdbcTemplate.queryForList(sql).size();
		return i;
	
	}
	
	public List<Map<String, Object>> GetguideFeeByID(String guideID)
	{
		String sql="SELECT  t_guideinfo.name,t_guidefee.* "
				+ "from t_guideinfo,t_guidefee  where t_guideinfo.phone = t_guidefee.guideID"
				+ " and t_guideinfo.phone='"+guideID+"'";
		return jdbcTemplate.queryForList(sql);
	}

}
