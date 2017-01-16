package com.TourGuide.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 将游客自己发布的预约订单存入数据库
	 * @param bookOrderID  订单编号，使用JDK的UUID产生
	 * @param scenicID  游客所预约的景区对应的景区编号
	 * @param produceTime  该订单的生成时间
	 * @param visitTime  游客参观的时间
	 * @param visitNum   参观的人数
	 * @param language  讲解员的讲解语言
	 * @param guideSex   讲解员的性别
	 * @param visitorPhone   游客的手机号
	 * @param visitorName   游客的姓名
	 * @param priceRange   游客可接受的价位区间
	 * @param purchaseTicket  是否代购门票
	 * @param otherCommand    其他需求
	 * @param releaseByVisitor  标志该订单是由游客自己发布的
	 * @param orderState 订单的状态
	 * @return 发布订单是否成功，成功：1  失败：0
	 */
	public boolean ReleaseBookOrder(String bookOrderID, String scenicID, String produceTime,String visitTime, 
			int visitNum, String language, String guideSex, String visitorPhone,String visitorName, 
			String priceRange, int purchaseTicket, String otherCommand, int releaseByVisitor, String orderState){
		
		boolean bool = false;
		
		String sqlString = "insert into t_bookorder (bookOrderID,scenicID,produceTime,"
				+ "visitTime,visitNum,language,guideSex,visitorPhone,visitorName,"
				+ "priceRange,purchaseTicket,otherCommand,releaseByVisitor,orderState) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlString, new Object[]{bookOrderID, scenicID, 
				produceTime, visitTime,visitNum, language, guideSex, visitorPhone, 
				visitorName, priceRange, purchaseTicket, otherCommand, releaseByVisitor, orderState});
		
		if(i == 1){
			bool = true;
		}
		return bool;
	}
	
	/**
	 * 得到订单信息并进行分页获取
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * 2017-1-14 19:49:47
	 */
	public List<Map<String , Object>> GetBookorderBypage(int currentPage,int pageRows) 
	{
		int k=(currentPage-1)*pageRows;
		String sql="select t_bookorder.*,t_visitor.name,t_visitor.phone,"
				+ "t_scenicspotinfo.scenicName "
				+ "from t_visitor,t_bookorder,t_scenicspotinfo"
				+ " where t_bookorder.visitorPhone=t_visitor.phone and t_bookorder.scenicID = t_scenicspotinfo.scenicNo"
				+ " LIMIT "+k+" ,"+pageRows+"";
		return jdbcTemplate.queryForList(sql);	
	}
	
	/**
	 * 得到订单信息表的数目
	 * @return
	 * 2017-1-14 20:20:03
	 */
	public int GetBookorderCount() {
		String sql="select t_bookorder.*,t_visitor.name,t_visitor.phone,"
				+ "t_scenicspotinfo.scenicName "
				+ "from t_visitor,t_bookorder,t_scenicspotinfo"
				+ " where t_bookorder.visitorPhone=t_visitor.phone "
				+ "and t_bookorder.scenicID = t_scenicspotinfo.scenicNo";
		 
		 return jdbcTemplate.queryForList(sql).size();
	}
	
	
	public List<Map<String , Object>> GetBookorderBySearch(int currentPage,int pageRows,String word,String value )
	{
		
		int k=(currentPage-1)*pageRows;
		String a="t_bookorder."+word+"='"+value+"'";
		if (word.equals("visitTime")) {
			a="t_bookorder."+word+">'"+value+"'";
		}
		String sql="select t_bookorder.*,t_visitor.name,t_visitor.phone,"
				+ "t_scenicspotinfo.scenicName "
				+ "from t_visitor,t_bookorder,t_scenicspotinfo"
				+ " where t_bookorder.visitorPhone=t_visitor.phone "
				+ "and t_bookorder.scenicID = t_scenicspotinfo.scenicNo and "+a
				+" LIMIT "+k+" ,"+pageRows+"";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	public int GetBookorderBySearchCount(String word,String value) 
	{
		String a="t_bookorder."+word+"='"+value+"'";
		String sql="select t_bookorder.*,t_visitor.name,t_visitor.phone,"
				+ "t_scenicspotinfo.scenicName "
				+ "from t_visitor,t_bookorder,t_scenicspotinfo"
				+ " where t_bookorder.visitorPhone=t_visitor.phone "
				+ "and t_bookorder.scenicID = t_scenicspotinfo.scenicNo and "+a;
		
		return jdbcTemplate.queryForList(sql).size();
		
	}
}
