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
import com.TourGuide.model.ScenicsSpotInfo;
import com.TourGuide.service.ScenicSpotService;
import com.google.gson.Gson;
/*
 * 景区信息的Controller
 * 
 * */
@Controller
@RequestMapping(value="scenic")
@SessionAttributes("adminSession")
public class ScenicSpotAction {

		@Autowired
		private ScenicSpotService scenicSpotService;
		 
		/*
		 * 通过当前页面与页面容量数目获取景区信息数目
		 * 参数：当前页，页面容量
		 * 2017-1-2 10:12:30	
	*/
		@RequestMapping(value="/GetScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object GetScenicInfoByPage(HttpServletResponse resp,
				@RequestParam(value="currentPage")int currentPage,
				@RequestParam(value="pageRows")int pageRows ) throws IOException
		{
			
			CommonResp.SetUtf(resp);
			List<ScenicsSpotInfo> list=scenicSpotService.getScenicInfoByPage(currentPage, pageRows);
			Map< String , Object> map=new HashMap<>();
			String jsonStr=new Gson().toJson(list).toString();
			int i=scenicSpotService.GetScenicCount();
			map.put("jsonStr", jsonStr);
			map.put("page", currentPage);
			map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
			return map;
		}
		
		/*
		 * 通过sql语句获取景区的信息
		 * 参数：sql语句
		 * 2017-1-2 10:15:30	
	*/
		@RequestMapping(value="/SearchScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object SearchScenicInfoByName(HttpServletResponse resp,
				@RequestParam(value="sql")String sqlStr) throws IOException {
			CommonResp.SetUtf(resp);
			ScenicsSpotInfo list = scenicSpotService.SearchScenicInfoByName_Service(sqlStr);
			String jsonStr = new Gson().toJson(list).toString();
			Map<String , Object> map=new HashMap<>();
			map.put("jsonStr", jsonStr);
			return map;
		}
		
		
		/**
		 * 通过省份，城市，详细地址进行搜索景区
		 * @param provin
		 * @param city
		 * @param s
		 * @param resp
		 * @return 景区list
		 * 2017-1-7 19:21:56
		 */
		@RequestMapping(value="SearchScenicInfoByloc.action",method=RequestMethod.POST)
		@ResponseBody
		public Object SearchScenicInfoByLocation(@RequestParam(value="pro")String provin,
				@RequestParam(value="city")String city,
				@RequestParam(value="s")String s,
				HttpServletResponse resp)
		{
			CommonResp.SetUtf(resp);
			
			String jsonStr;
			ScenicsSpotInfo list=scenicSpotService.SearchSceincInfoByLocation(provin, city, s);
			if(list.getScenicNo()!=null){
				jsonStr=new Gson().toJson(list).toString();
			}else
			{
				jsonStr="";
			}
			Map< String, Object> map=new HashMap<>();
			map.put("str", jsonStr);
			return map;
		}
		/*
		 *增加景区信息
		 * 2017-1-2 19:48:11
		 * */
		@RequestMapping(value="/AddScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object AddScenicInfo(HttpServletResponse reap,
				@RequestParam(value="scenicNo")String scenicNo,
				@RequestParam(value="scenicName")String scenicName,
				@RequestParam(value="totalVisits")String totalVisits,
				@RequestParam(value="openingHours")String openingHours,
				@RequestParam(value="scenicLevel")String scenicLevel,
				@RequestParam(value="scenicLocation")String scenicLocation,
				@RequestParam(value="isHotSpot")int isHotSpot,
				@RequestParam(value="province")String province,
				@RequestParam(value="city")String city,
				@RequestParam(value="chargePerson")String chargePerson,
				@RequestParam(value="scenicIntro")String scenicIntro) throws IOException {
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicNo(scenicNo);
			scenicsSpotInfo.setScenicName(scenicName);
			scenicsSpotInfo.setTotalVisits(totalVisits);
			scenicsSpotInfo.setOpeningHours(openingHours);
			scenicsSpotInfo.setScenicLevel(scenicLevel);
			scenicsSpotInfo.setScenicIntro(scenicIntro);
			scenicsSpotInfo.setScenicLocation(scenicLocation);
			scenicsSpotInfo.setProvince(province);
			scenicsSpotInfo.setCity(city);
			scenicsSpotInfo.setChargePerson(chargePerson);
			scenicsSpotInfo.setIsHotSpot(isHotSpot);
			
			boolean confirm = scenicSpotService.AddScenicInfo_Service(scenicsSpotInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		/*
		 * 通过景区名称进行删除景区信息
		 * 参数：景区名称
		 * 2017-1-2 19:49:04
		 * */
		@RequestMapping(value="/DeleteScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object DeleteScenicInfo(HttpServletResponse reap,
				@RequestParam(value="scenicName")String s
				) throws IOException {
			
			
			boolean confirm = scenicSpotService.DeleteScenicInfo_Service(s);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		/*
		 * 更新景区信息
		 * 2017-1-2 19:49:32
		 * */
		@RequestMapping(value="/UpdateScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object UpdateScenicInfo(HttpServletResponse reap,
				@RequestParam(value="scenicNo")String scenicNo,
				@RequestParam(value="scenicName")String scenicName,
				@RequestParam(value="totalVisits")String totalVisits,
				@RequestParam(value="openingHours")String openingHours,
				@RequestParam(value="scenicLevel")String scenicLevel,
				@RequestParam(value="scenicLocation")String scenicLocation,
				@RequestParam(value="isHotSpot")int isHotSpot,
				@RequestParam(value="province")String province,
				@RequestParam(value="city")String city,
				@RequestParam(value="chargePerson")String chargePerson,
				@RequestParam(value="scenicIntro")String scenicIntro) throws IOException {
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicNo(scenicNo);
			scenicsSpotInfo.setScenicName(scenicName);
			scenicsSpotInfo.setTotalVisits(totalVisits);
			scenicsSpotInfo.setOpeningHours(openingHours);
			scenicsSpotInfo.setScenicLevel(scenicLevel);
			scenicsSpotInfo.setScenicIntro(scenicIntro);
			scenicsSpotInfo.setScenicLocation(scenicLocation);
			scenicsSpotInfo.setProvince(province);
			scenicsSpotInfo.setCity(city);
			scenicsSpotInfo.setChargePerson(chargePerson);
			scenicsSpotInfo.setIsHotSpot(isHotSpot);
			boolean confirm = scenicSpotService.UpdateScenicInfo_Service(scenicsSpotInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
	}
