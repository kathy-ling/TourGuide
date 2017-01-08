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
	
	/*
	 * 跳转运营人员界面
	 * */
	@RequestMapping(value="/operateUser.action",method=RequestMethod.GET)
	public String ToOperateUser()
	{
		return "operateUser";
	}
	
	/*
	 * 跳转主界面
	 * */
	@RequestMapping(value="/index.action",method=RequestMethod.GET)
	public String ToMain1()
	{
		return "main1";
	}
	
	/*
	 * 跳转景区界面
	 * */
	@RequestMapping(value="/scenicInfo.action",method=RequestMethod.GET)
	public String ToScenicInfo()
	{
		return "scenicInfo";
	}
	/*
	 * 跳转游客界面
	 * */
	@RequestMapping(value="/visitor.action",method=RequestMethod.GET)
	public String ToVisitorInfo()
	{
		return "visitorInfo";
	}
	
	/*
	 * 跳转讲解员信息管理页面的Action
	 * */
	@RequestMapping(value="/guideInfo.action",method=RequestMethod.GET)
	public String ToGuideInfo()
	{
		return "guideInfo";
	}
	
	/**
	 * 跳转讲解员日程安排页面的Action
	 * @return
	 */
	@RequestMapping(value="/guideWorkday.action",method=RequestMethod.GET)
	public String ToGuideWorkDay()
	{
		return "GuideWorkday";
	}
	
	/**
	 * 跳转已审核讲解员页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideInfoYes.action",method=RequestMethod.GET)
	public String ToGuideInfoYes()
	{
		return "GuideInfoYes";
	}
	
	/**
	 * 跳转未审核讲解员页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideInfoNo.action",method=RequestMethod.GET)
	public String ToGuideInfoNo()
	{
		return "GuideInfoNo";
	}
}
