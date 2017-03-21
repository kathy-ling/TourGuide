package com.TourGuide.Action;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@RequestMapping(value="operate")
@SessionAttributes("adminSession")
public class OperateViewAction {
	
	
	
	/**
	 * 跳转主页
	 * @return
	 */
	@RequestMapping(value="/index.action",method=RequestMethod.GET)
	public String Tomain()
	{
		return "operate/main";
	}
	
	


}
