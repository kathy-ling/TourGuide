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
		return "admin/firsthome";
	}
	
	/*
	 * 跳转运营人员界面
	 * */
	@RequestMapping(value="/operateUser.action",method=RequestMethod.GET)
	public String ToOperateUser()
	{
		return "admin/operateUser";
	}
	
	/*
	 * 跳转主界面
	 * */
	@RequestMapping(value="/index.action",method=RequestMethod.GET)
	public String ToMain1()
	{
		return "admin/main1";
	}
	
	/*
	 * 跳转景区界面
	 * */
	@RequestMapping(value="/scenicInfo.action",method=RequestMethod.GET)
	public String ToScenicInfo()
	{
		return "admin/scenicInfo";
	}
	/*
	 * 跳转游客界面
	 * */
	@RequestMapping(value="/visitor.action",method=RequestMethod.GET)
	public String ToVisitorInfo()
	{
		return "admin/visitorInfo";
	}
	
	/*
	 * 跳转讲解员信息管理页面的Action
	 * */
	@RequestMapping(value="/guideInfo.action",method=RequestMethod.GET)
	public String ToGuideInfo()
	{
		return "admin/guideInfo";
	}
	
	/**
	 * 跳转讲解员日程安排页面的Action
	 * @return
	 */
	@RequestMapping(value="/guideWorkday.action",method=RequestMethod.GET)
	public String ToGuideWorkDay()
	{
		return "admin/GuideWorkday";
	}
	
	/**
	 * 跳转已审核讲解员页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideInfoYes.action",method=RequestMethod.GET)
	public String ToGuideInfoYes()
	{
		return "admin/GuideInfoYes";
	}
	
	/**
	 * 跳转未审核讲解员页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideInfoNo.action",method=RequestMethod.GET)
	public String ToGuideInfoNo()
	{
		return "admin/GuideInfoNo";
	}
	
	/**
	 * 跳转景区收入页面的Action
	 * @return
	 */
	@RequestMapping(value="/ScenicFee.action",method=RequestMethod.GET)
	public String ToScenicFee()
	{
		return "admin/ScenicFee";
	}
	
	/**
	 * 跳转讲解员收入页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideFee.action",method=RequestMethod.GET)
	public String ToGuideFee()
	{
		return "admin/GuideFee";
	}
	
	/**
	 * 跳转订单信息界面
	 * @return
	 */
	@RequestMapping(value="/BookOrderInfo.action",method=RequestMethod.GET)
	public String ToBookOrderInfo()
	{
		return "admin/BookOrderInfo";
	}
	
	
	/**
	 * 跳转游客黑名单信息界面
	 * @return
	 */
	@RequestMapping(value="/visitorInfoDisabled.action",method=RequestMethod.GET)
	public String ToVisitorDisabled()
	{
		return "admin/visitorInfoDisabled";
	}
	
	/**
	 * 跳转景区门票信息界面
	 * @return
	 */
	@RequestMapping(value="/scenicTicket.action",method=RequestMethod.GET)
	public String ToscenicTicket()
	{
		return "admin/scenicTicket";
	}
	
	/**
	 * 跳转景区门票信息界面
	 * @return
	 */
	@RequestMapping(value="/authority.action",method=RequestMethod.GET)
	public String ToAuthority()
	{
		return "admin/Authority";
	}
	
	@RequestMapping(value="/RewardOrPunishRecord.action",method=RequestMethod.GET)
	public String Toregist()
	{
		return "admin/RewardOrPunishRecord";
	}
	
	
	@RequestMapping(value="/OperateRecord.action",method=RequestMethod.GET)
	public String ToOperateRecord()
	{
		return "admin/OperateRecord";
	}
}
