package com.TourGuide.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.AdminInfo;
import com.TourGuide.service.MenuService;


@Controller
@RequestMapping(value = "/Menu")
@SessionAttributes("adminSession")
public class MenuAction {
	
	@Autowired
	private MenuService menuService;
	
	
	@RequestMapping(value="getMenu.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetMenu(HttpServletResponse resp,
			HttpSession session) {
		
		CommonResp.SetUtf(resp);
		
		Map<String , Object> map=new HashMap<String, Object>();
		AdminInfo accont=(AdminInfo) session.getAttribute("adminSession");
		
		String account=(String) menuService.getMenuByrole(accont.getUsername()).get("string");
		String role=(String) menuService.getMenuByrole(accont.getUsername()).get("role");
		map.put("menuHtml", account);
		map.put("role", role);
		map.put("account", accont.getUsername());
		return map;
	}

	@RequestMapping(value="getAuthor.action",method=RequestMethod.POST)
	@ResponseBody
	public Object GetAuthor(HttpServletResponse resp,
			HttpSession session) {
		
		CommonResp.SetUtf(resp);
		
		List<Map<String, Object>> account= menuService.getAuthority();
		System.out.println(account);
		return account;
	}
	
}
