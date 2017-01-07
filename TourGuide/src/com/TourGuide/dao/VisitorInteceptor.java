package com.TourGuide.dao;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Repository
public class VisitorInteceptor  implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String[] notFilter = new String[] {
				"/image","login.do"};
		
		// 请求的URI
		String uri = request.getRequestURI();
		
		// 是否过滤
		Boolean doFilter = true;
		for (String s : notFilter) {
			if (-1 != uri.indexOf(s)) {
				doFilter = false;
				break;
			}
		}
		
		if (doFilter) {
			
			// 从session中获取登录者的实体
			Object  objAdmin = request.getSession().getAttribute("visitorSession");
			 
			if (null == objAdmin) {

				// 未登录
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
				builder.append("alert(\"页面已过期，请重新登录!\");");
				builder.append(" </script>");
				out.print(builder.toString());
				out.close();
				
				return false;
			}
			else {
				return true;
			}
			
			
		} else {
			return true;
		}
	}

}