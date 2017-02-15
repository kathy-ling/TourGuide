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
		 * 加载页面时获取可以使用游客的基本信息及其他信息
		 * */
		@RequestMapping(value="/GetVisitorInfo.action",method=RequestMethod.POST)
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
		 * 通过手机号获取未禁用游客的信息
		 * 参数：手机号
		 * 2016-12-31 15:12:07	
		 */
		@RequestMapping(value="/SearchVisitorInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object SearchVisitorInfoByPhone(HttpServletResponse resp,
				@RequestParam(value="phone")String phone) throws IOException {
			CommonResp.SetUtf(resp);
			List<VisitorInfo> list = visitorService.SearchVisitorInfoByPhone_Service(phone);
			String jsonStr = new Gson().toJson(list).toString();
			System.out.println(jsonStr);
			return jsonStr;
		}
		
		
		
		
		
		/*
		 *	将游客移进黑名单
		 * */
		@RequestMapping(value="/ForbidVisitorInfo.action")
		@ResponseBody
		public Object ForbidVisitorInfo(HttpServletResponse resp,
				@RequestParam(value="phone")String phone) throws IOException {
			CommonResp.SetUtf(resp);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", visitorService.ForbidVisitorInfo_Service(phone));
			return map;
		}
		
		/**
		 * 获取黑名单的游客信息
		 * @param resp
		 * @param currentPage
		 * @param pageRows
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/GetVisitorDisabled.action",method=RequestMethod.POST)
		@ResponseBody
		public Object GetVisitorDisabledByPage(HttpServletResponse resp,
				@RequestParam(value="currentPage")int currentPage,
				@RequestParam(value="pageRows")int pageRows ) throws IOException
		{
			
			CommonResp.SetUtf(resp);
			List<VisitorInfo> list=visitorService.GetVisitorInfoDisabled(currentPage, pageRows);
			
			Map< String , Object> map=new HashMap<>();
			String jsonStr=new Gson().toJson(list).toString();
			int i=visitorService.GetVisitorDisabledCount();
			map.put("jsonStr", jsonStr);
			map.put("page", currentPage);
			map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
			return map;
		}
		
		/*
		 * 通过手机号获取黑名单游客的信息
		 * 参数：手机号
		 * 2016-12-31 15:12:07	
		 */
		@RequestMapping(value="/SearchVisitorDisabled.action",method=RequestMethod.POST)
		@ResponseBody
		public Object SearchVisitorDisabledByPhone(HttpServletResponse resp,
				@RequestParam(value="phone")String phone) throws IOException {
			CommonResp.SetUtf(resp);
			List<VisitorInfo> list = visitorService.SearchVisitorDisByPhone(phone);
			String jsonStr = new Gson().toJson(list).toString();
			System.out.println(jsonStr);
			return jsonStr;
		}
		
		
		
		
		
		
		/*
		 * 将游客移出黑名单
		 * */
		@RequestMapping(value="/RelieveVisitorInfo.action")
		@ResponseBody
		public Object RelieveVisitorInfo(HttpServletResponse resp,
				@RequestParam(value="phone")String phone) throws IOException {
			CommonResp.SetUtf(resp);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", visitorService.RelieveVisitorInfo_Service(phone));
			return map;
		}
}

