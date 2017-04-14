package com.TourGuide.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.service.GuideSalaryService;

@Controller
public class GuideSalaryController {

	@Autowired
	private GuideSalaryService guideSalaryService;
	
	
	/**
	 * 根据讲解员的手机号，查询讲解员的收入记录
	 * @param resp
	 * @param guidePhone
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getSalaryRecord.do")
	@ResponseBody
	public Object getSalaryRecord(HttpServletResponse resp, 
			@RequestParam("guidePhone") String guidePhone) throws IOException{
	
		CommonResp.SetUtf(resp);
		
		String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		List<Map<String , Object>> list = guideSalaryService.getSalaryRecord(guidePhone);
		
		return list;
	}
	
	
	/**
	 * 统计讲解员接单的总次数和总金额
	 * @param resp
	 * @param guidePhone
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getSalaryAmount.do")
	@ResponseBody
	public Object getSalaryAmount(HttpServletResponse resp, 
			@RequestParam("guidePhone") String guidePhone) throws IOException{
	
		CommonResp.SetUtf(resp);
		
		String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		Map<String , Object> map = guideSalaryService.getSalaryAmount(guidePhone);
		
		return map;
	}
}
