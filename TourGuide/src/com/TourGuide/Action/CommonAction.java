package com.TourGuide.Action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value="view")
@SessionAttributes("adminSession")
public class CommonAction {
	
	/*
	 * 跳转首页Action
	 * */
	@RequestMapping(value="/FirstShow.action",method=RequestMethod.GET)
	public String FirstShow()
	{
		return "firsthome";
	}
	
	@RequestMapping(value="/operateUser.action",method=RequestMethod.GET)
	public String ToOperateUser()
	{
		return "operateUser";
	}
	
	@RequestMapping(value="/index.action",method=RequestMethod.GET)
	public String ToMain1()
	{
		return "main1";
	}
	
	@RequestMapping(value="/scenicInfo.action",method=RequestMethod.GET)
	public String ToScenicInfo()
	{
		return "scenicInfo";
	}
	
	@RequestMapping(value="/visitor.action",method=RequestMethod.GET)
	public String ToVisitorInfo()
	{
		return "visitorInfo";
	}
	
}
