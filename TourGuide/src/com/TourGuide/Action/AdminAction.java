package com.TourGuide.Action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.TourGuide.model.AdminInfo;
import com.TourGuide.service.AdminService;


/*
 * 管理员登录的Controller
 * 
 * */

@Controller
public class AdminAction {

	@Autowired
	private AdminService adminService;
	/*
	 * 登录检验
	 * 
	 * */
	@RequestMapping(value="/logincheck.action")
	public String  logincheck() {
		return "index";
	}
	/*
	 * 登录界面
	 * 
	 * 
	 * */
	@RequestMapping(value="/login.action" ,method=RequestMethod.POST )
	@ResponseBody
	public ModelAndView Login(@RequestParam(value="username")String username,
			@RequestParam(value="password")String password,
			HttpServletRequest request, 
			HttpSession session, ModelMap model) {
		boolean flag=adminService.isValid(username, password);
		AdminInfo adminInfo=new AdminInfo();
		adminInfo.setUsername(username);
		adminInfo.setPassword(password);
		if (flag==true) {
			
			// 添加用户session
			model.addAttribute("adminSession", adminInfo);
			return new ModelAndView("main1");
		} else {
			return new ModelAndView("index","error","用户名或密码错误");
		}
		

		
		
		
	}
}
