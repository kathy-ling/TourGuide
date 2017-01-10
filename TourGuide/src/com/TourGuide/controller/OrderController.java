package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.dao.ScenicTicketsDao;
import com.TourGuide.service.OrderService;
import com.google.gson.Gson;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;
	
	
	/**
	 * 根据用户的手机号，查询用户的所有订单
	 * @param resp
	 * @param visitorPhone  用户手机号
	 * @throws IOException
	 * 订单编号、参观时间、参观人数、景区名称、图片（暂时不用）、订单状态、总金额
	 */
	@RequestMapping(value = "/getAllOrders.do")
	public void getAllOrders(HttpServletResponse resp,
			@RequestParam("visitorPhone") String visitorPhone) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String, String>> listResult =orderService.getAllOrders(visitorPhone);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listResult));
		writer.flush();
	}
	
	
	
	/**
	 * 根据订单的状态，查看用户相应的订单
	 * 订单状态：待接单、待付款、待游览、待评价
	 * @param resp
	 * @param visitorPhone   手机号
	 * @param orderState   订单状态
	 * @throws IOException
	 * 订单编号、参观时间、参观人数、景区名称、图片（暂时不用）、订单状态、总金额
	 */
	@RequestMapping(value = "/getOrdersWithState.do")
	public void getOrdersWithState(HttpServletResponse resp,
			@RequestParam("visitorPhone") String visitorPhone,
			@RequestParam("orderState") String orderState) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String, String>> listResult =orderService.getOrdersWithState(visitorPhone, orderState);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listResult));
		writer.flush();
	}
	
	
}
