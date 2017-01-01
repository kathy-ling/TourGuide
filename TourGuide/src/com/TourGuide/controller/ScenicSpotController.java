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
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.ScenicsSpotInfo;
import com.TourGuide.service.ScenicSpotService;
import com.google.gson.Gson;

@Controller
public class ScenicSpotController {

	@Autowired
	public ScenicSpotService scenicSpotService;
	
	/**
	 * 根据用户的位置（省份），获取对应省份的热门景点
	 * @param resp
	 * @param province  用户当前所在的省份
	 * @throws IOException
	 */
	@RequestMapping(value = "/getScenicByLocation.do")
	public void getScenicByLocation(HttpServletResponse resp,
			@RequestParam("province") String province) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<ScenicsSpotInfo> list = scenicSpotService.getScenicByLocation(province);
		List<Map<String, Object>> listresult = new ArrayList<Map<String, Object>>();
		
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("scenicImagePath", list.get(i).getScenicImagePath());
			map.put("scenicNo", list.get(i).getScenicNo());
			map.put("openingHours", list.get(i).getOpeningHours());
			map.put("scenicIntro", list.get(i).getScenicIntro());
			map.put("scenicLevel", list.get(i).getScenicLevel());
			map.put("province", list.get(i).getProvince());
			map.put("city", list.get(i).getCity());
			map.put("scenicLocation", list.get(i).getScenicLocation());
			map.put("scenicName", list.get(i).getScenicName());
			map.put("totalVisits", list.get(i).getTotalVisits());
			listresult.add(map);
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listresult));
		writer.flush();
	}
	
	
	
	/**
	 * 根据用户的当前所在的省份，获取该省份的所有景点
	 * @param resp
	 * @param province  用户当前所在的省份
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAllScenicByLocation.do")
	public void getAllScenicByLocation(HttpServletResponse resp,
			@RequestParam("province") String province) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<ScenicsSpotInfo> list = scenicSpotService.getAllScenicByLocation(province);
		List<Map<String, Object>> listresult = new ArrayList<Map<String, Object>>();
		
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("scenicImagePath", list.get(i).getScenicImagePath());
			map.put("scenicNo", list.get(i).getScenicNo());
			map.put("openingHours", list.get(i).getOpeningHours());
			map.put("scenicIntro", list.get(i).getScenicIntro());
			map.put("scenicLevel", list.get(i).getScenicLevel());
			map.put("province", list.get(i).getProvince());
			map.put("city", list.get(i).getCity());
			map.put("scenicLocation", list.get(i).getScenicLocation());
			map.put("scenicName", list.get(i).getScenicName());
			map.put("totalVisits", list.get(i).getTotalVisits());
			listresult.add(map);
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listresult));
		writer.flush();
	}
	
	
	/**
	 * 1、根据景区的名称进行搜索。
	 * 2、根据搜索的特定的景区的地址，进行相关的景区推荐，暂定推荐数为4个
	 * 返回 ：此景区的详细信息，相关的推荐景区的详细信息。
	 * 景区图片、编号、名称、简介、省、市、详细位置、等级、历史参观人数、开放时间
	 * @param resp
	 * @param scenicName  景区的名称
	 * @throws IOException
	 */
	@RequestMapping(value = "/getScenicByNameAndRelates.do")
	public void getScenicByNameAndRelates(HttpServletResponse resp,
			@RequestParam("scenicName") String scenicName) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<ScenicsSpotInfo> list = scenicSpotService.getScenicByNameAndRelates(scenicName);
		List<Map<String, Object>> listresult = new ArrayList<Map<String, Object>>();
		
		for(int i=0; i<list.size(); i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("scenicImagePath", list.get(i).getScenicImagePath());
			map.put("scenicNo", list.get(i).getScenicNo());
			map.put("openingHours", list.get(i).getOpeningHours());
			map.put("scenicIntro", list.get(i).getScenicIntro());
			map.put("scenicLevel", list.get(i).getScenicLevel());
			map.put("province", list.get(i).getProvince());
			map.put("city", list.get(i).getCity());
			map.put("scenicLocation", list.get(i).getScenicLocation());
			map.put("scenicName", list.get(i).getScenicName());
			map.put("totalVisits", list.get(i).getTotalVisits());
			listresult.add(map);
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listresult));
		writer.flush();
	}
	
}
