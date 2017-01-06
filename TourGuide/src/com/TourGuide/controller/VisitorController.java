package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.service.VisitorService;
import com.google.gson.Gson;

@Controller
public class VisitorController {
	
	@Autowired
	VisitorService visitorService;
	
	
	/**
	 * 游客注册
	 * @param resp
	 * @param nickName  昵称
	 * @param sex  性别
	 * @param name   姓名
	 * @param phone   手机号 
	 * @param passwd   用户密码
	 * @param image  用户头像
	 * @throws IOException
	 */
	@RequestMapping(value = "/visitorRegister.do")
	public void visitorRegister(HttpServletResponse resp,
			@RequestParam("nickName") String nickName, 
			@RequestParam("sex") String sex, 
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("passwd") String passwd,
			@RequestParam("image") String image) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		boolean bool = visitorService.visitorRegister(nickName, sex, name, phone, passwd, image);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(bool));
		writer.flush();
	}

}
