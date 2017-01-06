package com.TourGuide.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.GuideInfo;
import com.TourGuide.model.GuideOtherInfo;
import com.TourGuide.service.GuideService;
import com.google.gson.Gson;

/*
 * 讲解员contrller
 * */
@Controller
@RequestMapping(value = "/Guider")
public class GuideAction {
	@Autowired
	private GuideService guideService;
	
	/*
	 * 加载页面时获取讲解员的基本信息及其他信息
	 * */
	@RequestMapping(value="/GetGuider.action",method=RequestMethod.GET)
	@ResponseBody
	public Object GetGuideInfoByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<GuideInfo> list=guideService.getGuidersByPage(currentPage, pageRows);
		List<GuideOtherInfo> listOther = guideService.getGuideOtherInfoByPage_Service(currentPage, pageRows);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		String otherInfo =new Gson().toJson(listOther).toString();
		int i=guideService.GetGuideCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		map.put("otherInfo", otherInfo);
		return map;
	}
	
	/*
	 * 通过讲解员证号查找讲解员基本信息
	 * */
	@RequestMapping(value="/GetGuiderinfoBystring.action", method=RequestMethod.GET)
	@ResponseBody
	public Object GetGuiderinfoBystring(HttpServletResponse resp,
			@RequestParam(value="cID")String cID) throws IOException
	{
		CommonResp.SetUtf(resp);
		List<GuideInfo> list=guideService.GetGuiderinfoBystring(cID);
		String jsonStr=new Gson().toJson(list).toString();
		Map<String, Object> map = new HashMap<>();
		map.put("jsonStr", jsonStr);
		return map;
	}
	/*
	 * 添加讲解员的基本信息
	 * */
	@RequestMapping(value = "/addGuiderInfo.action", method = RequestMethod.POST)
	@ResponseBody
	public boolean addGuiderInfo(HttpServletResponse resp, @RequestParam(value = "phone")String phone,
			@RequestParam(value = "name")String name, @RequestParam(value = "sex")String sex, 
			@RequestParam(value = "cID")String cID, @RequestParam(value = "language")String  language,
			@RequestParam(value = "selfIntro")String selfIntro, @RequestParam(value = "age")int age,
			@RequestParam(value = "workAge")int workAge)
	{
		GuideInfo guideInfo = new GuideInfo();
		guideInfo.setPhone(phone);
		guideInfo.setName(name);
		guideInfo.setSex(sex);
		guideInfo.setCertificateID(cID);
		guideInfo.setLanguage(language);
		guideInfo.setSelfIntro(selfIntro);
		guideInfo.setAge(age);
		guideInfo.setWorkAge(workAge);
		
		CommonResp.SetUtf(resp);
		return guideService.isAdd(guideInfo);
	}
	/*
	 * 删除讲解员的信息
	 * */
	@RequestMapping(value="/DeleteGuideInfo.action")
	@ResponseBody
	public void DeleteGuideInfoById(HttpServletResponse resp,
			@RequestParam(value="id")String id) throws IOException {
		CommonResp.SetUtf(resp);
		guideService.DeleteGuideInfoById_Service(id);
	}
	/*
	 * 编辑讲解员的基本信息
	 * */
	@RequestMapping(value="/EditGuideInfo.action")
	@ResponseBody
	public Object EditGuideInfo(HttpServletResponse resp,@RequestParam(value="phone")String phone,
			@RequestParam(value = "name")String name, @RequestParam(value = "sex")String sex, 
			@RequestParam(value = "cID")String cID, @RequestParam(value = "language")String  language,
			@RequestParam(value = "selfIntro")String selfIntro, @RequestParam(value = "age")int age,
			@RequestParam(value = "workAge")int workAge) throws IOException
	{
		GuideInfo guideInfo = new GuideInfo();
		guideInfo.setPhone(phone);
		guideInfo.setName(name);
		guideInfo.setSex(sex);
		guideInfo.setCertificateID(cID);
		guideInfo.setLanguage(language);
		guideInfo.setSelfIntro(selfIntro);
		guideInfo.setAge(age);
		guideInfo.setWorkAge(workAge);
		CommonResp.SetUtf(resp);
		
		boolean confirm = guideService.EditGuideInfo_Service(guideInfo);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
	/*
	 * 查看讲解员的详细信息即讲解员其他信息
	 * */
	@RequestMapping(value="/LookGuideInfo.action")
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
	@RequestMapping(value="/CheckGuideInfo.action")
	@ResponseBody
	public Object CheckGuideInfo(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", guideService.CheckGuideInfo_Service(phone));
		return map;
	}
	/*
	 * 禁用讲解员
	 * */
	@RequestMapping(value="/ForbidGuideInfo.action")
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
	@RequestMapping(value="/RelieveGuideInfo.action")
	@ResponseBody
	public Object RelieveGuideInfo(HttpServletResponse resp,
			@RequestParam(value="phone")String phone) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", guideService.RelieveGuideInfo_Service(phone));
		return map;
	}
}
