package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.Promotion;
import com.TourGuide.service.PromotionService;
import com.google.gson.Gson;

@Controller
public class PromotionController {

	@Autowired
	public PromotionService promotionService;
	
	@RequestMapping(value = "/getPromotions.do")
	public void getOrderInfo(HttpServletResponse resp) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Promotion> list = promotionService.getPromotions();
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(list));
		writer.flush();
	}
}
