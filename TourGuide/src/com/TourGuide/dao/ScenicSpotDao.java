package com.TourGuide.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.model.ScenicsSpotInfo;

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
	
	
	
	/**
	 * 根据景区的编号， 查询景区的信息,景区名称、图片
	 * @param scenicID   景区编号
	 * @return 景区名称、图片
	 */
	public Map<String, String> getSomeScenicInfoByscenicID(String scenicID){
		
		final Map<String, String> map = new HashMap<String, String>();
		String sqlString = "select scenicName,scenicImagePath from t_scenicspotinfo "
				+ "where scenicNo='"+scenicID+"'";
		
		jdbcTemplate.query(sqlString,  new RowCallbackHandler() {
					
					@Override
					public void processRow(ResultSet res) throws SQLException {
						map.put("scenicName", res.getString(1));
						map.put("scenicImagePath", res.getString(2));
					}
		});
		return map;
	}
	
	
	/*
	 *通过页数与页数容量来获取景区信息 
	 * time：2017-1-2 10:37:30
	 * */
	public List<ScenicsSpotInfo> GetScenicInfoByPage(int currentPage,int rows)
	{
		int j=(currentPage-1)*rows;
		String sql="SELECT * FROM t_scenicspotinfo LIMIT "+j+" ,"+rows+"";
		
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
		List<ScenicsSpotInfo> listres=new ArrayList<>();
		for (int k = 0; k < list.size(); k++) {
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicNo((String) list.get(k).get("scenicNo"));
			scenicsSpotInfo.setScenicName((String) list.get(k).get("scenicName"));
			scenicsSpotInfo.setTotalVisits((String) list.get(k).get("totalVisits"));
			scenicsSpotInfo.setOpeningHours((String) list.get(k).get("openingHours"));
			scenicsSpotInfo.setScenicLevel((String) list.get(k).get("scenicLevel"));
			scenicsSpotInfo.setScenicLocation((String) list.get(k).get("scenicLocation"));
			scenicsSpotInfo.setProvince((String) list.get(k).get("province"));
			scenicsSpotInfo.setCity((String) list.get(k).get("city"));
			scenicsSpotInfo.setChargePerson((String) list.get(k).get("chargePerson"));
			scenicsSpotInfo.setIsHotSpot((int) list.get(k).get("isHotSpot"));
			scenicsSpotInfo.setScenicIntro((String) list.get(k).get("scenicIntro"));
			listres.add(scenicsSpotInfo);
		}
		
		return listres;
	}
	
	public  int  GetScenicCount() {
		String sql="SELECT * FROM t_scenicspotinfo";
		return jdbcTemplate.queryForList(sql).size();
	}
	
	
	/*
	 * 通过sql查询语句进行查询景区的详细信息
	 * 参数：SQL语句
	 * 2017-1-2 10:36:30
	 * */
	public List<ScenicsSpotInfo> SearchSceincInfoByName_Dao(String a) {
		final List<ScenicsSpotInfo> list = new ArrayList<ScenicsSpotInfo>();
		String sql=" select * from t_scenicspotinfo where scenicName = '" + a +"'";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
			@Override
			public void processRow(java.sql.ResultSet rSet) throws SQLException {
				
				ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
				scenicsSpotInfo.setScenicNo(rSet.getString(1));
				scenicsSpotInfo.setScenicImagePath(rSet.getString(2));
				scenicsSpotInfo.setScenicName(rSet.getString(3));
				scenicsSpotInfo.setScenicIntro(rSet.getString(4));
				scenicsSpotInfo.setTotalVisits(rSet.getString(5));
				scenicsSpotInfo.setOpeningHours(rSet.getString(6));
				scenicsSpotInfo.setProvince(rSet.getString(7));
				scenicsSpotInfo.setCity(rSet.getString(8));
				scenicsSpotInfo.setScenicLocation(rSet.getString(9));
				scenicsSpotInfo.setIsHotSpot(rSet.getInt(10));
				scenicsSpotInfo.setScenicLevel(rSet.getString(11));
				scenicsSpotInfo.setChargePerson(rSet.getString(12));
				list.add(scenicsSpotInfo);
			}
		});
		return list;
	}
	/*
	 * 增加景区信息
	 * 参数：景区信息类
	 * 2017-1-2 10:36:30
	 * */
	public boolean AddScenicInfo_Dao(ScenicsSpotInfo scenicsSpotInfo) {
		String sql = " select count(*) from t_scenicspotinfo where scenicName = '"
					+scenicsSpotInfo.getScenicName()+"'";
		 
		
		if (jdbcTemplate.queryForObject(sql, Integer.class) == 0) {
			sql =  " insert into t_scenicspotinfo values (?,?,?,?,?,?,?,?,?,?,?,?) ";
			jdbcTemplate.update(sql, new Object[]{
				scenicsSpotInfo.getScenicNo(),
				"0",
				scenicsSpotInfo.getScenicName(),
				scenicsSpotInfo.getScenicIntro(),
				scenicsSpotInfo.getTotalVisits(),
				scenicsSpotInfo.getOpeningHours(),
				scenicsSpotInfo.getProvince(),
				scenicsSpotInfo.getCity(),
				scenicsSpotInfo.getScenicLocation(),
				scenicsSpotInfo.getIsHotSpot(),
				scenicsSpotInfo.getScenicLevel(),
				scenicsSpotInfo.getChargePerson()
			});
			return true;
		}
		return false;
	}
	/*
	 * 	删除景区信息
	 * 	参数：景区名称
	 * 2017-1-2 10:35:30
	 * */
	public boolean DeleteScenicInfo(String s) {
		String sql = "delete from t_scenicspotinfo where scenicName='"+s+"'";
		if (jdbcTemplate.update(sql)>0) {
			return true;
		} else {
			return false;
		}
		
		
	}
	
	/*
	 * 更新景区信息
	 * 参数：景区信息类
	 * 2017-1-2 10:34:30
	 * */
	public boolean UpdateScenicInfo(ScenicsSpotInfo scenicsSpotInfo) {
		String sql = " update t_scenicspotinfo set scenicNo=?,scenicImagePath=?,scenicName=?, "+
					" scenicIntro=?,totalVisits=?,openingHours=?,province=?,city=?,scenicLocation=?, "+
					" isHotSpot=?,scenicLevel=?,chargePerson=?  where scenicName=? ";
		int i=jdbcTemplate.update(sql, new Object[]{
				scenicsSpotInfo.getScenicNo(),
				scenicsSpotInfo.getScenicImagePath(),
				scenicsSpotInfo.getScenicName(),
				scenicsSpotInfo.getScenicIntro(),
				scenicsSpotInfo.getTotalVisits(),
				scenicsSpotInfo.getOpeningHours(),
				scenicsSpotInfo.getProvince(),
				scenicsSpotInfo.getCity(),
				scenicsSpotInfo.getScenicLocation(),
				scenicsSpotInfo.getIsHotSpot(),
				scenicsSpotInfo.getScenicLevel(),
				scenicsSpotInfo.getChargePerson(),
				scenicsSpotInfo.getScenicName()});
		if (i>0) {
			return true;
		} else {
			return false;
		}
		
		
	}
}


