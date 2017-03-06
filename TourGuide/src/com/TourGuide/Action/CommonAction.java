package com.TourGuide.Action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.model.AdminInfo;
import com.TourGuide.service.MenuService;

@Controller
@RequestMapping(value="view")
@SessionAttributes("adminSession")
public class CommonAction {
	
	@Autowired
	private MenuService service;
	
	/*
	 * 跳转首页Action
	 * */
	@RequestMapping(value="/FirstShow.action",method=RequestMethod.GET)
	public String FirstShow()
	{
		return "admin/other/firsthome";
	}
	
	/*
	 * 跳转运营人员界面
	 * */
	@RequestMapping(value="/operateUser.action",method=RequestMethod.GET)
	public String ToOperateUser()
	{
		return "admin/other/operateUser";
	}
	
	/*
	 * 跳转主界面
	 * */
	@RequestMapping(value="/index.action",method=RequestMethod.GET)
	public String ToMain(HttpServletRequest request,HttpSession session)
	{
		AdminInfo accont=(AdminInfo) session.getAttribute("adminSession");
		
		String role=(String)service.getMenuByrole(accont.getUsername()).get("role");
		if (role.equals("超级管理员")) {
			return "admin/main";
		}else if (role.equals("运营人员")) {
			return "operate/main";
		} 
		else {
			return "scenicPer/main";
		}
	}
	
	/*
	 * 跳转景区界面
	 * */
	@RequestMapping(value="/scenicInfo.action",method=RequestMethod.GET)
	public String ToScenicInfo()
	{
		return "admin/scenic/scenicInfo";
	}
	/*
	 * 跳转游客界面
	 * */
	@RequestMapping(value="/visitor.action",method=RequestMethod.GET)
	public String ToVisitorInfo()
	{
		return "admin/visitor/visitorInfo";
	}
	
	/*
	 * 跳转讲解员信息管理页面的Action
	 * */
	@RequestMapping(value="/guideInfo.action",method=RequestMethod.GET)
	public String ToGuideInfo()
	{
		return "admin/guide/guideInfo";
	}
	
	/**
	 * 跳转讲解员日程安排页面的Action
	 * @return
	 */
	@RequestMapping(value="/guideWorkday.action",method=RequestMethod.GET)
	public String ToGuideWorkDay()
	{
		return "admin/guide/GuideWorkday";
	}
	
	/**
	 * 跳转已审核讲解员页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideInfoYes.action",method=RequestMethod.GET)
	public String ToGuideInfoYes()
	{
		return "admin/guide/GuideInfoYes";
	}
	
	/**
	 * 跳转未审核讲解员页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideInfoNo.action",method=RequestMethod.GET)
	public String ToGuideInfoNo()
	{
		return "admin/guide/GuideInfoNo";
	}
	
	/**
	 * 跳转景区收入页面的Action
	 * @return
	 */
	@RequestMapping(value="/ScenicFee.action",method=RequestMethod.GET)
	public String ToScenicFee()
	{
		return "admin/scenic/ScenicFee";
	}
	
	/**
	 * 跳转讲解员收入页面的Action
	 * @return
	 */
	@RequestMapping(value="/GuideFee.action",method=RequestMethod.GET)
	public String ToGuideFee()
	{
		return "admin/guide/GuideFee";
	}
	
	/**
	 * 跳转订单信息界面
	 * @return
	 */
	@RequestMapping(value="/BookOrderInfo.action",method=RequestMethod.GET)
	public String ToBookOrderInfo()
	{
		return "admin/order/BookOrderInfo";
	}
	
	
	/**
	 * 跳转游客黑名单信息界面
	 * @return
	 */
	@RequestMapping(value="/visitorInfoDisabled.action",method=RequestMethod.GET)
	public String ToVisitorDisabled()
	{
		return "admin/visitor/visitorInfoDisabled";
	}
	
	/**
	 * 跳转景区门票信息界面
	 * @return
	 */
	@RequestMapping(value="/scenicTicket.action",method=RequestMethod.GET)
	public String ToscenicTicket()
	{
		return "admin/scenic/scenicTicket";
	}
	
	@RequestMapping(value="/scenicTeam.action",method=RequestMethod.GET)
	public String ToscenicTeam()
	{
		return "admin/scenic/scenicTeam";
	}
	
	/**
	 * 跳转景区门票信息界面
	 * @return
	 */
	@RequestMapping(value="/authority.action",method=RequestMethod.GET)
	public String ToAuthority()
	{
		return "admin/scenic/Authority";
	}
	
	@RequestMapping(value="/RewardOrPunishRecord.action",method=RequestMethod.GET)
	public String Toregist()
	{
		return "admin/Record/RewardOrPunishRecord";
	}
	
	
	@RequestMapping(value="/OperateRecord.action",method=RequestMethod.GET)
	public String ToOperateRecord()
	{
		return "admin/Record/OperateRecord";
	}
	
	@RequestMapping(value="/SysManager.action",method=RequestMethod.GET)
	public String ToSysManager()
	{
		return "admin/other/SysManager";
	}
}
