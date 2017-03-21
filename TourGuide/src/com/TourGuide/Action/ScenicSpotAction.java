package com.TourGuide.Action;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.AdminInfo;
import com.TourGuide.model.ScenicTeam;
import com.TourGuide.model.ScenicsSpotInfo;
import com.TourGuide.service.ScenicSpotService;
import com.TourGuide.service.ScenicTeamService;
import com.google.gson.Gson;
/*
 * 景区信息的Controller
 * 
 * */
@Controller
@RequestMapping(value="scenic")
@SessionAttributes("adminSession")
public class ScenicSpotAction {

		@Autowired
		private ScenicSpotService scenicSpotService;
		
		@Autowired
		private ScenicTeamService scenicTeamService;
		private String fileName; 
		/*
		 * 通过当前页面与页面容量数目获取景区信息数目
		 * 参数：当前页，页面容量
		 * 2017-1-2 10:12:30	
	*/
		@RequestMapping(value="/GetScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object GetScenicInfoByPage(HttpServletResponse resp,
				@RequestParam(value="currentPage")int currentPage,
				@RequestParam(value="pageRows")int pageRows ) throws IOException
		{
			
			CommonResp.SetUtf(resp);
			List<ScenicsSpotInfo> list=scenicSpotService.getScenicInfoByPage(currentPage, pageRows);
			Map< String , Object> map=new HashMap<>();
			String jsonStr=new Gson().toJson(list).toString();
			int i=scenicSpotService.GetScenicCount();
			map.put("jsonStr", jsonStr);
			map.put("page", currentPage);
			map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
			return map;
		}
		
