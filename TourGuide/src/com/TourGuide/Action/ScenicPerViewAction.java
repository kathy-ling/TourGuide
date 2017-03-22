package com.TourGuide.Action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@RequestMapping(value="scenicPer")
@SessionAttributes("adminSession")
public class ScenicPerViewAction {
	
	/**
	 * 跳转主页
	 * @return
	 */
	@RequestMapping(value="/index.action",method=RequestMethod.GET)
	public String Tomain()
	{
		return "scenicPer/main";
	}
	
	/**
	 * 跳转到运营人员管理界面
	 * @return
	 */
	@RequestMapping(value="/operateInfo.action",method=RequestMethod.GET)
	public String ToOperateInfo()
	{
		return "scenicPer/other/operateUser";
	}
	
	
	
	/**
	 * 跳转到运营人员管理界面
	 * @return
	 */
	@RequestMapping(value="/scenicInfo.action",method=RequestMethod.GET)
	public String ToscenicInfo()
	{
		return "scenicPer/scenic/scenicInfo";
	}

}
