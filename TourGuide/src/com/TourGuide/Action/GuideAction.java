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

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.GuideOtherInfo;
import com.TourGuide.service.GuideService;
import com.google.gson.Gson;

/*
 * 讲解员contrller
 * */
@Controller
@RequestMapping(value = "/Guide")
public class GuideAction {
	@Autowired
	private GuideService guideService;
	
	/*
	 * 加载页面时获取已审核讲解员的基本信息及其他信息
	 * */
	@RequestMapping(value="/GetGuiderofYes.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetGuideofYesByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		Map< String , Object> map=new HashMap<>();
		List<Map<String , Object>> list=guideService.GetGuideofYes(currentPage, pageRows);
		String jsonStr=new Gson().toJson(list).toString();
		int i=guideService.GetGuideofYesCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	/*
	 * 加载页面时获取未审核讲解员的基本信息及其他信息
	 * */
	@RequestMapping(value="/GetGuiderofNo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetGuideofNoByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		Map< String , Object> map=new HashMap<>();
		List<Map<String , Object>> list=guideService.GetGuideofNo(currentPage, pageRows);
		String jsonStr=new Gson().toJson(list).toString();
		int i=guideService.GetGuideofNoCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	
	
	/*
	 * 通过讲解员证号查找讲解员基本信息
	 * */
	@RequestMapping(value="/GetGuiderinfoBystring.action", method=RequestMethod.POST)
	@ResponseBody
	public Object GetGuiderinfoBystring(HttpServletResponse resp,
			@RequestParam(value="cID")String cID) throws IOException
	{
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list=guideService.GetGuiderinfoBystring(cID);
		String jsonStr=new Gson().toJson(list).toString();
		Map<String, Object> map = new HashMap<>();
		map.put("jsonStr", jsonStr);
		return map;
	}
	
	/*
	 * 通过讲解员证号查找讲解员基本信息
	 * */
	@RequestMapping(value="/GetGuiderinfoByPhone.action", method=RequestMethod.POST)
	@ResponseBody
	public Object GetGuiderinfoByPhone(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException
	{
		CommonResp.SetUtf(resp);
		List<Map<String, Object>> list=guideService.GetGuiderinfoByPhone(phone);
		String jsonStr=new Gson().toJson(list).toString();
		Map<String, Object> map = new HashMap<>();
		map.put("jsonStr", jsonStr);
		return map;
	}
	
	/*
	 * 删除讲解员的信息
	 * */
	@RequestMapping(value="/DeleteGuideInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object DeleteGuideInfoById(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException {
		CommonResp.SetUtf(resp);
		int i=guideService.DeleteGuideInfoById_Service(phone);
		return i;
	}
	/*
	 * 编辑讲解员的基本信息
	 * */
	@RequestMapping(value="/EditGuideInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object EditGuideInfo(HttpServletResponse resp,@RequestParam(value="level")String level,
			@RequestParam(value = "historyNum")String historyNum, @RequestParam(value = "guideNum")String guideNum, 
			@RequestParam(value = "fee")String fee,@RequestParam(value = "phone")String phone) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		
		boolean confirm = guideService.EditGuideInfo_Service(level, historyNum, guideNum, fee, phone);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
	/*
	 * 查看讲解员的详细信息即讲解员其他信息
	 * */
	@RequestMapping(value="/LookGuideInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object LookGuideInfo(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException {
		CommonResp.SetUtf(resp);
		List<GuideOtherInfo> list = guideService.LookGuideInfo_Service(phone);
		String jsonStr = new Gson().toJson(list).toString();
		Map<String, Object> map = new HashMap<>();
		map.put("jsonStr", jsonStr);
		return map;
	}
	/*
	 * 审核讲解员
	 * */
	@RequestMapping(value="/CheckGuideInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object CheckGuideInfo(HttpServletResponse resp,
			@RequestParam(value="phone")String phone,
			@RequestParam(value="historyNum")int historyNum,
			@RequestParam(value="singleMax")int singleMax,
			@RequestParam(value="guideFee")int guideFee,
			@RequestParam(value="guideLevel")String guideLevel,
			@RequestParam(value="scenicBelong")String scenicBelong,
			@RequestParam(value="workAge")int workAge,
			@RequestParam(value="certificateID")String certificateID) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", guideService.CheckGuideInfo_Dao(phone, historyNum, singleMax, guideFee, guideLevel, scenicBelong, workAge, certificateID));
		return map;
	}
	/*
	 * 禁用讲解员
	 * */
	@RequestMapping(value="/ForbidGuideInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object ForbidGuideInfo(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", guideService.ForbidGuideInfo_Service(phone));
		return map;
	}
	/*
	 * 解禁讲解员
	 * */
	@RequestMapping(value="/RelieveGuideInfo.action",method = RequestMethod.POST)
	@ResponseBody
	public Object RelieveGuideInfo(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", guideService.RelieveGuideInfo_Service(phone));
		return map;
	}
	
	
	
}
