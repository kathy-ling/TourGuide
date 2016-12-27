package com.TourGuide.Action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/*
 * 登录的Controller
 * 
 * */

@Controller
public class LoginAction {

	/*
	 * 登录检验
	 * 
	 * */
	@RequestMapping(value="/logincheck.action")
	public String  logincheck() {
		System.out.println("进入登录界面");
		return "index";
	}
	/*
	 * 登录界面
	 * 
	 * 
	 * */
	@RequestMapping(value="/login.action" )
	public ModelAndView Login(String username,String password) {
	
		if (username.equals("15029319152")&&password.equals("123456")) {
			return new ModelAndView("main");
		} else {
			return new ModelAndView("index","error","用户名或密码错误");
		}
		
	}
}
