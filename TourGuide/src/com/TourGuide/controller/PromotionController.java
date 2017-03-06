package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.Promotion;
import com.TourGuide.service.PromotionService;
import com.TourGuide.service.VisitorService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("visitorSession")
public class PromotionController {

	@Autowired
	public PromotionService promotionService;
	
	@Autowired
	private VisitorService visitorService;
	
	/**
	 * 获取首页的活动信息，暂定返回五个
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getPromotions.do")
	public void getPromotions(HttpServletResponse resp) throws IOException{
		
		CommonResp.SetUtf(resp);
		

		List<Promotion> list = promotionService.getPromotions();
		
		List<Map<String, Object>> listresult = new ArrayList<Map<String, Object>>();
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("promotionImage", list.get(i).getPromotionImage());
			map.put("promotionLinks", list.get(i).getPromotionLinks());
			listresult.add(map);
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listresult));
		writer.flush();		
	}
}
