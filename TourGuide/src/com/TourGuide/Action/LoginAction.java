package com.TourGuide.Action;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.AdminInfo;
import com.TourGuide.service.AdminService;


/*
 * 管理员登录的Controller
 * 
 * */

@Controller
@SessionAttributes("adminSession")
public class LoginAction {

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
	@RequestMapping(value="/login.action" ,method=RequestMethod.POST)
	@ResponseBody
	public Object  Login(@RequestParam(value="username")String username,
			@RequestParam(value="password")String password,
			HttpServletRequest request, 
			HttpSession session, ModelMap model,HttpServletResponse resp) throws IOException {
			
			boolean flag=adminService.isValid(username, password);
			AdminInfo adminInfo=new AdminInfo();
			adminInfo.setUsername(username);
			adminInfo.setPassword(password);
			String role=adminService.getRoleByAccount(username);
			if (flag==true) {
			
			// 添加用户session
			model.addAttribute("adminSession", adminInfo);
			if (role.equals("超级管理员")) {
				resp.sendRedirect("/TourGuide/admin/index.action");
			}else if (role.equals("运营人员")) {
				resp.sendRedirect("/TourGuide/operate/index.action");
			} 
			else {
				resp.sendRedirect("/TourGuide/scenicPer/index.action");
			}
			
			return null;
			} else {
			return new ModelAndView("index","error","用户名或密码错误");
		}
	}
	
	@RequestMapping(value="/UpdatePass.action" ,method=RequestMethod.POST)
	@ResponseBody
	public Object UpdatePass(HttpServletResponse resp,
			String username,String passNew,String passOld) {
		
		CommonResp.SetUtf(resp);
		int i=adminService.UpdatePass(username, passNew, passOld);
		
		return i;
		
	}
	
	
	
	@RequestMapping(value="/exit.action" ,method=RequestMethod.POST)
	@ResponseBody
	public boolean  exitSys(HttpServletResponse resp,HttpServletRequest req) {
		
		HttpSession session=req.getSession(false);
		session.invalidate();
		return true;
		
	}
	
	
}
