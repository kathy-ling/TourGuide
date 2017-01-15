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
import com.google.gson.Gson;

@Controller
@SessionAttributes("adminSession")
@RequestMapping(value="bookorder")
public class BookOrderAction {

	@Autowired
	private BookOrderService bookOrderService;
	
	
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
			list.get(k).put("time", s);
		}

		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
		
		
	}
	
	
}
