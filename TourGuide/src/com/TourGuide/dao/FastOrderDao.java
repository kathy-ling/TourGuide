package com.TourGuide.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.TourGuide.common.MyDateFormat;
import com.TourGuide.model.IntroFeeAndMaxNum;

@Repository
public class FastOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	GuideDao guideInfoDao;
	
	@Autowired
	IntroFeeAndMaxNumDao introFeeAndMaxNumDao;
	
	@Autowired
	ScenicSpotDao scenicSpotDao;
	
	/**
	 * 游客发起快捷拼团
	 * @param consistOrderID  拼团订单号
	 * @param scenicID  景区编号
	 * @param visitNum  参观人数
	 * @param guideFee  景区该天的讲解费
	 * @param visitorPhone 游客的手机号
	 * @return
	 */
	public boolean releaseFastOrder(String consistOrderID, String scenicID, 
			int visitNum, int guideFee, String visitorPhone){
		
		boolean bool = false;
		
		int isConsisted = 0;
		int hadPay = 1;
		int totalGuideFee = visitNum * guideFee;
		String orderState = "待接单"; 
		String time = MyDateFormat.form(new Date());
		
		String sqlInsert = "insert into t_consistOrder (consistOrderID,scenicID,"
				+ "produceTime,payTime,visitorPhone,visitNum,guideFee,totalGuideFee,"
				+ "isConsisted,hadPay,orderState) values (?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sqlInsert, new Object[]{consistOrderID, scenicID, 
				time, time, visitorPhone, visitNum, guideFee, totalGuideFee,
				isConsisted, hadPay, orderState});
		
		if(i != 0){
			bool = true;
		}
		return bool;
	}
	
	
	/**
	 * 导游接单前的确认操作
	 * @param guidePhone
	 * @return 返回此次接单的订单号、单人讲解费、最大接单人数
	 */
	public Map<String, Object> confirmBeforTake(String guidePhone){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String orderID = UUID.randomUUID().toString().replace("-", "");
		int visitNum = 0;
		
		//由讲解员手机号获取其所在的景区
		List<Map<String, Object>> list = guideInfoDao.getDetailGuideInfoByPhone(guidePhone);
		String scenicNo = (String) list.get(0).get("scenicBelong");
		String scenicName = (String) scenicSpotDao.getSomeScenicInfoByscenicID(scenicNo).get(0).get("scenicName");
		
		//查询该天该景区的拼单人数和讲解费		
		IntroFeeAndMaxNum introFeeAndMaxNum = 
				introFeeAndMaxNumDao.getIntroFeeAndMaxNum(MyDateFormat.form1(new Date()), scenicNo);
		int maxNum = introFeeAndMaxNum.getMaxNum();
		int guideFee = introFeeAndMaxNum.getFee();
		
		//finishScan: 0-导游未完成快捷拼单  1-快捷拼单完成  2-不是快捷拼单
		String sqlSelect = "select orderID,visitNum from t_consistresult where "
				+ "guidePhone='"+guidePhone+"' and finishScan=0";
		List<Map<String, Object>> listOrder = jdbcTemplate.queryForList(sqlSelect);
		
		//查看讲解员是否有未完成的快捷拼单，若有，返回该拼单的订单号
		if(listOrder.size() != 0){
			orderID = (String) listOrder.get(0).get("orderID");
			visitNum = (int) listOrder.get(0).get("visitNum");
		}else {
			String sqlString = "insert into t_consistresult (orderID,maxNum,"
					+ "scenicID,guideFee,guidePhone) values (?,?,?,?,?)";
			int i = jdbcTemplate.update(sqlString, new Object[]{orderID, 
					maxNum, scenicNo, guideFee,guidePhone});
			
			String sql = "select visitNum from t_consistresult where orderID='"+orderID+"'";
			List<Map<String, Object>> listNum = jdbcTemplate.queryForList(sql);
			if(listNum.size() != 0){
				visitNum = (int) listNum.get(0).get("visitNum");
			}			
		}
								
		map.put("orderID", orderID);
		map.put("maxNum", maxNum);
		map.put("guideFee", guideFee);
		map.put("scenicName", scenicName);
		map.put("visitNum", visitNum);
				
		return map;
	}
	
	
	
	/**
	 * 讲解员加单（快捷拼单）
	 * @param consistOrderID  游客的订单编号
	 * @param orderID   讲解员的订单编号
	 * @param guidePhone  讲解员的手机号
	 * @param num  游客的订单中的参观人数
	 * @return 0 接单失败  1-接单成功  -1-已经被接单
	 */
	public int takeFastOrder(String consistOrderID, String guidePhone, int num){
		
		int ret = 0;
		
		String time = MyDateFormat.form(new Date());
		String orderState = "待游览";
		int isConsisted = 1;
		String orderID = null;
		
		String sqlSelect = "select orderID,visitNum from t_consistresult where "
				+ "guidePhone='"+guidePhone+"' and finishScan=0";
		List<Map<String, Object>> listOrder = jdbcTemplate.queryForList(sqlSelect);
		
		if(listOrder.size() != 0){
			orderID = (String) listOrder.get(0).get("orderID");
		}
		
		//beScanned标志，使游客的拼单只能被导游扫描一次;1-已被扫描 ，0-未被扫描
		String sqlUpdate = "update t_consistOrder set orderID='"+orderID+"',"
				+ "takeOrderTime='"+time+"',guidePhone='"+guidePhone+"',"
				+ "orderState='"+orderState+"',isConsisted='"+isConsisted+"'"
				+ "where consistOrderID='"+consistOrderID+"' and beScanned=0";
		int i = jdbcTemplate.update(sqlUpdate);
		
		String date = MyDateFormat.form1(new Date());
		String sqlString = "update t_consistOrder set beScanned=1"
				+ " where consistOrderID='"+consistOrderID+"'";
		int j = jdbcTemplate.update(sqlString);
		
		String sql = "update t_consistresult set visitNum=visitNum+"+num+" where orderID='"+orderID+"'";
		int k = jdbcTemplate.update(sql);
		
		if(i!=0 && j!= 0 && k!=0){
			ret = 1;
		}else if(i!=0){
			ret = -1;
		}
		
		return ret;
		
	}
}
