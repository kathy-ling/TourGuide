package com.TourGuide.Action;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.Operateper;
import com.TourGuide.service.OperateperService;
import com.google.gson.Gson;

/*
 * 运营人员的Controller
 * 
 * */
@Controller
@RequestMapping(value="OperateUser")
public class OperateAction {

	@Autowired
	private OperateperService operateperService;
	
	
	/*
	 * 通过当前页面与页面容量数目获取运营人员数目
	 * 参数：当前页，页面容量
	 * 2016-12-30 15:24:51	
*/
	@RequestMapping(value="GetOperateUser.action",method=RequestMethod.GET)
	@ResponseBody
	public Object GetOperateInfoByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<Operateper> list=operateperService.getOperatepersByPage(currentPage, pageRows);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		int i=operateperService.GetOperateCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
}
