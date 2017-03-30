package com.TourGuide.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.weixin.util.JsSignUtil;

@Controller
public class weixinScanController {
	
	/**
	 * 微信扫一扫接口的后台处理程序
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/weixinScan.do")
	 @ResponseBody
	 public Object weixinScan( HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
<<<<<<< HEAD
		CommonResp.SetUtf(response);
=======

		CommonResp.SetUtf(response);
		
>>>>>>> 8153f72fcb4d7b4aebd3bea0f8ab9f221e799bcf
		 String weburl = request.getParameter("url"); 

	     Map<String, String> resMap = new HashMap<String, String>();
	     resMap = JsSignUtil.sign(weburl);
	     
	     return resMap;
	 }

}
