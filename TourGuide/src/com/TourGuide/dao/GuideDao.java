package com.TourGuide.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			guideInfo.setWorkAge((int) list.get(k).get("workAge"));
			guideInfo.setCertificateID((String) list.get(k).get("certificateID"));
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
				guideInfo.setWorkAge(res.getInt(5));
				guideInfo.setCertificateID(res.getString(6));
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
		String sql1 = "select count(*) from t_guideinfo where certificateID = '" + guideInfo.getCertificateID() + "'";
		String sql2 = "insert into t_guideinfo values(?,?,?,?,?,?,?,?)";
		if(jdbcTemplate.queryForObject(sql1, Integer.class) != 0)
			return false;
		else {
			jdbcTemplate.update(sql2,new Object[]{guideInfo.getPhone(), guideInfo.getName(), guideInfo.getSex(),
					guideInfo.getAge(), guideInfo.getWorkAge(), guideInfo.getCertificateID(), guideInfo.getLanguage(), 
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
				guideInfo.getAge(),
				guideInfo.getWorkAge(),
				guideInfo.getCertificateID()});
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
}
