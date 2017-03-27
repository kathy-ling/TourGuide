package com.TourGuide.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.TourGuide.common.MyDateFormat;
import com.TourGuide.model.IntroFeeAndMaxNum;
import com.TourGuide.service.FastOrderService;
import com.TourGuide.service.IntroFeeAndMaxNumService;
import com.TourGuide.service.OrderService;
import com.TourGuide.service.ScenicSpotService;

@Controller
public class FastOrderController {
	
	@Autowired
	private FastOrderService fastOrderService;
	
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	@Autowired
	IntroFeeAndMaxNumService introFeeAndMaxNumService;
	
	@Autowired
	OrderService orderService;
	
	
	/**
	 * 游客发起快捷拼团
	 * @param resp
	 * @param scenicName 景区名称
	 * @param visitNum 参观人数
	 * @param guideFee  景区该天的讲解费
	 * @param visitorPhone 游客的手机号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/releaseFastOrder.do")
	@ResponseBody
	public Object releaseFastOrder(HttpServletResponse resp,
			@RequestParam("scenicName") String scenicName, 			 
			@RequestParam("visitNum") String visitNum,
			@RequestParam("guideFee") String guideFee,
			@RequestParam("visitorPhone") String visitorPhone) throws IOException{
		//scenicName=秦始皇兵马俑&visitNum=3&guideFee=30&visitorPhone=18191762572
		
		CommonResp.SetUtf(resp);
		
		String consistOrderID = UUID.randomUUID().toString().replace("-", "");
		
		List<Map<String, Object>> scenicSpotInfo = scenicSpotService.getScenicByName(scenicName);
		String scenicID = (String) scenicSpotInfo.get(0).get("scenicNo");
		
		boolean bool = fastOrderService.releaseFastOrder(consistOrderID, scenicID, 
				Integer.parseInt(visitNum), Integer.parseInt(guideFee), visitorPhone);
		
		return bool;
	}
	
	
	/**
	 * 导游接单前的确认操作
	 * @param guidePhone
	 * @return 返回此次接单的订单号、单人讲解费、最大接单人数
	 */
	@RequestMapping(value = "/confirmBeforTake.do")
	@ResponseBody
	public Object confirmBeforTake(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone) throws IOException{
	//orderID=59cc89e711604fcb8c4efb1bcf22002e&guidePhone=13823456789
		
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map = fastOrderService.confirmBeforTake(guidePhone);
		
		return map;
	}
	
	
	/**
	 * 讲解员接单（快捷拼单）
	 * @param resp
	 * @param consistOrderID 游客的订单编号
	 * @param orderID  讲解员的订单编号
	 * @param guidePhone   讲解员手机号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/takeFastOrder.do")
	@ResponseBody
	public Object takeFastOrder(HttpServletResponse resp,
			@RequestParam("consistOrderID") String consistOrderID,
			@RequestParam("orderID") String orderID,
			@RequestParam("guidePhone") String guidePhone) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String, Object>> listResult = orderService.getDetailOrderInfo(consistOrderID);
		int visitNum = (int) listResult.get(0).get("visitNum");
		
		boolean bool = fastOrderService.takeFastOrder(consistOrderID, orderID, guidePhone, visitNum);
		
		return bool;
	}

}
