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
import com.TourGuide.model.RecordofReward;
import com.TourGuide.service.RecordofRewardService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("adminSession")
@RequestMapping(value="RecordofReward")
public class RecordofRewardAction {
	
	@Autowired
	private RecordofRewardService recordofRewardService;
	
	/**
	 * 获取讲解员奖惩记录信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * 2017-2-22 14:42:23
	 */
	@RequestMapping(value="GetRecordofReward.action",method = RequestMethod.POST)
	@ResponseBody
	public Object GetRecordofRewardBypage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows) 
	{
		
		CommonResp.SetUtf(resp);
		Map<String, Object> map=new HashMap<String, Object>();
		List<RecordofReward> list=recordofRewardService.GetRecordofReward(currentPage,pageRows);
		String jsonStr=new Gson().toJson(list).toString();
		int i=recordofRewardService.GetRecordofRewardCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		
		return map;
	}

	/**
	 * 通过日期可以获取讲解员奖惩记录信息
	 * @param resp
	 * @param date1
	 * @param date2
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="GetRecordofRewardByDate.action",method = RequestMethod.POST)
	@ResponseBody
	public Object GetRecordofRewardByDate(HttpServletResponse resp,
			@RequestParam(value="date1")String date1,
			@RequestParam(value="date2")String date2,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows)
	{
		CommonResp.SetUtf(resp);
		Map<String, Object> map=new HashMap<String, Object>();
		List<RecordofReward> list=recordofRewardService.GetRecordofRewardByDate(date1, date2,currentPage,pageRows);
		String jsonStr=new Gson().toJson(list).toString();
		int i=recordofRewardService.GetRecordofRewardByDateCount(date1, date2);
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		
		return map;
	} 
	
	/**
	 * 通过手机号来获取讲解员的奖惩信息
	 * @param resp
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="GetRecordofRewardByPhone.action",method = RequestMethod.POST)
	@ResponseBody
	public Object GetRecordofRewardByPhone(HttpServletResponse resp,
			@RequestParam(value="phone")String phone,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows)
	{
		CommonResp.SetUtf(resp);
		Map<String, Object> map=new HashMap<String, Object>();
		List<RecordofReward> list=recordofRewardService.GetRecordofRewardByPhone(phone,currentPage,pageRows);
		String jsonStr=new Gson().toJson(list).toString();
		int i=recordofRewardService.GetRecordofRewardByPhoneCount(phone);
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		
		return map;
	} 
	
}
