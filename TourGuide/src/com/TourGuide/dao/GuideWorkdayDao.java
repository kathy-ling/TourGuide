package com.TourGuide.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.Guideworkday;

@Repository
public class GuideWorkdayDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @param currentPage(当前页)
	 * @param rows(每页的显示数目)
	 * @return 讲解员工作安排信息
	 * 2017-1-8 18:57:58
	 */
	public List<Map<String , Object>> GetGuideworkday(int currentPage,int rows)
	{
		int k=(currentPage-1)*rows;
		int j=currentPage*rows;
		String sql="SELECT t_guideworkday.*,t_guideinfo.name FROM t_guideworkday, t_guideinfo where"
				+ " t_guideinfo.phone=t_guideworkday.phone "
				+ "AND t_guideworkday.phone in "
				+ "(select phone from t_guideotherinfo where disabled=0 and authorized=1)  "
				+ "LIMIT "+k+" ,"+j+"";
		List<Map<String , Object>> list= jdbcTemplate.queryForList(sql);
		
		return list;
	}
	
	/**
	 * 得到未禁用已审核的讲解员信息数目
	 * @return int
	 * 2017-1-8 18:58:43
	 */
	public int getguideWorkdayCount()
	{
		String sql="SELECT count(*)  FROM t_guideotherinfo where t_guideotherinfo.disabled=0 and "
				+ "t_guideotherinfo.authorized=1";
		int i =jdbcTemplate.queryForObject(sql, Integer.class);
		return i;
	}
	
	/**
	 * 编辑讲解员日程安排信息
	 * @param guideworkday
	 * @return	
	 * 2017-1-8 19:04:19
	 */
	public int UpdateGuideWorkday(Guideworkday guideworkday)
	{
		String sql="update t_guideworkday set one=?,two=?,three=?,four=? where "
				+ "phone=?";
		int i=jdbcTemplate.update(sql, new Object[]{guideworkday.getDay1(),guideworkday.getDay2(),
				guideworkday.getDay3(),guideworkday.getDay4(),guideworkday.getPhone()});
		return i;
	}
	
	/**
	 * 通过手机号查询讲解员日程安排信息
	 * @param phone
	 * @return
	 * 2017-1-8 20:10:47
	 */
	public List<Map<String, Object>> QueryGuideworkdayByPhone(String phone)
	{
		
		String sql="select t_guideworkday.*,t_guideinfo.name from t_guideworkday,t_guideinfo "
				+ "where t_guideworkday.phone='"+phone+"'";
		return jdbcTemplate.queryForList(sql);
		
	}
}
