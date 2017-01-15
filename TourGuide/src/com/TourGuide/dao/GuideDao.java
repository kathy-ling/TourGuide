package com.TourGuide.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	 * @return 0-失败  1-成功  -1-账号已存在
	 */
	public int getGuideAuthentication(String phone, String name,String sex, 
			String language, String selfIntro, String image, int age){
		
		int retValue = 0;
		final GuideInfo guideInfo = new GuideInfo();
		
		String sqlSearch = "select phone from t_guideinfo where phone='"+phone+"'";
		
		jdbcTemplate.query(sqlSearch,  new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet res) throws SQLException {
				guideInfo.setPhone(res.getString(1));
			}
		});
		String phoneExist = guideInfo.getPhone();
		
		if(phoneExist != null){
			
			return retValue = -1;
		}
		
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
			retValue = 1;
		}
		return retValue;
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
	 * 通过手机号删除讲解员
	 * */
	public int DeleteGuideInfoById_Dao(String phone) {
		String sql = " delete from t_guideinfo where phone = '"+phone+"'";
		int i;
		try {
			i= jdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			i=0;
		}
		return i;
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
		String sql = " update t_guideotherinfo set disabled=0 where phone='"+phone+"'";
		int i = jdbcTemplate.update(sql);
		
		if (i > 0) return true;
		return false;
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
		String sql="SELECT t_guideotherinfo.*,t_guideinfo.*,t_scenicspotinfo.scenicName "
				+ "FROM t_guideotherinfo, t_guideinfo,t_scenicspotinfo "
				+ "where t_guideinfo.phone=t_guideotherinfo.phone"
				+ " AND t_guideinfo.phone in"
				+ "(select phone from t_guideotherinfo "
				+ "where authorized=1)  and t_scenicspotinfo.scenicNo=t_guideotherinfo.scenicBelong "
				+ " LIMIT "+k+" ,"+rows+"";
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
		String sql="select * from t_guideinfo where t_guideinfo.phone in"
				+ "(select phone from t_guideotherinfo "
				+ "where authorized=0) LIMIT "+k+" ,"+rows+"";
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
