package com.TourGuide.Action;

import java.io.IOException;
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
import com.TourGuide.service.ScenicfeeService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="scenicfee")
@SessionAttributes("adminSession")
public class ScenicfeeAction {
	
	@Autowired
	private ScenicfeeService scenicfeeService; 
	
	/**
	 * 分页得到景区收入的详细信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * @throws IOException
	 * 2017-1-15 11:35:58
	 */
	@RequestMapping(value="/GetScenicFee.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetScenicFeeByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list=scenicfeeService.GetscenicFee(currentPage, pageRows);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		int i=scenicfeeService.GetscenicFeeCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	/**
	 * 通过景区编号得到景区收入信息
	 * @param resp
	 * @param scenicID
	 * @return
	 * @throws IOException
	 * 2017-1-15 11:36:27
	 */
	@RequestMapping(value="/GetScenicFeeByscenicID.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetScenicFeeByPageById(HttpServletResponse resp,
			@RequestParam(value="scenicID")String  scenicID ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list=scenicfeeService.GetscenicFeeByID(scenicID);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		return map;
	}
	
	/**
	 * 通过日期来查询景区的收入详情
	 * @param resp
	 * @param date
	 * @param date1
	 * @param scenicID
	 * @return
	 */
	@RequestMapping(value="/GetscenicFeeBydate.action",method=RequestMethod.POST)
	@ResponseBody
	public  Object GetscenicFeeBydate(HttpServletResponse resp,
			@RequestParam(value="date")String date,
			@RequestParam(value="date1")String date1,
			@RequestParam(value="scenicID")String scenicID)
	{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String, Object>> list=scenicfeeService.GetscenicFeeBydate(date, date1, scenicID);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		return map;
		
	}
	
	 

}
