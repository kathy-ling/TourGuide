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
import com.TourGuide.service.GuideFeeService;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="guidefee")
@SessionAttributes("adminSession")
public class GuideFeeAction {
	
	@Autowired
	private GuideFeeService guideFeeService;
	
	/**
	 * 分页得到讲解员收入信息表
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * @throws IOException
	 * 
	 * 2017-1-13 21:45:30
	 */
	@RequestMapping(value="/GetGuideFee.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetScenicFeeByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list=guideFeeService.GetGuideFee(currentPage, pageRows);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		int i=guideFeeService.GetGuideFeeCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	
	/**
	 * 通过手机号查询到该讲解员的收入信息
	 * @param resp
	 * @param guideID
	 * @return
	 * @throws IOException
	 * 2017-1-13 21:46:11
	 */
	@RequestMapping(value="/GetGuideFeeByguideID.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetScenicFeeByPageById(HttpServletResponse resp,
			@RequestParam(value="guideID")String  guideID ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list=guideFeeService.GetguideFeeByID(guideID);
		Map<String,Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		return map;
	}

}
