package com.TourGuide.Action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	@RequestMapping(value="/GetOperateUser.action",method=RequestMethod.POST)
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
	 * 通过账号获取运营人员的信息
	 * 参数：账号
	 * 2016-12-31 15:12:07	
*/
	@RequestMapping(value="/SearchOperateUser.action",method=RequestMethod.POST)
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
	@RequestMapping(value="/AddOperateperInfo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object AddOperateperInfo(HttpServletResponse resp,HttpServletRequest req,
			@RequestParam(value="name")String name,
			@RequestParam(value="account")String account,
			@RequestParam(value="role")String role,
			@RequestParam(value="phone")String phone,
			@RequestParam(value="scenicID")String scenicID,
			@RequestParam(value="password")String password) throws IOException {
		
		CommonResp.SetUtf(resp);
		Operateper operateper = new Operateper();
		operateper.setOperateper_name(name);
		operateper.setOperateper_account(account);
		operateper.setOperateper_role(role);
		operateper.setOperateper_phone(phone);
		operateper.setOperateper_scenic(scenicID);
		operateper.setOperateper_password(password);
		boolean confirm = operateperService.AddOperateperInfo_Service(operateper);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		HttpSession session=req.getSession();
		System.out.println(session.toString());
		return map;
	}
	
	/*
	 * 删除运营人员
	 * 参数：账号
	 * 2017-1-6 15:03:30
	 * */
	@RequestMapping(value="/DeleteOperateperInfo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object DeleteOperateperInfo(HttpServletResponse reap,
			@RequestParam(value="account")String s
			) throws IOException {
		
		
		boolean confirm = operateperService.DeleteOperateperInfo_Service(s);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
	
	/*
	 * 更新运营人员信息
	 * 2017-1-6 15:04:09
	 * */
	@RequestMapping(value="/UpdateOperateperInfo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object UpdateOperateperInfo(HttpServletResponse reap,
			@RequestParam(value="name")String name,
			@RequestParam(value="account")String account,
			@RequestParam(value="role")String role,
			@RequestParam(value="phone")String phone,
			@RequestParam(value="scenicID")String  scenicID,
			@RequestParam(value="password")String password) throws IOException {
		Operateper operateper = new Operateper();
		operateper.setOperateper_name(name);
		operateper.setOperateper_account(account);
		operateper.setOperateper_role(role);
		operateper.setOperateper_phone(phone);
		operateper.setOperateper_scenic(scenicID);;
		operateper.setOperateper_password(password);
		boolean confirm = operateperService.UpdateOperateperInfo_Service(operateper);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}
	/*
	 * 禁用运营人员
	 * */
	@RequestMapping(value="/ForbidOperate.action")
	@ResponseBody
	public Object ForbidOperate(HttpServletResponse resp,
			@RequestParam(value="account")String account) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", operateperService.ForbidOperate_Service(account));
		return map;
	}
	/*
	 * 解禁运营人员
	 * */
	@RequestMapping(value="/RelieveOperate.action")
	@ResponseBody
	public Object RelieveOperate(HttpServletResponse resp,
			@RequestParam(value="account")String account) throws IOException {
		CommonResp.SetUtf(resp);
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", operateperService.RelieveOperate_Service(account));
		return map;
	}
}

