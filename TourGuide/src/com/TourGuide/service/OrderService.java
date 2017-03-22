package com.TourGuide.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TourGuide.dao.OrderDao;



@Service
public class OrderService {

	@Autowired
	public OrderDao orderDao;
	
	/**
	 * 根据用户的手机号，查询用户的所有订单
	 * 全部订单包括：拼单（用户自己发起的拼单、与他人进行拼单）
	 * 			  预约单（选择讲解员进行预约、自己发布预约订单）
	 * @param phone  用户手机号
	 * @return 订单编号、参观时间、参观人数、景区名称、图片（暂时不用）、订单状态、总金额
	 */
	public List<Map<String , Object>> getAllOrders(String phone){
		return orderDao.getAllOrders(phone);
	}
	
	
	/**
	 * 根据订单编号和订单状态，查询订单的详细信息
	 * @param orderID  订单编号
	 * @param orderState   订单状态
	 * @return
	 */
	public List<Map<String, Object>> getDetailOrderInfo(String orderID){
		return orderDao.getDetailOrderInfo(orderID);
	}
	
//	
//	/**
//	 * 根据用户的手机号，查询特定订单状态的订单
//	 * 订单状态：待接单、待付款、待游览、待评价
//	 * @param phone  手机号
//	 * @param orderState   订单状态
//	 * @return
//	 */
//	public List<Map<String, String>> getOrdersWithState(String phone, String orderState){
//		return orderDao.getOrdersWithState(phone, orderState);
//	}
	

}
