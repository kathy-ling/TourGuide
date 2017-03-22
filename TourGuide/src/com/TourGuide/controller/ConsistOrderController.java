package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.common.DateConvert;
import com.TourGuide.common.MyDateFormat;
import com.TourGuide.common.TotalMoney;
import com.TourGuide.model.ConsistOrder;
import com.TourGuide.model.IntroFeeAndMaxNum;
import com.TourGuide.model.ScenicTickets;
import com.TourGuide.service.ConsistOrderService;
import com.TourGuide.service.IntroFeeAndMaxNumService;
import com.TourGuide.service.ScenicSpotService;
import com.TourGuide.service.ScenicTicketService;
import com.google.gson.Gson;

@Controller
public class ConsistOrderController {
	
	@Autowired
	IntroFeeAndMaxNumService introFeeAndMaxNumService;
	
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	@Autowired
	ConsistOrderService consistOrderService;
	
	@Autowired
	ScenicTicketService scenicTicketService;
	
	private final String orderState = "待接单";  //游客刚发布的拼单的状态为“待接单”
	
	private int isConsisted = 0;  //游客刚发布的拼单，没有被拼单
	
	
	/**
	 * 查询某天该景区的拼单讲解费
	 * @param resp
	 * @param scenicID  景区编号
	 * @param date   日期
	 * @throws IOException
	 */
	@RequestMapping(value = "/getIntroFee.do")
	public void getIntroFee(HttpServletResponse resp,
			@RequestParam("scenicName") String scenicName, 
			@RequestParam("date") String date) throws IOException{
	
		CommonResp.SetUtf(resp);
		
		List<Map<String, Object>> scenicSpotInfo = scenicSpotService.getScenicByName(scenicName);
		String scenicID = (String) scenicSpotInfo.get(0).get("scenicNo");
		
		//讲解费,？元/人		
		IntroFeeAndMaxNum introFeeAndMaxNum = 
				introFeeAndMaxNumService.getIntroFeeAndMaxNum(date,scenicID);
		int fee = introFeeAndMaxNum.getFee();
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(fee));
		writer.flush();
	}
	
	
	
	
	/**
	 * 将游客自己发布的拼单订单存入数据库
	 * @param resp
	 * @param scenicName  景区名称
	 * @param visitTime  游客参观的时间
	 * @param visitNum    参观的人数
	 * @param visitorPhone   游客的手机号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/releaseConsistOrder.do")
	@ResponseBody
	public Object releaseConsistOrder(HttpServletResponse resp,
			@RequestParam("scenicName") String scenicName, 
			@RequestParam("visitTime") String visitTime, 
			@RequestParam("visitNum") String visitNum,
			@RequestParam("visitorPhone") String visitorPhone
//			@RequestParam("purchaseTicket") String purchaseTicket,
//			@RequestParam("halfPrice") String halfPrice,
//			@RequestParam("discoutPrice") String discoutPrice,
//			@RequestParam("fullPrice") String fullPrice
			) throws IOException{
		
		//releaseConsistOrder.do?scenicName=秦始皇兵马俑&visitTime=2017-03-22 15:00&visitNum=10&visitorPhone=18191762572
		
		CommonResp.SetUtf(resp);
		
		String consistOrderID = UUID.randomUUID().toString().replace("-", "");
		String orderID = UUID.randomUUID().toString().replace("-", "");
		String produceTime = MyDateFormat.form(new Date());
		
//		//查询该景区的门票信息
//		ScenicTickets scenicTickets = scenicTicketService.geTicketsByScenicNo(scenicID);
		
		List<Map<String, Object>> scenicSpotInfo = scenicSpotService.getScenicByName(scenicName);
		String scenicID = (String) scenicSpotInfo.get(0).get("scenicNo");
		
		//查询该景区该日的讲解费、最大可拼单人数
		String[] date = visitTime.toString().split(" ");
		IntroFeeAndMaxNum introFeeAndMaxNum = 
				introFeeAndMaxNumService.getIntroFeeAndMaxNum(date[0], scenicID);
		
		int fee = introFeeAndMaxNum.getFee();
		int totalFee = Integer.parseInt(visitNum) * fee  ;
		
//		int maxNum = introFeeService.getMaxNum(date[0], scenicID);
//		int fee = introFeeService.getIntroFee(date[0], scenicID);
		
		//订单相关的计算
//		List<Integer> money = TotalMoney.getMoney(fee, Integer.parseInt(visitNum), Integer.parseInt(purchaseTicket), 
//				scenicTickets.getHalfPrice(), scenicTickets.getDiscoutPrice(), scenicTickets.getFullPrice(), 
//				Integer.parseInt(halfPrice), Integer.parseInt(discoutPrice), Integer.parseInt(fullPrice));
		
//		int totalFee = money.get(0);  //讲解费总额
//		int totalTicket = money.get(1);  //门票费总额
//		int totalMoney = money.get(2);  //讲解费总额 + 门票费总额
		
		boolean bool = consistOrderService.ReleaseConsistOrder(consistOrderID, orderID, 
				scenicID, produceTime, visitTime, Integer.parseInt(visitNum), visitorPhone,
				orderState, isConsisted, introFeeAndMaxNum.getMaxNum(), totalFee, fee);
		
		return bool;
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
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(list));
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
//	@RequestMapping(value = "/consistWithconsistOrderID.do")
//	@ResponseBody
//	public Object consistWithconsistOrderID(HttpServletResponse resp,
//			@RequestParam("orderID") String orderID,  
//			@RequestParam("visitNum") String visitNum,
//			@RequestParam("visitorPhone") String visitorPhone,
//			@RequestParam("purchaseTicket") String purchaseTicket,
//			@RequestParam("halfPrice") String halfPrice,
//			@RequestParam("discoutPrice") String discoutPrice,
//			@RequestParam("fullPrice") String fullPrice) throws IOException{
//		
//		CommonResp.SetUtf(resp);
//		
//		String consistOrderID = UUID.randomUUID().toString().replace("-", "");
//		String produceTime = MyDateFormat.form(new Date());
//		
//		//查询该拼单结果的详细信息
//		List<Map<String , Object>> consistResult = consistOrderService.getDetailConsistResult(orderID);
//		int currentNum = (int)consistResult.get(0).get("visitNum");
//		int maxNum = (int)consistResult.get(0).get("maxNum");
//		String scenicID = (String)consistResult.get(0).get("scenicID");
//		Timestamp timestamp = (Timestamp)consistResult.get(0).get("visitTime");
//		String visitTime = DateConvert.timeStamp2DateTime(timestamp);
//		
//		currentNum = currentNum + Integer.parseInt(visitNum);
//		
//		String[] date = visitTime.toString().split(" ");
//		int fee = introFeeService.getIntroFee(date[0], scenicID);
//		
//		//查询该景区的门票信息
//		ScenicTickets scenicTickets = scenicTicketService.geTicketsByScenicNo(scenicID);
//		
//		//计算每个订单的应付总额
//		List<Integer> money= TotalMoney.getMoney(fee, Integer.parseInt(visitNum), Integer.parseInt(purchaseTicket), 
//				scenicTickets.getHalfPrice(), scenicTickets.getDiscoutPrice(), scenicTickets.getFullPrice(), 
//				Integer.parseInt(halfPrice), Integer.parseInt(discoutPrice), Integer.parseInt(fullPrice));
//		int totalFee = money.get(0);  //讲解费总额
//		int totalTicket = money.get(1);  //门票费总额
//		int totalMoney = money.get(2);  //讲解费总额 + 门票费总额
//		
//		isConsisted = 1;
//		
//		boolean bool = consistOrderService.consistWithconsistOrderID(orderID, 
//				consistOrderID, scenicID, produceTime, visitTime, 
//				Integer.parseInt(visitNum), visitorPhone, totalMoney, currentNum,
//				Integer.parseInt(purchaseTicket), orderState, isConsisted, maxNum,
//				Integer.parseInt(halfPrice), Integer.parseInt(discoutPrice), Integer.parseInt(fullPrice),
//				totalFee, totalTicket, fee);
//		
//		return bool;
//	}
	
	
}
