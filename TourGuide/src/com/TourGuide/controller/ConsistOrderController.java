package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.common.MyDateFormat;
import com.TourGuide.common.TotalMoney;
import com.TourGuide.model.ConsistOrder;
import com.TourGuide.model.ScenicTickets;
import com.TourGuide.service.ConsistOrderService;
import com.TourGuide.service.IntroFeeAndMaxNumService;
import com.TourGuide.service.ScenicTicketService;
import com.google.gson.Gson;

@Controller
public class ConsistOrderController {
	
	@Autowired
	IntroFeeAndMaxNumService introFeeService;
	
	@Autowired
	ConsistOrderService consistOrderService;
	
	@Autowired
	ScenicTicketService scenicTicketService;
	
	private final String orderState = "待接单";  //游客刚发布的拼单的状态为“待接单”
	
	private int isConsisted = 0;  //游客刚发布的拼单，没有被拼单
	
	private int fee = 0;  //讲解费,？元/人
	
	
	/**
	 * 查询某天该景区的拼单讲解费
	 * @param resp
	 * @param scenicID  景区编号
	 * @param date   日期
	 * @throws IOException
	 */
	@RequestMapping(value = "/getIntroFee.do")
	public void getIntroFee(HttpServletResponse resp,
			@RequestParam("scenicID") String scenicID, 
			@RequestParam("date") String date) throws IOException{
	
		CommonResp.SetUtf(resp);
		
		fee = introFeeService.getIntroFee(date, scenicID);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(fee));
		writer.flush();
	}
	
	
	
	/**
	 * 将游客自己发布的拼单订单存入数据库
	 * @param resp
	 * @param scenicID  景区编号
	 * @param visitTime 游客参观的时间
	 * @param visitNum  参观的人数
	 * @param visitorPhone  游客的手机号
	 * @param purchaseTicket   是否代购门票
	 * @param halfPrice  若代购门票，购买半价票的人数
	 * @param discoutPrice 若代购门票，购买折扣票的人数
	 * @param fullPrice  若代购门票，购买全价票的人数
	 * @throws IOException
	 */
	@RequestMapping(value = "/releaseConsistOrder.do")
	public void releaseConsistOrder(HttpServletResponse resp,
			@RequestParam("scenicID") String scenicID, 
			@RequestParam("visitTime") String visitTime, 
			@RequestParam("visitNum") String visitNum,
			@RequestParam("visitorPhone") String visitorPhone,
			@RequestParam("purchaseTicket") String purchaseTicket,
			@RequestParam("halfPrice") String halfPrice,
			@RequestParam("discoutPrice") String discoutPrice,
			@RequestParam("fullPrice") String fullPrice) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		String consistOrderID = UUID.randomUUID().toString().replace("-", "");
		String orderID = UUID.randomUUID().toString().replace("-", "");
		String produceTime = MyDateFormat.form(new Date());
		
		//查询该景区的门票信息
		ScenicTickets scenicTickets = scenicTicketService.geTicketsByScenicNo(scenicID);
		
		//查询给景区该日的最大可拼单人数
		String[] date = visitTime.split(" ");
		int maxNum = introFeeService.getMaxNum(date[0], scenicID);
		
		//计算每个订单的应付总额
		int totalMoney = TotalMoney.getTotalMoney(fee, Integer.parseInt(visitNum), Integer.parseInt(purchaseTicket), 
				scenicTickets.getHalfPrice(), scenicTickets.getDiscoutPrice(), scenicTickets.getFullPrice(), 
				Integer.parseInt(halfPrice), Integer.parseInt(discoutPrice), Integer.parseInt(fullPrice));
		
		boolean bool = consistOrderService.ReleaseConsistOrder(consistOrderID, orderID, scenicID,
				produceTime, visitTime, Integer.parseInt(visitNum), visitorPhone, 
				totalMoney, Integer.parseInt(purchaseTicket), orderState, isConsisted, maxNum);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(bool));
		writer.flush();
	}
	
	
	
	/**
	 * 查询该景区当前可拼单的订单
	 * @param resp
	 * @param scenicID  景区编号
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAvailableConsistOrder.do")
	public void getAvailableConsistOrder(HttpServletResponse resp,
			@RequestParam("scenicID") String scenicID )throws IOException{
		
		CommonResp.SetUtf(resp);
		
		String timeNow = MyDateFormat.form(new Date());		
		
		List<ConsistOrder> list = consistOrderService.getAvailableConsistOrder(scenicID, timeNow);
		List<Map<String, Object>> listresult = new ArrayList<Map<String, Object>>();
		
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderID", list.get(i).getOrderID());
			map.put("visitTime", list.get(i).getVisitTime());
			map.put("visitNum", list.get(i).getCurrentNum());
			map.put("maxNum", list.get(i).getMaxNum());
			listresult.add(map);
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listresult));
		writer.flush();
	}

	
	
	/**
	 * 从可拼单列表中选择订单，进行拼单
	 * 
	 * @param resp
	 * @param orderID  订单编号
	 * @param visitNum  拼单的参观人数
	 * @param visitorPhone  游客的手机
	 * @param purchaseTicket  是否代购门票
	 * @param halfPrice  若代购门票，购买半价票的人数
	 * @param discoutPrice 若代购门票，购买折扣票的人数
	 * @param fullPrice  若代购门票，购买全价票的人数
	 * @throws IOException
	 */
	@RequestMapping(value = "/consistWithconsistOrderID.do")
	public void consistWithconsistOrderID(HttpServletResponse resp,
			@RequestParam("orderID") String orderID,  
			@RequestParam("visitNum") String visitNum,
			@RequestParam("visitorPhone") String visitorPhone,
			@RequestParam("purchaseTicket") String purchaseTicket,
			@RequestParam("halfPrice") String halfPrice,
			@RequestParam("discoutPrice") String discoutPrice,
			@RequestParam("fullPrice") String fullPrice) throws IOException{
		
		//orderID=4e06cb84e1684ad28bca53bd1e7ed295&visitNum=6&visitorPhone=18756952269&purchaseTicket=0&halfPrice=0&discoutPrice=0&fullPrice=0
		
		CommonResp.SetUtf(resp);
		
		String consistOrderID = UUID.randomUUID().toString().replace("-", "");
		String produceTime = MyDateFormat.form(new Date());
		
		//根据订单编号，查询该订单中的所有拼单的编号
		List<String> ConsistOrderIDList = consistOrderService.getConsistOrderIDsWithOrderID(orderID);
		
		//查询订单编号中，第一个拼单编号的详细信息
		ConsistOrder consistOrder = consistOrderService.getDetailConsistOrder(ConsistOrderIDList.get(0));
		
		//查询该订单的参观总人数
		int currentNum = 0;
		for(int i=0; i<ConsistOrderIDList.size(); i++){
			//单个拼单的参观人数
			ConsistOrder order = consistOrderService.getDetailConsistOrder(ConsistOrderIDList.get(i));
			int num = order.getVisitNum();
			currentNum += num;
		}
		currentNum += Integer.parseInt(visitNum);
		
		//查询该景区的门票信息
		String scenicID = consistOrder.getScenicID();
		ScenicTickets scenicTickets = scenicTicketService.geTicketsByScenicNo(scenicID);
		
		//计算每个订单的应付总额
		int totalMoney = TotalMoney.getTotalMoney(fee, Integer.parseInt(visitNum), Integer.parseInt(purchaseTicket), 
				scenicTickets.getHalfPrice(), scenicTickets.getDiscoutPrice(), scenicTickets.getFullPrice(), 
				Integer.parseInt(halfPrice), Integer.parseInt(discoutPrice), Integer.parseInt(fullPrice));
		
		isConsisted = 1;
		
		boolean bool = consistOrderService.consistWithconsistOrderID(orderID, 
				consistOrderID, scenicID, produceTime, consistOrder.getVisitTime(), 
				Integer.parseInt(visitNum), visitorPhone, totalMoney, currentNum,
				Integer.parseInt(purchaseTicket), orderState, isConsisted, consistOrder.getMaxNum());
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(bool));
		writer.flush();
	}
	
	
}
