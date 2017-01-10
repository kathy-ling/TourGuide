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
	
	
	/**
	 * 讲解员提交相应的信息，申请认证
	 * @param phone  手机号
	 * @param name  姓名
	 * @param sex  性别
	 * @param language  讲解语言
	 * @param selfIntro   自我介绍
	 * @param image  头像
	 * @param age    年龄
	 * @return
	 */
	public boolean getGuideAuthentication(String phone, String name,String sex, 
			String language, String selfIntro, String image, int age){
		
		boolean bool = false;
		
		//向t_guideinfo表中插入导游的基本信息
		String sqlString = "insert into t_guideinfo (phone,name,sex,language,selfIntro,image,age) "
				+ "values (?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{phone, name, sex,
				language, selfIntro, image, age});
		
		//向t_guideotherinfo插入其他的信息
		String sqlString2 = "insert into t_guideotherinfo (phone,historyNum,authorized,disabled) "
				+ "values (?,?,?,?)";
		int j = jdbcTemplate.update(sqlString2, new Object[]{phone, 0, 0, 0});
		
		if (i!=0 && j!=0) {
			bool = true;
		}
		return bool;
	}
	
	
	/**
	 * 查询最受欢迎的讲解员,暂定显示10个
	 * 查询条件：级别、历史带团人数、是否认证、是否禁用（先按级别排序，再按带团人数排序）
	 * @return  讲解员的基本信息及级别
	 * phone,image,name,sex,age,language,selfIntro,guideLevel
	 */
	public List<Map<String, Object>> getPopularGuides(){
		
		int popularNum = 10;
		
		String sqlString = "select t_guideinfo.*,t_guideotherinfo.guideLevel from t_guideinfo,t_guideotherinfo "
				+ "where t_guideinfo.phone=t_guideotherinfo.phone and t_guideotherinfo.phone in "
				+ "(select phone from t_guideotherinfo where disabled=0 and authorized=1 "
				+ "order by historyNum,guideLevel desc) limit ?";
		List<Map<String , Object>> list=jdbcTemplate.queryForList(sqlString, new Object[]{popularNum});
		
		return list;
	}
	
	
	
	/**
	 * 查询可被预约的讲解员
	 * 查询条件：讲解员的工作时间、单次最大带团人数、所属景区、是否认证、是否禁用、级别
	 * @param visitTime  游客的参观时间
	 * @param visitNum  参观的人数
	 * @param scenicID  景区编号
	 * @return 可被预约的讲解员的基本信息（按星级排序）
	 * phone,image,name,sex,age,language,selfIntro,guideLevel
	 */
	public List<Map<String, Object>> getAvailableGuides(String visitTime, 
			int visitNum, String scenicID){
		
		
		
		List<Map<String , Object>> list = null;
		
		return list;
	}
	
	
	
	
	
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
	 * 获得讲解员人数
	 * */
	public int GetGuideCount() {
		String sql="SELECT * FROM t_guideinfo";
		return jdbcTemplate.queryForList(sql).size();
	}
	/*
	 * 按证号查找讲解员的基本信息
	 * */
	public List<GuideInfo> GetGuiderinfoBystring(String cID)
	{
		final List<GuideInfo> list = new ArrayList<>();
		String sql = "select * from t_guideinfo where certificateID = '" + cID + "'";
		jdbcTemplate.query(sql,  new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet res) throws SQLException{
				GuideInfo guideInfo = new GuideInfo();
				guideInfo.setPhone(res.getString(1));
				guideInfo.setName(res.getString(2));
				guideInfo.setSex(res.getString(3));
				guideInfo.setAge(res.getInt(4));
				guideInfo.setLanguage(res.getString(7));
				guideInfo.setSelfIntro(res.getString(8));
				list.add(guideInfo);
			}
		});
		return list;
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
	public boolean EditGuideInfo_Dao(GuideInfo guideInfo) {
		String sql = " update t_guideinfo set phone=?,name=?,sex=?,language=?, "
				+" selfIntro=?,age=?,workAge=? where certificateID=?";
		int i=jdbcTemplate.update(sql, new Object[]{
				guideInfo.getPhone(),
				guideInfo.getName(),
				guideInfo.getSex(),
				guideInfo.getLanguage(),
				guideInfo.getSelfIntro(),
				guideInfo.getAge()});
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
	public boolean CheckGuideInfo_Dao(String phone) {
		String sql = " update t_guideotherinfo set authorized=1 where phone='"+phone+"'";
		int i = jdbcTemplate.update(sql);
		
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
		String sql = " update t_guideotherinfo set disabled=0 where phone='"+phone+"'";
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
	
}
