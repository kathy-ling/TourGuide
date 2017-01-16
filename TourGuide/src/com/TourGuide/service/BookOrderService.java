package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.BookOrderDao;

@Service
public class BookOrderService {
	
	@Autowired
	public BookOrderDao bookOrderDao;
	
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
		
		return bookOrderDao.ReleaseBookOrder(bookOrderID, scenicID, produceTime, 
				visitTime, visitNum, language, guideSex, visitorPhone, visitorName, 
				priceRange, purchaseTicket, otherCommand, releaseByVisitor, orderState);
	}
	
	/**
	 * 
	 * @param currentPage
	 * @param pageRows
	 * @return
	 */
	
	public List<Map<String , Object>> GetBookorderBypage(int currentPage,int pageRows) 
	{
		return bookOrderDao.GetBookorderBypage(currentPage, pageRows);
	}
	
	/**
	 * 得到订单信息表的数目
	 * @return
	 * 2017-1-14 20:20:34
	 */
	public int GetBookorderCount() {
		return bookOrderDao.GetBookorderCount();
	}
	
	public List<Map<String , Object>> GetBookorderBySearch(int currentPage,int pageRows,String word,String value )
	{
		return bookOrderDao.GetBookorderBySearch(currentPage,pageRows,word, value);
	}
	
	public int GetBookorderBySearchCount(String word,String value)
	{
		return bookOrderDao.GetBookorderBySearchCount(word, value);
	}
}
