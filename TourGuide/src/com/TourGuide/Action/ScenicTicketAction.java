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
import com.TourGuide.model.ScenicTickets;
import com.TourGuide.service.ScenicTeamService;
import com.TourGuide.service.ScenicTicketService;
import com.google.gson.Gson;


@Controller
@SessionAttributes("adminSession")
@RequestMapping(value="scenicTicket")
public class ScenicTicketAction {

	@Autowired
	private ScenicTicketService scenicTicketService;
	
	@Autowired
	private ScenicTeamService scenicTeamService;
	
	/**
	 * 分页得到景区门票价格信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * 2017-2-10 22:15:12
	 */
	@RequestMapping(value="getScenicTicket.action",method=RequestMethod.POST)
	@ResponseBody
	public  Object getscenicTicketBypage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) {
		
		
		
		CommonResp.SetUtf(resp);
		
		
		Map< String , Object> map=new HashMap<>();
		List<Map<String , Object>> list=scenicTicketService.getScenicTicketByPage(currentPage, pageRows);
		
		String jsonStr=new Gson().toJson(list).toString();
		int i=scenicTicketService.getScenicTicketCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	
	/**
	 * 更新景区门票信息
	 * @param resp
	 * @param scenicID
	 * @param half
	 * @param full
	 * @param discount
	 * @return
	 * 2017-2-10 22:15:37
	 */
	@RequestMapping(value="updateScenicTicket.action",method=RequestMethod.POST)
	@ResponseBody
	public Object  UpdatescenicTicket(HttpServletResponse resp,
			@RequestParam(value="scenicID")String scenicID,
			@RequestParam(value="half")int half,
			@RequestParam(value="full")int full,
			@RequestParam(value="discount")int discount
			) {
		
		
		CommonResp.SetUtf(resp);
		
		ScenicTickets scenicTickets=new ScenicTickets();
		scenicTickets.setScenicNo(scenicID);
		scenicTickets.setDiscoutPrice(discount);
		scenicTickets.setFullPrice(full);
		scenicTickets.setHalfPrice(half);
		
 		int i=scenicTicketService.UpdatescenicTicket(scenicTickets);
		
		return i;
	}
	
	/**
	 * 通过景区编号得到景区门票信息
	 * @param resp
	 * @param scenicID
	 * @return
	 * 2017-2-10 22:19:28
	 */
	@RequestMapping(value="getScenicTicketByscenicID.action",produces = "text/html;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public Object getScenicTicketByscenicID(HttpServletResponse resp,
			@RequestParam(value="scenicID")String scenicID,HttpSession session) {
		
		
		CommonResp.SetUtf(resp);
			
		AdminInfo adminInfo=(AdminInfo) session.getAttribute("adminSession");
		if (scenicID.equals("a")) {
			scenicID=scenicTeamService.getScenicNoByAccount(adminInfo.getUsername());
		}
		List<Map<String , Object>> list=scenicTicketService.getTicketByscenicN(scenicID);
		String jsonStr=new Gson().toJson(list).toString();
		
		
		return jsonStr;
	}
}