		/*
		 * 通过sql语句获取景区的信息
		 * 参数：sql语句
		 * 2017-1-2 10:15:30	
	*/
		@RequestMapping(value="/SearchScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object SearchScenicInfoByName(HttpServletResponse resp,
				@RequestParam(value="sql")String sqlStr,HttpSession session) throws IOException {
			
			
			CommonResp.SetUtf(resp);
			
			AdminInfo adminInfo=(AdminInfo) session.getAttribute("adminSession");
			if (sqlStr.equals("a")) {
				sqlStr=scenicTeamService.getScenicNameByAccount(adminInfo.getUsername());
			}
			
			ScenicsSpotInfo list = scenicSpotService.SearchScenicInfoByName_Service(sqlStr);
			String jsonStr = new Gson().toJson(list).toString();
			Map<String , Object> map=new HashMap<>();
			map.put("jsonStr", jsonStr);
			return map;
		}
		
		
		/**
		 * 通过省份，城市，详细地址进行搜索景区
		 * @param provin
		 * @param city
		 * @param s
		 * @param resp
		 * @return 景区list
		 * 2017-1-7 19:21:56
		 */
		@RequestMapping(value="SearchScenicInfoByloc.action",method=RequestMethod.POST)
		@ResponseBody
		public Object SearchScenicInfoByLocation(@RequestParam(value="pro")String provin,
				@RequestParam(value="city")String city,
				@RequestParam(value="s")String s,
				HttpServletResponse resp)
		{
			CommonResp.SetUtf(resp);
			
			String jsonStr;
			ScenicsSpotInfo list=scenicSpotService.SearchSceincInfoByLocation(provin, city, s);
			if(list.getScenicNo()!=null){
				jsonStr=new Gson().toJson(list).toString();
			}else
			{
				jsonStr="";
			}
			Map< String, Object> map=new HashMap<>();
			map.put("str", jsonStr);
			return map;
		}
		/*
		 *增加景区信息
		 * 2017-1-2 19:48:11
		 * */
		@RequestMapping(value="/AddScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object AddScenicInfo(HttpServletResponse reap,
				@RequestParam(value="scenicNo")String scenicNo,
				@RequestParam(value="scenicName")String scenicName,
				@RequestParam(value="totalVisits")String totalVisits,
				@RequestParam(value="openingHours")String openingHours,
				@RequestParam(value="scenicLevel")String scenicLevel,
				@RequestParam(value="scenicLocation")String scenicLocation,
				@RequestParam(value="isHotSpot")int isHotSpot,
				@RequestParam(value="province")String province,
				@RequestParam(value="city")String city,
				@RequestParam(value="chargePerson")String chargePerson,
				@RequestParam(value="scenicIntro")String scenicIntro,
				@RequestParam(value="account")String account,
				@RequestParam(value="password")String password) throws IOException {
			
			
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicNo(scenicNo);
			scenicsSpotInfo.setScenicName(scenicName);
			scenicsSpotInfo.setTotalVisits(totalVisits);
			scenicsSpotInfo.setOpeningHours(openingHours);
			scenicsSpotInfo.setScenicLevel(scenicLevel);
			scenicsSpotInfo.setScenicIntro(scenicIntro);
			scenicsSpotInfo.setScenicLocation(scenicLocation);
			scenicsSpotInfo.setProvince(province);
			scenicsSpotInfo.setCity(city);
			scenicsSpotInfo.setChargePerson(chargePerson);
			scenicsSpotInfo.setIsHotSpot(isHotSpot);
			scenicsSpotInfo.setScenicImagePath(fileName);
			scenicsSpotInfo.setAccount(account);
			
			
			boolean confirm = scenicSpotService.AddScenicInfo_Service(scenicsSpotInfo,password);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		/*
		 * 通过景区名称进行删除景区信息
		 * 参数：景区名称
		 * 2017-1-2 19:49:04
		 * */
		@RequestMapping(value="/DeleteScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object DeleteScenicInfo(HttpServletResponse reap,
				@RequestParam(value="scenicName")String s
				) throws IOException {
			
			
			boolean confirm = scenicSpotService.DeleteScenicInfo_Service(s);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		/*
		 * 更新景区信息
		 * 2017-1-2 19:49:32
		 * */
		@RequestMapping(value="/UpdateScenicInfo.action",method=RequestMethod.POST)
		@ResponseBody
		public Object UpdateScenicInfo(HttpServletResponse reap,
				@RequestParam(value="scenicNo")String scenicNo,
				@RequestParam(value="scenicName")String scenicName,
				@RequestParam(value="totalVisits")String totalVisits,
				@RequestParam(value="openingHours")String openingHours,
				@RequestParam(value="scenicLevel")String scenicLevel,
				@RequestParam(value="scenicLocation")String scenicLocation,
				@RequestParam(value="isHotSpot")int isHotSpot,
				@RequestParam(value="province")String province,
				@RequestParam(value="city")String city,
				@RequestParam(value="chargePerson")String chargePerson,
				@RequestParam(value="scenicIntro")String scenicIntro,
				@RequestParam(value="account")String account) throws IOException {
			
			
			ScenicsSpotInfo scenicsSpotInfo = new ScenicsSpotInfo();
			scenicsSpotInfo.setScenicNo(scenicNo);
			scenicsSpotInfo.setScenicName(scenicName);
			scenicsSpotInfo.setTotalVisits(totalVisits);
			scenicsSpotInfo.setOpeningHours(openingHours);
			scenicsSpotInfo.setScenicLevel(scenicLevel);
			scenicsSpotInfo.setScenicIntro(scenicIntro);
			scenicsSpotInfo.setScenicLocation(scenicLocation);
			scenicsSpotInfo.setProvince(province);
			scenicsSpotInfo.setCity(city);
			scenicsSpotInfo.setChargePerson(chargePerson);
			scenicsSpotInfo.setIsHotSpot(isHotSpot);
			scenicsSpotInfo.setScenicImagePath(fileName);
			scenicsSpotInfo.setAccount(account);
			
			boolean confirm = scenicSpotService.UpdateScenicInfo_Service(scenicsSpotInfo);
			Map<String, Object> map = new HashMap<>();
			map.put("confirm", confirm);
			return map;
		}
		
		
		/**
		 * 上传景区图片
		 * @param resp
		 * @param request
		 * @param file
		 * @return
		 * 
		 * 2017-1-15 15:52:26
		 */
		@RequestMapping(value="/UploadImage.action",method=RequestMethod.POST)
		@ResponseBody
		public Object UploadImage(HttpServletResponse resp,HttpServletRequest request,
				@RequestParam MultipartFile file) {
			
			CommonResp.SetUtf(resp);
			Map<String , Object> map=new HashMap<>();
			String realPath=request.getSession().getServletContext().getRealPath("image/scenics");
			File pathFile = new File(realPath);
			if (!pathFile.exists()) {
				//文件夹不存 创建文件
				System.out.println("目录不存在，创建目录");
				pathFile.mkdirs();
			}
			fileName="/image/scenics/"+file.getOriginalFilename();
			System.out.println("文件类型："+file.getContentType());
			System.out.println("文件名称："+file.getOriginalFilename());
			System.out.println("文件大小:"+file.getSize());
			System.out.println(".................................................");
				//将文件copy上传到服务器
			try {
				System.out.println(realPath + "/" + file.getOriginalFilename());
				File fileImageFile=new File(realPath + "/" + file.getOriginalFilename());
				file.transferTo(fileImageFile);
				System.out.println("图片上传成功");
				map.put("json", "true");
			} catch (IllegalStateException | IOException e) {
					
				e.printStackTrace();
				map.put("json", "false");
			}	
			return  map;
		}  	
		
		
		
		@RequestMapping(value="/EditImage.action",method=RequestMethod.POST)
		@ResponseBody
		public Object EditImage(HttpServletResponse resp,HttpServletRequest request,
				@RequestParam MultipartFile editfile) {
			
			CommonResp.SetUtf(resp);
			Map<String , Object> map=new HashMap<>();
//			String realPath="E:/Project/TourGuide/TourGuide/WebRoot/image/scenics";
			String realPath=request.getSession().getServletContext().getRealPath("image/scenics");
			File pathFile = new File(realPath);
			if (!pathFile.exists()) {
				//文件夹不存 创建文件
				System.out.println("目录不存在，创建目录");
				pathFile.mkdirs();
			}
			fileName="/image/scenics/"+editfile.getOriginalFilename();
			System.out.println("文件类型："+editfile.getContentType());
			System.out.println("文件名称："+editfile.getOriginalFilename());
			System.out.println("文件大小:"+editfile.getSize());
			System.out.println(".................................................");
				//将文件copy上传到服务器
			try {
				System.out.println(realPath + "/" + editfile.getOriginalFilename());
				File fileImageFile=new File(realPath + "/" + editfile.getOriginalFilename());
				editfile.transferTo(fileImageFile);
				System.out.println("图片上传成功");
				map.put("json", "true");
			} catch (IllegalStateException | IOException e) {
					
				e.printStackTrace();
				map.put("json", "false");
			}	
			return  map;
		}  	
}
