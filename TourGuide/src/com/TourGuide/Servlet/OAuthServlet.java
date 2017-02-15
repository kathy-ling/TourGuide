package com.TourGuide.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.TourGuide.model.WeixinOauth2Token;
import com.TourGuide.weixin.util.Oauth2Util;
import com.TourGuide.weixin.util.SNSUserInfoUtil;


/**
 * 授权后的回调请求处理
 * @author Tian
 *
 */
public class OAuthServlet extends HttpServlet{
	
	private static final long serialVersionUID = -1847238807216447030L;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response,ModelMap model) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 用户同意授权后，能获取到code
		// 如果code等于"authdeny"，表示用户不同意授权，则直接跳转到目标页面。
		String code = request.getParameter("code");
		System.out.println("code :" + code);
		
		// 用户同意授权
		if (!"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = Oauth2Util.getOauth2AccessToken(code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			SNSUserInfoUtil.snsUserInfo = SNSUserInfoUtil.getSNSUserInfo(accessToken, openId);
			SNSUserInfoUtil.snsUserInfo.getNickname();
			SNSUserInfoUtil.snsUserInfo.getSex();
			model.addAttribute("visitor", SNSUserInfoUtil.snsUserInfo);							
			// 设置要传递的参数
			request.setAttribute("snsUserInfo", SNSUserInfoUtil.snsUserInfo);
		}
		// 跳转到index.html
		PrintWriter w = response.getWriter();
		w.print("<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>"
		+"<script type='text/javascript'>window.location.href='http://1f656026j8.imwork.net/TourGuide/web/index.html';"
		+"</script></body></html>");
		w.close();
	}

}
