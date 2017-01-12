package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TourGuide.common.CommonResp;
import com.TourGuide.service.GuideService;
import com.google.gson.Gson;

@Controller
public class GuideController {
	
	@Autowired
	private GuideService guideService;
	
	
	/**
	 * 讲解员提交相应的信息，申请认证
	 * @param resp
	 * @param phone  手机号
	 * @param name  姓名
	 * @param sex  性别
	 * @param language  讲解语言
	 * @param selfIntro   自我介绍
	 * @param image  头像
	 * @param age    年龄
	 * @throws IOException
	 */
	@RequestMapping(value = "/getGuideAuthentication.do")
	public void getGuideAuthentication(HttpServletResponse resp,
			@RequestParam("phone") String phone, 
			@RequestParam("name") String name, 
			@RequestParam("sex") String sex,
			@RequestParam("language") String language, 
			@RequestParam("selfIntro") String selfIntro, 
			@RequestParam("image") String image,
			@RequestParam("age") String age) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		int ret = guideService.getGuideAuthentication(phone, name, 
				sex, language, selfIntro, image, Integer.parseInt(age));
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(ret));
		writer.flush();
	}
	
	
	
	/**
	 * 查询最受欢迎的讲解员
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getPopularGuides.do")
	public void getPopularGuides(HttpServletResponse resp) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String , Object>> listResult = guideService.getPopularGuides();
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(listResult));
		writer.flush();
	}
}
