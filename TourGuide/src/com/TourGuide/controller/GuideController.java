package com.TourGuide.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.TourGuide.common.CommonResp;
import com.TourGuide.model.ScenicsSpotInfo;
import com.TourGuide.service.GuideService;
import com.TourGuide.service.ScenicSpotService;
import com.google.gson.Gson;

@Controller
public class GuideController {
	
	@Autowired
	private GuideService guideService;
	
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	private String imgPath;
	
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
			@RequestParam("age") String age,
			@RequestParam("workAge") String workAge) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		int ret = guideService.getGuideAuthentication(phone, name, 
				sex, language, selfIntro, imgPath, Integer.parseInt(age), workAge);
		
		PrintWriter writer = resp.getWriter();
		writer.write(new Gson().toJson(ret));
		writer.flush();
	}
	
	
	@RequestMapping(value="/upLoadImg.do")
	public void UploadImage(HttpServletResponse resp,
			HttpServletRequest request,
			@RequestParam MultipartFile btn_file) throws IOException {
		
		CommonResp.SetUtf(resp);
		boolean ret = false;
		
		String realPath=request.getSession().getServletContext().getRealPath("image/visitor");
		File pathFile = new File(realPath);
		
		if (!pathFile.exists()) {
			//文件夹不存 创建文件
			System.out.println("目录不存在，创建目录");
			pathFile.mkdirs();
		}
		imgPath = "/image/visitor/" + btn_file.getOriginalFilename();
		System.out.println("文件类型："+btn_file.getContentType());
		System.out.println("文件名称："+btn_file.getOriginalFilename());
		System.out.println("文件大小:"+btn_file.getSize());
		System.out.println(".................................................");
			//将文件copy上传到服务器
		try {
			System.out.println(realPath + "/" + btn_file.getOriginalFilename());
			File fileImageFile=new File(realPath + "/" + btn_file.getOriginalFilename());
			btn_file.transferTo(fileImageFile);
			System.out.println("图片上传成功");
			ret = true;
		} catch (IllegalStateException | IOException e) {
				
			e.printStackTrace();
		}	
		
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
	
	
	
	/**
	 * 查询可被预约的讲解员（时间冲突的讲解员已被过滤）
	 * @param resp
	 * @param scenicName   景区名称（景区名称必须存在于数据库中）
	 * @param visitTime 游客的参观时间
	 * @param visitNum 参观的人数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAvailableGuides.do")
	@ResponseBody
	public Object getAvailableGuides(HttpServletResponse resp,
			@RequestParam("scenicName") String scenicName, 
			@RequestParam("visitTime") String visitTime,
			@RequestParam("visitNum") String visitNum) throws IOException{
		//scenicName=秦始皇兵马俑&visitTime=2017-1-13 8:30&visitNum=5
		CommonResp.SetUtf(resp);
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		//根据scenicName，查找相应的景区信息
		ScenicsSpotInfo scenicsSpotInfo = scenicSpotService.SearchScenicInfoByName_Service(scenicName);
		String scenicID = scenicsSpotInfo.getScenicNo();
		
		if(scenicID != null){
			listResult = guideService.getAvailableGuides(visitTime, Integer.parseInt(visitNum), scenicID);
		}
		
		return listResult;  //若返回值为空，则景区名称有误或者没有符合条件的讲解员
	}
	
	
	
	/**
	 * 按用户的筛选条件，查询相应的讲解员信息
	 * @param resp
	 * @param scenicName  景区名称
	 * @param visitTime  参观时间
	 * @param visitNum   参观人数
	 * @param sex   筛选讲解员的性别  （男，女）
	 * @param age   年龄  （20-30，30-40，40-50）
	 * @param language  讲解语言 （0，1）
	 * @param level  级别   （1，2，3，4，5，6，7）
	 * @return 符合条件的讲解员的信息
	 * phone,image,name,sex,age,language,selfIntro,guideLevel
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAvailableGuidesWithSelector.do")
	@ResponseBody
	public Object getAvailableGuidesWithSelector(HttpServletResponse resp,
			@RequestParam("scenicName") String scenicName, 
			@RequestParam("visitTime") String visitTime,
			@RequestParam("visitNum") String visitNum,
			@RequestParam("sex") String sex,
			@RequestParam("age") String age,
			@RequestParam("language") String language,
			@RequestParam("level") String level) throws IOException{
		//scenicName=秦始皇兵马俑&visitTime=2017-3-23 16:00&visitNum=15&sex=null&age=null&language=null&level=null
		CommonResp.SetUtf(resp);
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		//根据scenicName，查找相应的景区信息
		ScenicsSpotInfo scenicsSpotInfo = scenicSpotService.SearchScenicInfoByName_Service(scenicName);
		String scenicID = scenicsSpotInfo.getScenicNo();
		
		if(scenicID != null){
			listResult = guideService.getAvailableGuidesWithSelector(visitTime, Integer.parseInt(visitNum),
					scenicID, sex, age, language, level);
		}
		
		return listResult;
	}
	
	
	/**
	 * 根据手机号，查询导游的详细信息
	 * @param resp
	 * @param guidePhone  手机号
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getDetailGuideInfoByPhone.do")
	@ResponseBody
	public Object getDetailGuideInfoByPhone(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone) throws IOException{
		
		CommonResp.SetUtf(resp);
		
		List<Map<String , Object>> listResult = new ArrayList<>();
		
		listResult = guideService.getDetailGuideInfoByPhone(guidePhone);
		
		return listResult;
	}
	
	
	/**
	 * 判断该讲解员已经被预约了的时间(被预约了的时间要大于当前时间)与time是否冲突
	 * 若有冲突，则返回数据；否则，查询结果为空
	 * @param guidePhone  讲解员的手机号
	 * @param visitTime  游客的预约参观时间
	 * @return
	 */
	@RequestMapping(value = "/isTimeConflict.do")
	@ResponseBody
	public Object isTimeConflict(HttpServletResponse resp,
			@RequestParam("guidePhone") String guidePhone,
			@RequestParam("visitTime") String visitTime) throws IOException{
		
		CommonResp.SetUtf(resp);		
		
		boolean bool = guideService.isTimeConflict(guidePhone, visitTime);
		
		return bool;
	}
}




/**
 * 该导游在该天，是否被预约了
 * @param resp
 * @param guidePhone   导游的手机号
 * @param visitTime  游览时间
 * @return   1-被预约了    0-未被预约
 * @throws IOException
 */
//@RequestMapping(value = "/WhetherBooked.do")
//@ResponseBody
//public Object WhetherBooked(HttpServletResponse resp,
//		@RequestParam("guidePhone") String guidePhone, 
//		@RequestParam("visitTime") String visitTime) throws IOException{
//	
//	CommonResp.SetUtf(resp);
//	
//	int booked = 0;
//	
//	String[] date = visitTime.toString().split(" ");
//	String visitDate = date[0];
//	
//	booked = guideService.WhetherBooked(guidePhone, visitDate);
//	
//	return booked;
//}
