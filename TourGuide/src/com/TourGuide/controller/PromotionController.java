package com.TourGuide.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.service.PromotionService;

@Controller
public class PromotionController {

	@Autowired
	public PromotionService promotionService;
	
	@RequestMapping(value = "/getPromotions.do")
	public void getOrderInfo(HttpServletResponse resp) throws IOException{
		
		CommonResp.SetUtf(resp);
		
	}
}
