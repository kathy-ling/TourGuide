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
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.ResultHandler;

import com.TourGuide.model.ScenicsSpotInfo;

@Repository
public class ScenicSpotDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final int scenicNum = 6;  //推荐的热门景点的数目
	private final int isHotSpot = 1;  //是否是热门景点，是：1  否：0
	private final int recommandNum = 4;  //相关景点的推荐数目
	
	
	/**
 	 * 根据景区编号，查看景区的详细信息
 	 * @param scenicNo  景区编号
 	 * @return 景区详细信息
 	 * 景区图片、编号、名称、简介、省、市、详细位置、等级、历史参观人数、开放时间
 	 */
 	public List<Map<String , Object>> getDetailScenicByScenicID(String scenicNo){
 		
 		String sqlString = "select scenicImagePath,scenicNo,scenicName,scenicIntro,province,"
 				+ "city,scenicLocation,scenicLevel,openingHours,totalVisits "
 				+ "from t_scenicspotinfo where scenicNo='"+scenicNo+"'";
 		
 		List<Map<String , Object>> list = jdbcTemplate.queryForList(sqlString);
 		
 		return list;
 	}
	
	
	/**
	 * 根据用户的位置（省份），获取对应省份的热门景点
	 * @param location 用户当前的位置
	 * @return 景区图片、编号、名称
	 */
 	public List<Map<String , Object>> getScenicByLocation(String location){
		
		String sqlString = "select scenicImagePath,scenicNo,scenicName "
				+ "from t_scenicspotinfo  where  province like '%"+location+"%' "
						+ "and isHotSpot='"+isHotSpot+"' limit "+scenicNum+"";
		
		List<Map<String , Object>> list = jdbcTemplate.queryForList(sqlString);
		
		return list;
	}
 	
 	
 	
	
	/**
	 * 根据用户所在的省份，获取该省内的所有景点
	 * @param location 用户所在的省份
	 * @return 景区图片、编号、名称
	 */
	public List<Map<String , Object>> getAllScenicByLocation(String location){
		
		String sqlString = "select scenicImagePath,scenicNo,scenicName "
				+ "from t_scenicspotinfo where province like '%"+location+"%'";
		
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlString);
		
		return list;
	}

	
	/**
	 * 根据景区的名称进行搜索。
	 * @param scenicName  景区的名称
	 * @return
	 */
	public List<Map<String , Object>> getScenicByName(String scenicName){
		
		//根据景区的名称，搜索对应景区的详细信息
		String sqlSearch = "select scenicImagePath,scenicNo,scenicName,scenicIntro,"
				+ "province,city,scenicLocation,scenicLevel,openingHours,totalVisits "
				+ "from `t_scenicspotinfo`  where scenicName='"+scenicName+"'";
		
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlSearch);
		
		return list;
	}
	
	/**
	 * 根据搜索的特定的景区的地址，进行相关的景区推荐，暂定推荐数为4个
	 * @param name 景区的名称
	 * @return 相关的推荐景区的信息。
	 * 景区图片、编号、名称
	 */
	public List<Map<String , Object>> getScenicRelatesByName(String scenicName){
		
		List<Map<String , Object>> list = getScenicByName(scenicName);
		String cityString = null;
		String scenicID = null;
		if(list.size() != 0){
			cityString = (String)list.get(0).get("city");
			scenicID = (String)list.get(0).get("scenicNo");
		}
		
		//根据查询的景区的地址，推荐其它相关的景区
		String sqlString = "select scenicImagePath,scenicNo,scenicName"
				+ " from t_scenicspotinfo where scenicNo !='"+scenicID+"'"
				+ " and city like '%"+cityString+"%' limit ?";
		
		List<Map<String , Object>> listResult=jdbcTemplate.queryForList(sqlString
				,new Object[]{recommandNum});	
		
		return listResult;
	}
	
	
	
	/**
	 * 根据景区的编号， 查询景区的信息,景区名称、图片
	 * @param scenicID   景区编号
	 * @return 景区名称、图片
	 */
	public List<Map<String , Object>> getSomeScenicInfoByscenicID(String scenicID){
		
		String sqlString = "select scenicName,scenicImagePath from t_scenicspotinfo "
				+ "where scenicNo='"+scenicID+"'";
		
		List<Map<String , Object>> list = new ArrayList<>();
		
		list = jdbcTemplate.queryForList(sqlString);
		
		return list;
	}
	
	
	/**
	 * 根据景区名称，搜索名称相似的景区
	 * @param scenicName 景区名称
	 * @return  相似景区的名称、编号
	 */
	public List<Map<String , Object>> getNameSimilarScenics(String scenicName){
		
		String sql = "select scenicName,scenicNo from t_scenicspotinfo  "
				+ "where scenicName like '%"+scenicName+"%'";
		List<Map<String , Object>> list = jdbcTemplate.queryForList(sql);
		return list;
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
	public ScenicsSpotInfo SearchSceincInfoByName_Dao(String a) {
		final ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
		String sql=" select * from t_scenicspotinfo where scenicName = '" + a +"'";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
			@Override
			public void processRow(java.sql.ResultSet rSet) throws SQLException {
				
				
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
			
			}
		});
		return scenicsSpotInfo;
	}
	
	/**
	 * 根据景区的省份、市、详细地址进行景区信息查看
	 * @param provin
	 * @param city
	 * @param s
	 * @return
	 * 2017-1-8 21:40:19
	 */
	public ScenicsSpotInfo SearchSceincInfoByLocation_Dao(String provin,
			String city ,String s) {
		ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
		String sql=" select * from t_scenicspotinfo where province = '"+provin+"' and city='"
				+city+"' and scenicLocation='"+s+"'";
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
		if (list.size()>0) {
			
			scenicsSpotInfo.setScenicNo((String) list.get(0).get("scenicNo"));
			scenicsSpotInfo.setScenicImagePath((String) list.get(0).get("scenicImagePath"));
			scenicsSpotInfo.setScenicName((String) list.get(0).get("scenicName"));
			scenicsSpotInfo.setScenicIntro((String) list.get(0).get("scenicIntro"));
			scenicsSpotInfo.setTotalVisits((String) list.get(0).get("totalVisits"));
			scenicsSpotInfo.setOpeningHours((String) list.get(0).get("openingHours"));
			scenicsSpotInfo.setProvince((String) list.get(0).get("province"));
			scenicsSpotInfo.setCity((String) list.get(0).get("city"));
			scenicsSpotInfo.setScenicLocation((String) list.get(0).get("scenicLocation"));
			scenicsSpotInfo.setIsHotSpot((int) list.get(0).get("isHotSpot"));
			scenicsSpotInfo.setScenicLevel((String) list.get(0).get("scenicLevel"));
			scenicsSpotInfo.setChargePerson((String) list.get(0).get("chargePerson"));
		}
		
		return scenicsSpotInfo;
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


