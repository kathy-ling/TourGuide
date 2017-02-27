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
import com.TourGuide.model.OperateRecord;
import com.TourGuide.service.OperateRecordService;
import com.google.gson.Gson;


@Controller
@SessionAttributes("adminSession")
@RequestMapping(value="OperateRecord")
public class OperateRecordAction {
	
	@Autowired
	private OperateRecordService operateRecordService;
	
	
	/**
	 * 分页得到操作记录信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(value="getOperateRecord.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetOperateRecordBypage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows) {
		
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<OperateRecord> list=operateRecordService.getOperateRecordBypage(currentPage, pageRows);
		int i=operateRecordService.getOperateRecordCount();
		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	/**
	 * 分页得到通过日期的记录信息
	 * @param resp
	 * @param date1
	 * @param date2
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * 2017-2-23 20:47:02
	 */
	@RequestMapping(value="getOperateRecordBydate.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetOperateRecordByDate(HttpServletResponse resp,
			@RequestParam(value="date1")String date1,
			@RequestParam(value="date2")String date2,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows) {
		
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<OperateRecord> list=operateRecordService.getOperateRecordByDate(currentPage, pageRows, date1, date2);
		int i=operateRecordService.getOperateRecordByDateCount(date1,date2);
		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	/**
	 * 分页得到通过景区名称查到的记录信息
	 * @param resp
	 * @param scenic
	 * @param currentPage
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(value="getOperateRecordByscenic.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetOperateRecordByScenic(HttpServletResponse resp,
			@RequestParam(value="scenic")String scenic,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows) {
		
		CommonResp.SetUtf(resp);
		
		Map<String, Object> map=new HashMap<String, Object>();
		List<OperateRecord> list=operateRecordService.getOperateRecordByScenic(currentPage, pageRows, scenic);
		int i=operateRecordService.getOperateRecordByScenicCount(scenic);
		String jsonStr=new Gson().toJson(list).toString();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}

	
}
