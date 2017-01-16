package com.TourGuide.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.service.AffiliationService;

@Controller
public class AffiliationController {

	@Autowired
	private AffiliationService affiliationService;
	
	
	/**
	 * 导游选中景区后，申请挂靠
	 * @param resp
	 * @param guidePhone  导游手机号
	 * @param scenicID  景区编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/applyForAffiliation.do")
	@ResponseBody
	public Object applyForAffiliation(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone, 
			@RequestParam("scenicID") String scenicID) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String applyDate = dateFormat.format(new Date());
		
		boolean bool = affiliationService.applyForAffiliation(guidePhone, scenicID, applyDate);
		
		return bool;
	}
	
	
	/**
	 * 取消导游所挂靠的景区
	 * @param guidePhone  导游手机号
	 * @param scenicID   景区编号
	 * @return
	 */
	@RequestMapping(value = "/cancleAffiliation.do")
	@ResponseBody
	public Object cancleAffiliation(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone, 
			@RequestParam("scenicID") String scenicID) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String quitDate = dateFormat.format(new Date());
		
		boolean bool = affiliationService.cancleAffiliation(guidePhone, scenicID, quitDate);
		
		return bool;
	}
	
	
	
	/**
	 * 查看当前景区的申请挂靠时间
	 * @param resp
	 * @param guidePhone  导游手机号 
	 * @param scenicID  景区编号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getApplyDateofCurrentScenic.do")
	@ResponseBody
	public Object getApplyDateofCurrentScenic(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone, 
			@RequestParam("scenicID") String scenicID) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		listResult = affiliationService.getApplyDateofCurrentScenic(guidePhone, scenicID);
		
		return listResult;
	}
	
	
	
	/**
	 * 查看该导游的挂靠景区记录
	 * @param resp
	 * @param guidePhone  手机号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getApplyHistory.do")
	@ResponseBody
	public Object getApplyHistory(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		listResult = affiliationService.getApplyHistory(guidePhone);
		
		return listResult;
	}
}
