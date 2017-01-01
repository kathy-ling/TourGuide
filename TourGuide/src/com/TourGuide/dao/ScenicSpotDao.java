package com.TourGuide.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.ScenicsSpotInfo;
import com.TourGuide.service.ScenicSpotService;

@Repository
public class ScenicSpotDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final int scenicNum = 6;  //推荐的热门景点的数目
	private final int isHotSpot = 1;  //是否是热门景点，是：1  否：0
	private final int recommandNum = 4;  //相关景点的推荐数目
	
	
	/**
	 * 根据用户的位置（省份），获取对应省份的热门景点
	 * @param location 用户当前的位置
	 * @return 景区图片、编号、名称、简介、省、市、详细位置、等级、历史参观人数、开放时间
	 */
 	public List<ScenicsSpotInfo> getScenicByLocation(String location){
		
		List<ScenicsSpotInfo> listResult = new ArrayList<>();
		String sqlString = "select * from `t_scenicspotinfo`  where  province=? and isHotSpot=? limit ?";
		
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlString
				,new Object[]{location,isHotSpot,scenicNum});
		
		for (int j = 0; j <list2.size(); j++){
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicImagePath((String)list2.get(j).get("scenicImagePath"));
			scenicsSpotInfo.setScenicNo((String)list2.get(j).get("scenicNo"));
			scenicsSpotInfo.setScenicName((String)list2.get(j).get("scenicName"));
			scenicsSpotInfo.setScenicIntro((String)list2.get(j).get("scenicIntro"));
			scenicsSpotInfo.setProvince((String)list2.get(j).get("province"));
			scenicsSpotInfo.setCity((String)list2.get(j).get("city"));
			scenicsSpotInfo.setScenicLocation((String)list2.get(j).get("scenicLocation"));
			scenicsSpotInfo.setScenicLevel((String)list2.get(j).get("scenicLevel"));
			scenicsSpotInfo.setOpeningHours((String)list2.get(j).get("openingHours"));
			scenicsSpotInfo.setTotalVisits((String)list2.get(j).get("totalVisits"));
			listResult.add(scenicsSpotInfo);
		}
		
		return listResult;
	}
	
	
	/**
	 * 根据用户所在的省份，获取该省内的所有景点
	 * @param location 用户所在的省份
	 * @return 景区图片、编号、名称、简介、省、市、详细位置、等级、历史参观人数、开放时间
	 */
	public List<ScenicsSpotInfo> getAllScenicByLocation(String location){
		
		List<ScenicsSpotInfo> listResult = new ArrayList<>();
		String sqlString = "select * from `t_scenicspotinfo`  where  province=?";
		
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlString
				,new Object[]{location});
		
		for (int j = 0; j <list2.size(); j++){
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicImagePath((String)list2.get(j).get("scenicImagePath"));
			scenicsSpotInfo.setScenicNo((String)list2.get(j).get("scenicNo"));
			scenicsSpotInfo.setScenicName((String)list2.get(j).get("scenicName"));
			scenicsSpotInfo.setScenicIntro((String)list2.get(j).get("scenicIntro"));
			scenicsSpotInfo.setProvince((String)list2.get(j).get("province"));
			scenicsSpotInfo.setCity((String)list2.get(j).get("city"));
			scenicsSpotInfo.setScenicLocation((String)list2.get(j).get("scenicLocation"));
			scenicsSpotInfo.setScenicLevel((String)list2.get(j).get("scenicLevel"));
			scenicsSpotInfo.setOpeningHours((String)list2.get(j).get("openingHours"));
			scenicsSpotInfo.setTotalVisits((String)list2.get(j).get("totalVisits"));
			listResult.add(scenicsSpotInfo);
		}
		return listResult;
	}

	
	/**
	 * 1、根据景区的名称进行搜索。
	 * 2、根据搜索的特定的景区的地址，进行相关的景区推荐，暂定推荐数为4个
	 * @param name 景区的名称
	 * @return  此景区的详细信息，相关的推荐景区的详细信息。
	 * 景区图片、编号、名称、简介、省、市、详细位置、等级、历史参观人数、开放时间
	 */
	public List<ScenicsSpotInfo> getScenicByNameAndRelates(String name){
		
		final ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
		String cityString = null;
		List<ScenicsSpotInfo> listResult = new ArrayList<>();
		
		//根据景区的名称，搜索对应景区的详细信息
		String sqlSearch = "select scenicImagePath,scenicNo,scenicName,scenicIntro,"
				+ "province,city,scenicLocation,scenicLevel,openingHours,totalVisits "
				+ "from `t_scenicspotinfo`  where scenicName like '%"+name+"%'";
		
		jdbcTemplate.query(sqlSearch,  new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet res) throws SQLException {
				scenicsSpotInfo.setScenicImagePath(res.getString(1));
				scenicsSpotInfo.setScenicNo(res.getString(2));
				scenicsSpotInfo.setScenicName(res.getString(3));
				scenicsSpotInfo.setScenicIntro(res.getString(4));
				scenicsSpotInfo.setProvince(res.getString(5));
				scenicsSpotInfo.setCity(res.getString(6));
				scenicsSpotInfo.setScenicLocation(res.getString(7));
				scenicsSpotInfo.setScenicLevel(res.getString(8));
				scenicsSpotInfo.setOpeningHours(res.getString(9));
				scenicsSpotInfo.setTotalVisits(res.getString(10));
			}
		});
		
		listResult.add(scenicsSpotInfo);
		cityString = scenicsSpotInfo.getCity();
		
		//根据查询的景区的地址，推荐其它相关的景区
		String sqlString = "select * from t_scenicspotinfo where city like '%"+cityString+"%' limit ?";
		List<Map<String , Object>> list2=jdbcTemplate.queryForList(sqlString
				,new Object[]{recommandNum});
		
		for (int j = 0; j <list2.size(); j++){
			ScenicsSpotInfo scenicsSpotInfos = new ScenicsSpotInfo();
			scenicsSpotInfos.setScenicImagePath((String)list2.get(j).get("scenicImagePath"));
			scenicsSpotInfos.setScenicNo((String)list2.get(j).get("scenicNo"));
			scenicsSpotInfos.setScenicName((String)list2.get(j).get("scenicName"));
			scenicsSpotInfos.setScenicIntro((String)list2.get(j).get("scenicIntro"));
			scenicsSpotInfos.setProvince((String)list2.get(j).get("province"));
			scenicsSpotInfos.setCity((String)list2.get(j).get("city"));
			scenicsSpotInfos.setScenicLocation((String)list2.get(j).get("scenicLocation"));
			scenicsSpotInfos.setScenicLevel((String)list2.get(j).get("scenicLevel"));
			scenicsSpotInfos.setOpeningHours((String)list2.get(j).get("openingHours"));
			scenicsSpotInfos.setTotalVisits((String)list2.get(j).get("totalVisits"));
			listResult.add(scenicsSpotInfos);
		}
		
		return listResult;
	}
}
