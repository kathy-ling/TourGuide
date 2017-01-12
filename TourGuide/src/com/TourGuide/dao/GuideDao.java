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
import org.springframework.stereotype.Repository;

import com.TourGuide.model.GuideInfo;
import com.TourGuide.model.GuideOtherInfo;
@Repository
public class GuideDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	 * 获得所有讲解员的基本信息
	 * */
	public List<GuideInfo> GetGuiderInfoByPage(int currentPage,int rows)
	{
		int i=(currentPage-1)*rows;
		int j=currentPage*rows;
		String sql="SELECT * FROM t_guideinfo LIMIT "+i+" ,"+j+"";
		
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sql);
		List<GuideInfo> listres=new ArrayList<>();
		for (int k = 0; k < list.size(); k++) {
			GuideInfo guideInfo=new GuideInfo();
			guideInfo.setPhone((String) list.get(k).get("phone"));
			guideInfo.setName((String) list.get(k).get("name"));
			guideInfo.setSex((String) list.get(k).get("sex"));
			guideInfo.setAge((int) list.get(k).get("age"));
			guideInfo.setLanguage((String) list.get(k).get("language"));
			guideInfo.setSelfIntro((String) list.get(k).get("selfIntro"));
			listres.add(guideInfo);
		}
		return listres;
	}
	/*
	 * 获得已审核讲解员人数
	 * */
	public int GetGuideCount() {
		String sql="SELECT * FROM t_guideotherinfo where authorized=1";
		return jdbcTemplate.queryForList(sql).size();
	}
	/*
	 * 按证号查找讲解员的基本信息
	 * */
	public List<Map<String , Object>> GetGuiderinfoBystring(String cID)
	{
		
		String sql="SELECT t_guideotherinfo.*,t_guideinfo.*,t_scenicspotinfo.scenicName "
				+ "FROM t_guideotherinfo, t_guideinfo,t_scenicspotinfo "
				+ "where t_guideinfo.phone=t_guideotherinfo.phone"
				+ " AND t_guideinfo.phone in"
				+ "(select phone from t_guideotherinfo "
				+ "where authorized=1) AND 	t_scenicspotinfo.scenicNo=t_guideotherinfo.scenicBelong "
				+ "and t_guideotherinfo.certificateID='"+cID+"'";
		return jdbcTemplate.queryForList(sql);
	}
	/*
	 * 添加讲解员基本信息
	 * */
	public boolean isAdd(GuideInfo guideInfo)
	{
		String sql1 = "select count(*) from t_guideinfo where certificateID = '"  + "'";
		String sql2 = "insert into t_guideinfo values(?,?,?,?,?,?,?,?)";
		if(jdbcTemplate.queryForObject(sql1, Integer.class) != 0)
			return false;
		else {
			jdbcTemplate.update(sql2,new Object[]{guideInfo.getPhone(), guideInfo.getName(), guideInfo.getSex(),
					guideInfo.getAge(),  guideInfo.getLanguage(), 
					guideInfo.getSelfIntro()});
			return true;
		}
	}
	/*
	 * 通过证号删除讲解员
	 * */
	public void DeleteGuideInfoById_Dao(String id) {
		String sql = " delete from t_guideinfo where certificateID = '"+id+"'";
		jdbcTemplate.update(sql);
	}
	/*
	 * 编辑讲解员
	 * */
	public boolean EditGuideInfo_Dao(String level,String historyNum,
			String guideNum,String fee,String phone) {
		String sql = " update t_guideotherinfo set guideLevel=?,historyNum=?,"
				+ "singleMax=?,guideFee=? "
				+"  where phone=?";
		int i=jdbcTemplate.update(sql, new Object[]{
				level,historyNum,guideNum,fee,phone
		});
		if (i>0) return true;
		else return false;
	}
	/*
	 * 获得讲解员的其他详细信息
	 * */
	public List<GuideOtherInfo> LookGuideInfo_Dao(String phone) {
		String sql = "select * from t_guideotherinfo where phone='"+phone+"'";
		final List<GuideOtherInfo> list = new ArrayList<>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rSet) throws SQLException {
				GuideOtherInfo guideOtherInfo = new GuideOtherInfo();
				guideOtherInfo.setPhone(rSet.getString(1));
				guideOtherInfo.setHistoryNum(rSet.getInt(2));
				guideOtherInfo.setSingleMax(rSet.getInt(3));
				guideOtherInfo.setGuideFee(rSet.getInt(4));
				guideOtherInfo.setGuideLevel(rSet.getString(5));
				guideOtherInfo.setAuthorized(rSet.getInt(6));
				guideOtherInfo.setScenicBelong(rSet.getString(7));
				guideOtherInfo.setDisabled(rSet.getInt(8));
				list.add(guideOtherInfo);
			}
		});
		return list;
	}
	/*
	 * 修改讲解员的审核状态
	 * */
	public boolean CheckGuideInfo_Dao(String phone,int historyNum,
				int singleMax,int guideFee,String guideLevel,String scenicBelong,
				int workAge,String certificateID)
	{
		String sql1="select scenicNo from t_scenicspotinfo where scenicName='"+scenicBelong+"'";
		String scenicNo=jdbcTemplate.queryForObject(sql1, String.class);
		String sql = " update t_guideotherinfo set authorized=1, "
				+ " historyNum=?,singleMax=?,guideFee=?,"
				+ "guideLevel=?,scenicBelong=?,workAge=?,"
				+ "certificateID=? where phone='"+phone+"'";
		int i = jdbcTemplate.update(sql,new Object[]{historyNum,singleMax,
				guideFee,guideLevel,scenicNo,workAge,certificateID});
		
		if (i > 0) return true;
		return false;
	}
	/*
	 * 禁用讲解员
	 * */
	public boolean ForbidGuideInfo_Dao(String phone) {
		String sql = " update t_guideotherinfo set disabled=1 where phone='"+phone+"'";
		int i = jdbcTemplate.update(sql);
		
		if (i > 0) return true;
		return false;
	}
	/*
	 * 解禁讲解员
	 * */
	public boolean RelieveGuideInfo_Dao(String phone) {
		String sql = " update t_guidinfo set disabled=0 where phone='"+phone+"'";
		int i = jdbcTemplate.update(sql);
		
		if (i > 0) return true;
		return false;
	}
	
	/**
	 * 已审核讲解员信息获取分页
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	public List<GuideOtherInfo> GetGuideOtherInfoByPage_Dao(int currentPage,int rows)
	{
		int i = (currentPage-1)*rows;
		int j = currentPage*rows;
		String sql = "SELECT * FROM t_guideotherinfo LIMIT "+i+" ,"+j+"";
		
		List<Map<String , Object>> list = jdbcTemplate.queryForList(sql);
		List<GuideOtherInfo> listres = new ArrayList<>();
		for (int k = 0; k < list.size(); k++) {
			GuideOtherInfo guideOtherInfo = new GuideOtherInfo();
			guideOtherInfo.setAuthorized((int)list.get(k).get("authorized"));
			guideOtherInfo.setDisabled((int)list.get(k).get("disabled"));
			guideOtherInfo.setGuideFee((int)list.get(k).get("guideFee"));
			guideOtherInfo.setGuideLevel((String)list.get(k).get("guideLevel"));
			guideOtherInfo.setHistoryNum((int)list.get(k).get("historyNum"));
			guideOtherInfo.setPhone((String)list.get(k).get("phone"));
			guideOtherInfo.setScenicBelong((String)list.get(k).get("scenicBelong"));
			guideOtherInfo.setSingleMax((int)list.get(k).get("singleMax"));
			listres.add(guideOtherInfo);
		}
		return listres;
	}
	
	
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
	
	/**
	 * 分页得到已审核的讲解员
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> GetGuideofYes(int currentPage,int rows)
	{
		int k=(currentPage-1)*rows;
		int j=currentPage*rows;
		String sql="SELECT t_guideotherinfo.*,t_guideinfo.*,t_scenicspotinfo.scenicName "
				+ "FROM t_guideotherinfo, t_guideinfo,t_scenicspotinfo "
				+ "where t_guideinfo.phone=t_guideotherinfo.phone"
				+ " AND t_guideinfo.phone in"
				+ "(select phone from t_guideotherinfo "
				+ "where authorized=1)  and t_scenicspotinfo.scenicNo=t_guideotherinfo.scenicBelong "
				+ " LIMIT "+k+" ,"+j+"";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
		k=list.size();		
		return list;
	}
	
	/**
	 * 分页得到未审核讲解员的信息
	 * @param currentPage
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> GetGuideofNo(int currentPage,int rows)
	{
		int k=(currentPage-1)*rows;
		int j=currentPage*rows;
		String sql="select * from t_guideinfo where t_guideinfo.phone in"
				+ "(select phone from t_guideotherinfo "
				+ "where authorized=0) LIMIT "+k+" ,"+j+"";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);	
		return list;
	}
	
	/**
	 * 得到已审核讲解员的信息数目
	 * @return int
	 * 2017-1-12 08:25:58
	 */
	public int  GetGuideofYesCount()
	{
		String sql="SELECT * FROM t_guideotherinfo where authorized=1";
		return jdbcTemplate.queryForList(sql).size();
	}
	
	/**
	 * 得到未审核讲解员的信息数目
	 * @return int
	 * 2017-1-12 08:25:58
	 */
	public int  GetGuideofNoCount()
	{
		String sql="SELECT * FROM t_guideotherinfo where authorized=0";
		return jdbcTemplate.queryForList(sql).size();
	}
	
	/**
	 * 通过手机号得到未审核讲解员的基本信息
	 * @param phone
	 * @return
	 * 2017-1-12 08:27:11
	 */
	public List<Map<String, Object>> GetGuiderinfoByPhone(String phone) {
		
		String sql="SELECT * FROM t_guideinfo where   phone='"+phone+"'";
		return jdbcTemplate.queryForList(sql);
	}
}
