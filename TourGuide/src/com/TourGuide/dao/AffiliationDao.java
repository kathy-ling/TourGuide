package com.TourGuide.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AffiliationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 导游选中景区后，申请挂靠
	 * @param guidePhone  导游手机号
	 * @param scenicID  景区编号
	 * @param applyDate  申请日期
	 * @return
	 */
	public boolean applyForAffiliation(String guidePhone, String scenicID, String applyDate){
		
		boolean bool = false;
		
		List<Map<String , Object>> listResult = getCurrentAffiliation(guidePhone);
		String belong = listResult.get(0).get("scenicBelong").toString();
		
		if(belong != null){
			
			String sqlUpdate = "update t_guideotherinfo set scenicBelong='"+scenicID+"' "
					+ "where phone='"+guidePhone+"'";
			int i = jdbcTemplate.update(sqlUpdate);
			
			String sqlInsert = "insert into t_affiliation (guidePhone,scenicID,applyDate) "
					+ "values (?,?,?)";
			int j = jdbcTemplate.update(sqlInsert, new Object[]{guidePhone, scenicID, applyDate});
			
			if (i != 0 && j != 0){
				bool = true;
			}
		}
				
		return bool;
	}
	
	
	/**
	 * 取消导游所挂靠的景区
	 * @param guidePhone  导游手机号
	 * @param scenicID   景区编号
	 * @param quitDate  取消挂靠的日期
	 * @return
	 */
	public boolean cancleAffiliation(String guidePhone, String scenicID, String quitDate){
		
		boolean bool = false;
		
		String sqlUpdate = "update t_guideotherinfo set scenicBelong='null' "
				+ "where phone='"+guidePhone+"'";
		int i = jdbcTemplate.update(sqlUpdate);
		
		String sqlInsert = "update t_affiliation set quitDate='"+quitDate+"' "
				+ "where guidePhone='"+guidePhone+"' and scenicID='"+scenicID+"'";
		int j = jdbcTemplate.update(sqlInsert);
		
		if (i != 0 && j != 0){
			bool = true;
		}
		
		return bool;
	}
	
	
	
	/**
	 * 查看当前景区的申请挂靠时间
	 * @param guidePhone  导游手机号
	 * @param scenicID  景区编号
	 * @return
	 */
	public List<Map<String , Object>> getApplyDateofCurrentScenic(String guidePhone, String scenicID){
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		String sql = "select  applyDate from t_affiliation where "
				+ "guidePhone='"+guidePhone+"' and scenicID='"+scenicID+"'";
		listResult = jdbcTemplate.queryForList(sql);
		
		return listResult;
	}
	
	
	
	/**
	 * 查看该导游的挂靠景区记录
	 * @param guidePhone  手机号
	 * @return
	 */
	public List<Map<String , Object>> getApplyHistory(String guidePhone){
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		String sql = "select * from t_affiliation where guidePhone='"+guidePhone+"'";
		listResult = jdbcTemplate.queryForList(sql);
		
		return listResult;
	}
	
	
	/**
	 * 查看该导游的当前挂靠景区
	 * @param guidePhone  手机号
	 * @return
	 */
	public List<Map<String , Object>> getCurrentAffiliation(String guidePhone){
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		String sql = "select scenicBelong from t_guideotherinfo where phone='"+guidePhone+"' ";
		listResult = jdbcTemplate.queryForList(sql);
		
		return listResult;
	}
}
