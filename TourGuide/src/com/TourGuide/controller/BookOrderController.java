package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.common.MyDateFormat;
import com.TourGuide.service.BookOrderService;
import com.TourGuide.service.IntroFeeAndMaxNumService;
import com.google.gson.Gson;

@Controller
public class BookOrderController {

	@Autowired
	public BookOrderService bookOrderService;
	
	private final int releaseByVisitor = 1;  //是否是游客自己发布的订单
	private final String orderState = "待接单";  //游客刚发布的订单的状态为“待接单”
	
	
	/**
	 * 将游客自己发布的预约订单存入数据库
	 * @param resp
	 * @param scenicID  游客所预约的景区对应的景区编号
	 * @param visitTime  游客参观的时间
	 * @param visitNum   参观的人数
	 * @param language  讲解员的讲解语言
	 * @param guideSex   讲解员的性别
	 * @param visitorPhone   游客的手机号
	 * @param visitorName   游客的姓名
	 * @param priceRange   游客可接受的价位区间
	 * @param purchaseTicket  是否代购门票
	 * @param otherCommand    其他需求
	 * @throws IOException
	 */
	@RequestMapping(value = "/releaseBookOrder.do")
	public void releaseBookOrder(HttpServletResponse resp,
			@RequestParam("scenicID") String scenicID, 
			@RequestParam("visitTime") String visitTime, 
			@RequestParam("visitNum") String visitNum,
			@RequestParam("language") String language,
			@RequestParam("guideSex") String guideSex,
			@RequestParam("visitorPhone") String visitorPhone,
			@RequestParam("visitorName") String visitorName,
			@RequestParam("priceRange") String priceRange,
			@RequestParam("purchaseTicket") String purchaseTicket,
			@RequestParam("otherCommand") String otherCommand) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		String bookOrderID = UUID.randomUUID().toString().replace("-", "");
		String produceTime = MyDateFormat.form(new Date());
		
		boolean bool = bookOrderService.ReleaseBookOrder(bookOrderID, scenicID, produceTime,
				visitTime, Integer.parseInt(visitNum), language, guideSex, 
				visitorPhone, visitorName, priceRange, Integer.parseInt(purchaseTicket), 
				otherCommand, releaseByVisitor, orderState);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(bool));
		writer.flush();
	}

}
