package com.TourGuide.Action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OperateUserAction {
	
	
	
	/*
	 * 获取运营人员的相关信息
	 * 参数：当前页，页面信息允许数目
	 * time：2016-12-29 20:22:41
	 * */
	@RequestMapping(value="GetOperateUser.action",method=RequestMethod.GET)
	public void GetOperateUserByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int index,
			@RequestParam(value="pageRows")int rows) {
		
	}
}
