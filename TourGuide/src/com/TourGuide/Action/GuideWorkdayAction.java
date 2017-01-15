package com.TourGuide.Action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.Guideworkday;
import com.TourGuide.service.GuideworkdayService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="guideworkday")
@SessionAttributes("adminSession")
public class GuideWorkdayAction {

	@Autowired
	private GuideworkdayService guideworkdayService;
	
	/**
	 *	得到讲解员工作日安排的信息 
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(value="GetGuideWorkDay.action",method = RequestMethod.POST)
	@ResponseBody
	public Object GetGuideWorkDay(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows)
	{
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String , Object>> list=guideworkdayService.GetGuideworkday(currentPage, pageRows);
		String jsonStr=new Gson().toJson(list).toString();
		int i=guideworkdayService.getguideWorkdayCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		
		return map;
	}
	
	/**
	 * 
	 * @param resp
	 * @param day1	 今天
	 * @param day2	明天
	 * @param day3	后天
	 * @param day4	大后天
	 * @return
	 */
	@RequestMapping(value="EditGuideWorkdayInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object EditGuideWorkdayInfo(HttpServletResponse resp,
			@RequestParam(value="day1")int day1,@RequestParam(value="day2")int day2,
			@RequestParam(value="day3")int day3,@RequestParam(value="day4")int day4,
			@RequestParam(value="phone")String phone)
	{
		
		CommonResp.SetUtf(resp);
		
		Guideworkday guideworkday=new Guideworkday();
		guideworkday.setDay1(day1);
		guideworkday.setDay2(day2);
		guideworkday.setDay3(day3);
		guideworkday.setDay4(day4);
		guideworkday.setPhone(phone);
		return  guideworkdayService.UpdateGuideWorkday(guideworkday);
	}
	
	/**
	 * 通过手机号查询该讲解员的详细安排时间
	 * @param resp
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="QueryGuideWorkdayByPhone.action",method = RequestMethod.POST)
	@ResponseBody
	public Object QueryGuideWorkdayByPhone(HttpServletResponse resp,
			@RequestParam(value="phone")String phone)
	{
		
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list= guideworkdayService.QueryGuideworkdayByPhone(phone);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("jsonStr", new Gson().toJson(list).toString());
		return map;
		
	}
}
