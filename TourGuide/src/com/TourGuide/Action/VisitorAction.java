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
import com.TourGuide.model.VisitorInfo;
import com.TourGuide.service.VisitorService;
import com.google.gson.Gson;
/*
 * 游客的Controller
 * 
 * */
@Controller
@RequestMapping(value="visitor")
@SessionAttributes("adminSession")
public class VisitorAction {
		@Autowired
		private VisitorService visitorService;
		
		
		/*
		 * 通过当前页面与页面容量数目获取游客数目
		 * 参数：当前页，页面容量
		 * 2016-12-30 15:24:51	
	*/
		@RequestMapping(value="/GetVisitorInfo.action",method=RequestMethod.GET)
		@ResponseBody
		public Object GetVisitorInfoByPage(HttpServletResponse resp,
				@RequestParam(value="currentPage")int currentPage,
				@RequestParam(value="pageRows")int pageRows ) throws IOException
		{
			
			CommonResp.SetUtf(resp);
			List<VisitorInfo> list=visitorService.getVisitorInfoByPage(currentPage, pageRows);
			Map< String , Object> map=new HashMap<>();
			String jsonStr=new Gson().toJson(list).toString();
			int i=visitorService.GetVisitorCount();
			map.put("jsonStr", jsonStr);
			map.put("page", currentPage);
			map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
			return map;
		}
		
		/*
		 * 通过sql语句获取游客的信息
		 * 参数：sql语句
		 * 2016-12-31 15:12:07	
	*/
		@RequestMapping(value="/SearchVisitorInfo.action",method=RequestMethod.GET)
		@ResponseBody
		public Object SearchVisitorInfoByName(HttpServletResponse resp,
				@RequestParam(value="sql")String sqlStr) throws IOException {
			CommonResp.SetUtf(resp);
			List<VisitorInfo> list = visitorService.SearchVisitorInfoByPhone_Service(sqlStr);
			String jsonStr = new Gson().toJson(list).toString();
			Map<String , Object> map=new HashMap<>();
			map.put("jsonStr", jsonStr);
			return map;
		}
		
		
		/*
		 *增加游客 
		 * 
		 * */
		@RequestMapping(value="/AddVisitorInfo.action",method=RequestMethod.GET)
		@ResponseBody
		public Object AddVisitorInfo(HttpServletResponse reap,
				@RequestParam(value="name")String name,
				@RequestParam(value="nickName")String nickName,
				@RequestParam(value="sex")String sex,
				@RequestParam(value="city")String city,
				@RequestParam(value="phone")String phone) throws IOException {
			VisitorInfo visitorInfo = new VisitorInfo();
			visitorInfo.setNickName(nickName);
			visitorInfo.setName(name);
			visitorInfo.setSex(sex);
			visitorInfo.setCity(city);
			visitorInfo.setPhone(phone);
			
			boolean confirm = visitorService.AddVisitorInfo_Service(visitorInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		@RequestMapping(value="/DeleteVisitorInfo.action",method=RequestMethod.GET)
		@ResponseBody
		public Object DeleteVisitorInfo(HttpServletResponse reap,
				@RequestParam(value="phone")String s
				) throws IOException {
			
			
			boolean confirm = visitorService.DeleteVisitorInfo_Service(s);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		@RequestMapping(value="/UpdateVisitorInfo.action",method=RequestMethod.GET)
		@ResponseBody
		public Object UpdateVisitorInfo(HttpServletResponse reap,
				@RequestParam(value="name")String name,
				@RequestParam(value="nickName")String nickName,
				@RequestParam(value="sex")String sex,
				@RequestParam(value="phone")String phone,
				@RequestParam(value="city")String city) throws IOException {
			VisitorInfo visitorInfo = new VisitorInfo();
			visitorInfo.setName(name);
			visitorInfo.setNickName(nickName);
			visitorInfo.setSex(sex);
			visitorInfo.setPhone(phone);
			visitorInfo.setCity(city);
			
			boolean confirm = visitorService.UpdateVisitorInfo_Service(visitorInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
	}

