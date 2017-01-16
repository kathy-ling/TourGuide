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
import com.TourGuide.service.BookOrderService;
import com.TourGuide.service.GuideService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("adminSession")
@RequestMapping(value="bookorder")
public class BookOrderAction {

	@Autowired
	private BookOrderService bookOrderService;
	@Autowired
	private GuideService guideService;
	
	/**
	 * 分页得到订单的所有信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * 2017-1-16 15:21:42
	 */
	@RequestMapping(value="getBookorderInfo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetBookorderBypage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows) {
		
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String , Object>> list=bookOrderService.GetBookorderBypage(currentPage, pageRows);
		int i=bookOrderService.GetBookorderCount();
		for (int k = 0; k < list.size(); k++) {
			String s=list.get(k).get("visitTime").toString();
			String guidePhone1;
			String guideName;
			Object phone=list.get(k).get("guidePhone");
			if (phone==null) {
				guidePhone1="等待讲解员接单";
				guideName="等待讲解员接单";
			}else {
				guidePhone1=phone.toString();
				guideName=guideService.GetGuiderinfoByPhone(guidePhone1).get(0).get("name").toString();
			}
			list.get(k).put("time", s);
			list.get(k).put("guidePhone1", guidePhone1);
			list.get(k).put("guideName", guideName);
		}

		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
		
		
	}
	
	
	/**
	 * 通过字段与只得到自己想要的所有订单信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @param word
	 * @param value
	 * @return
	 * 2017-1-16 15:22:17
	 */
	@RequestMapping(value="getBookorderBySearch.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetBookorderBySearch(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows,
			@RequestParam(value="word")String  word,
			@RequestParam(value="value")String  value) {
		
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<Map<String , Object>> list=bookOrderService.GetBookorderBySearch(currentPage, pageRows, word, value);;
		int i=bookOrderService.GetBookorderBySearchCount(word, value);
		for (int k = 0; k < list.size(); k++) {
			String s=list.get(k).get("visitTime").toString();
			String guidePhone1;
			String guideName;
			Object phone=list.get(k).get("guidePhone");
			if (phone==null) {
				guidePhone1="等待讲解员接单";
				guideName="等待讲解员接单";
			}else {
				guidePhone1=phone.toString();
				guideName=guideService.GetGuiderinfoByPhone(guidePhone1).get(0).get("name").toString();
			}
			list.get(k).put("time", s);
			list.get(k).put("guidePhone1", guidePhone1);
			list.get(k).put("guideName", guideName);
		}

		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
		
		
	}
	
	
}
