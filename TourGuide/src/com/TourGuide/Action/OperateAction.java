package com.TourGuide.Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.Operateper;
import com.TourGuide.service.OperateperService;
import com.google.gson.Gson;

/*
 * 运营人员的Controller
 * 
 * */
@Controller
@RequestMapping(value="operate")
@SessionAttributes("adminSession")
public class OperateAction {

	@Autowired
	private OperateperService operateperService;
	
	
	/*
	 * 通过当前页面与页面容量数目获取运营人员数目
	 * 参数：当前页，页面容量
	 * 2016-12-30 15:24:51	
*/
	@RequestMapping(value="/GetOperateUser.action",method=RequestMethod.GET)
	@ResponseBody
	public Object GetOperateInfoByPage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<Operateper> list=operateperService.getOperatepersByPage(currentPage, pageRows);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		int i=operateperService.GetOperateCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	/*
	 * 通过sql语句获取运营人员的信息
	 * 参数：sql语句
	 * 2016-12-31 15:12:07	
*/
	@RequestMapping(value="/SearchOperateUser.action",method=RequestMethod.GET)
	@ResponseBody
	public Object SearchOperateInfoByAccount(HttpServletResponse resp,
			@RequestParam(value="sql")String sqlStr) throws IOException {
		CommonResp.SetUtf(resp);
		List<Operateper> list = operateperService.SearchOperateInfoByAccount_Service(sqlStr);
		String jsonStr = new Gson().toJson(list).toString();
		Map<String , Object> map=new HashMap<>();
		map.put("jsonStr", jsonStr);
		return map;
	}
	
	
	/*
	 *增加运营人员 
	 * 
	 * */
	@RequestMapping(value="/AddOperateperInfo.action",method=RequestMethod.GET)
	@ResponseBody
	public Object AddOperateperInfo(HttpServletResponse reap,
			@RequestParam(value="name")String name,
			@RequestParam(value="account")String account,
			@RequestParam(value="role")String role,
			@RequestParam(value="phone")String phone) throws IOException {
		Operateper operateper = new Operateper();
		operateper.setOperateper_name(name);
		operateper.setOperateper_account(account);
		operateper.setOperateper_role(role);
		operateper.setOperateper_phone(phone);
		
		boolean confirm = operateperService.AddOperateperInfo_Service(operateper);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
	
	@RequestMapping(value="/DeleteOperateperInfo.action",method=RequestMethod.GET)
	@ResponseBody
	public Object DeleteOperateperInfo(HttpServletResponse reap,
			@RequestParam(value="account")String s
			) throws IOException {
		
		
		boolean confirm = operateperService.DeleteOperateperInfo_Service(s);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
	
	@RequestMapping(value="/UpdateOperateperInfo.action",method=RequestMethod.GET)
	@ResponseBody
	public Object UpdateOperateperInfo(HttpServletResponse reap,
			@RequestParam(value="name")String name,
			@RequestParam(value="account")String account,
			@RequestParam(value="role")String role,
			@RequestParam(value="phone")String phone,
			@RequestParam(value="bool")int bool) throws IOException {
		Operateper operateper = new Operateper();
		operateper.setOperateper_name(name);
		operateper.setOperateper_account(account);
		operateper.setOperateper_role(role);
		operateper.setOperateper_phone(phone);
		operateper.setOperateper_bool(bool);
		boolean confirm = operateperService.UpdateOperateperInfo_Service(operateper);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
}
