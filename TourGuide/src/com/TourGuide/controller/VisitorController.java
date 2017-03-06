package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.VisitorInfo;
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
			@RequestParam("image") String image,
			@RequestParam("openID") String openID) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		boolean bool = visitorService.visitorRegister(nickName, sex, name, phone, passwd, image, openID);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(bool));
		writer.flush();
	}
	
	

	/**
	 * 根据游客的手机号，查询个人详细信息
	 * @param resp
	 * @param phone  手机号
	 * @throws IOException
	 * @return 手机号、姓名、性别、昵称、头像
	 */
	@RequestMapping(value = "/getVisitorInfoWithPhone.do")
	public void getVisitorInfoWithPhone(HttpServletResponse resp,
			@RequestParam("phone") String phone) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		VisitorInfo visitorInfo = visitorService.getVisitorInfoWithPhone(phone);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(visitorInfo));
		writer.flush();
	}
}
