package com.TourGuide.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import com.TourGuide.dao.VisitorDao;
import com.TourGuide.model.SNSUserInfo;
import com.TourGuide.model.VisitorInfo;
import org.springframework.ui.ModelMap;
import com.TourGuide.model.WeixinOauth2Token;
import com.TourGuide.service.VisitorService;
import com.TourGuide.weixin.util.Oauth2Util;
import com.TourGuide.weixin.util.SNSUserInfoUtil;
import com.TourGuide.weixin.util.SpringContextUtil;


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

			SNSUserInfo snsUserInfo = SNSUserInfoUtil.getSNSUserInfo(accessToken, openId);
			
			
			VisitorService visitorService = (VisitorService) SpringContextUtil.getBean("visitorService");
			VisitorInfo visitorInfo=visitorService.getInfobyOpenID(openId);								

			
			// 设置要传递的参数
			request.setAttribute("snsUserInfo", snsUserInfo);
			
			if(null == visitorInfo || visitorInfo.getOpenID()==null){
				request.getRequestDispatcher("redirect.jsp").forward(request, response);
			}else {
				String phone = visitorInfo.getPhone();
				response.sendRedirect("/TourGuide/web/index.html?phone="+phone);
				// 跳转到index.html
//				PrintWriter w = response.getWriter();
//				w.print("<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>"
//				+"<script type='text/javascript'>window.location.href='http://1f656026j8.imwork.net/TourGuide/web/index.html';"
//				+"</script></body></html>");
//				w.close();
			}
		}
					
	}

}
