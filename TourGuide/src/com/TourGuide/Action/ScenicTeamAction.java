package com.TourGuide.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.AdminInfo;
import com.TourGuide.model.ScenicTeam;
import com.TourGuide.service.ScenicTeamService;
import com.google.gson.Gson;

@Controller
@SessionAttributes(value="adminSession")
@RequestMapping(value="scenicTeam")
public class ScenicTeamAction {

	@Autowired
	private ScenicTeamService scenicTeamService;
	
	@RequestMapping(value="getscenicTeam.action",method=RequestMethod.POST)
	@ResponseBody
	public  Object getscenicTeamBypage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) {
		
		CommonResp.SetUtf(resp);
		Map< String , Object> map=new HashMap<>();
		List<ScenicTeam> list=scenicTeamService.getScenicTeam(currentPage, pageRows);
		
		String jsonStr=new Gson().toJson(list).toString();
		int i=scenicTeamService.getScenicTeamCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	/**
	 * 通过景区编号查询进去拼团信息
	 * @param resp
	 * @param scenicNo
	 * @return
	 */
	@RequestMapping(value="getscenicTeamByscenicNo.action",produces = "text/html;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public  Object getscenicTeamByscenicNo(HttpServletResponse resp,
			@RequestParam(value="scenicNo")String scenicNo,HttpSession session ) {
		
		
		AdminInfo adminInfo=(AdminInfo) session.getAttribute("adminSession");
		if (scenicNo.equals("a")) {
			scenicNo=scenicTeamService.getScenicNoByAccount(adminInfo.getUsername());
		}
		
		CommonResp.SetUtf(resp);
		List<ScenicTeam> list=scenicTeamService.getScenicTeamByscenicNo(scenicNo);
		
		String jsonStr=new Gson().toJson(list).toString();
		
		return jsonStr;
	}
	
	
	
	
	/**
	 * 更新景区拼团信息
	 * @param scenicNo
	 * @param fee
	 * @param maxNum
	 * @param date
	 * @return
	 */
	@RequestMapping(value="UpdateScenicTeam.action",method=RequestMethod.POST)
	@ResponseBody
	public Object UpdateScenicTeam(@RequestParam(value="scenicNo")String scenicNo,
			@RequestParam(value="fee")int fee,
			@RequestParam(value="maxNum")int maxNum,
			@RequestParam(value="date")String date)
	{
		return scenicTeamService.UpdateScenicTeam(scenicNo, fee, maxNum,date);
		
	}
	
	
}
