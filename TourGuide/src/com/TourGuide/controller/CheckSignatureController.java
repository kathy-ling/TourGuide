package com.TourGuide.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TourGuide.common.CommonResp;
import com.TourGuide.common.SignUtil;
import com.TourGuide.service.PromotionService;
import com.google.gson.Gson;

@Controller
public class CheckSignatureController {

	
	@Autowired
	public PromotionService promotionService;
	
	@RequestMapping(value = "CheckSignature.do")
	@ResponseBody
	public Object CheckSignature(HttpServletResponse resp,
			@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp, 
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		String ret = null;
		
		if(SignUtil.checkSignature(signature, timestamp, nonce)){
			ret = echostr;
		}
		
		return ret;
	}
}
