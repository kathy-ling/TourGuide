package com.TourGuide.Action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.TourGuide.service.ScenicTeamService;
import com.TourGuide.web.Service.PromotionInfoService;
import com.TourGuide.web.model.PromotionInfo;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="PromotionInfo")
@SessionAttributes("adminSession")
public class PromotionInfoAction {
	
	@Autowired
	private PromotionInfoService promotionInfoService;
	
	@Autowired
	private ScenicTeamService scenicTeamService;
	
	
	private String filename; 
	/**
	 * 分页得到所有景区活动信息
	 * @param resp
	 * @param currentPage
	 * @param pageRows
	 * @return
	 * @throws IOException
	 * 2017-3-28 19:15:16
	 */
	@RequestMapping(value="/getPromotionInfo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object getPromotionInfoBypage(HttpServletResponse resp,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows ) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		List<PromotionInfo> list=promotionInfoService.getPromotionInfoBypage(currentPage, pageRows);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		int i=promotionInfoService.getProCount();
		map.put("jsonStr", jsonStr);
		map.put("page", currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	
	
	@RequestMapping(value="/getPromotionInfoByscenicNo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object getPromotionInfoByscenicNo(HttpServletResponse resp,
			HttpSession session,
			@RequestParam(value="currentPage")int currentPage,
			@RequestParam(value="pageRows")int pageRows,
			@RequestParam(value="scenicNo")String scenicNo) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		
		AdminInfo adminInfo=(AdminInfo) session.getAttribute("adminSession");
		if (scenicNo.equals("aaa")) {
			scenicNo=scenicTeamService.getScenicNoByAccount(adminInfo.getUsername());
		}
		List<PromotionInfo> list=promotionInfoService.getPromotionInfoByscenicNo(currentPage, pageRows, scenicNo);
		Map< String , Object> map=new HashMap<>();
		String jsonStr=new Gson().toJson(list).toString();
		int i=promotionInfoService.getProByscenicNoCount(scenicNo);
		map.put("jsonStr", jsonStr);
		map.put("page",    currentPage);
		map.put("total", (int)(i%pageRows==0? i/pageRows:i/pageRows + 1));
		return map;
	}
	
	@RequestMapping(value="/UpdateMainShow.action",method=RequestMethod.POST)
	@ResponseBody
	public Object UpdateMainShow(HttpServletResponse resp,
			@RequestParam(value="mainShow")String  mainShow,
			@RequestParam(value="proID")String proID) throws IOException
	{
		
		CommonResp.SetUtf(resp);
		int i=promotionInfoService.UpdateMainShow(proID, mainShow);
		return i;
	}
	
	
	
	/**
	 * 上传景区活动图片
	 * @param resp
	 * @param request
	 * @param file
	 * @return
	 * 
	 * 2017-1-15 15:52:26
	 */
	@RequestMapping(value="/UploadProImage.action",method=RequestMethod.POST)
	@ResponseBody
	public Object UploadImage(HttpServletResponse resp,HttpServletRequest request,
			@RequestParam MultipartFile file) {
		
		CommonResp.SetUtf(resp);
		Map<String , Object> map=new HashMap<>();
		String realPath=request.getSession().getServletContext().getRealPath("image/promotions");
		File pathFile = new File(realPath);
		if (!pathFile.exists()) {
			//文件夹不存 创建文件
			System.out.println("目录不存在，创建目录");
			pathFile.mkdirs();
		}
		System.out.println("文件类型："+file.getContentType());
		System.out.println("文件名称："+file.getOriginalFilename());
		System.out.println("文件大小:"+file.getSize());
		System.out.println(".................................................");
		
		
		String scenicImage=file.getOriginalFilename();
		String[] strs=scenicImage.split("\\.");
		scenicImage=promotionInfoService.getBookOrderID()+"."+strs[strs.length-1];
		filename="/image/promotions/"+scenicImage;
			//将文件copy上传到服务器
		try {
			System.out.println(realPath + "/" + scenicImage);
			File fileImageFile=new File(realPath + "/" + scenicImage);
			file.transferTo(fileImageFile);
			System.out.println("图片上传成功");
			map.put("json", "true");
		} catch (IllegalStateException | IOException e) {
				
			e.printStackTrace();
			map.put("json", "false");
		}	
		return  map;
	}  
	
	
	
	/**
	 * 增加活动信息
	 * @param resp
	 * @param request
	 * @param session
	 * @param proTitle
	 * @param proStart
	 * @param proEnd
	 * @param proContext
	 * @return
	 * @throws IOException
	 * 2017-3-30 21:46:41
	 */
	@RequestMapping(value="/AddProInfo.action",method=RequestMethod.POST)
	@ResponseBody
	public Object AddScenicInfo(HttpServletResponse resp,HttpServletRequest request,
			HttpSession session,
			@RequestParam(value="proTitle")String proTitle,@RequestParam(value="proStart")String proStart,
			@RequestParam(value="proEnd")String proEnd,
			@RequestParam(value="proText")String proContext,
			@RequestParam(value="scenicName")String scenicName
			) throws IOException {
		
		CommonResp.SetUtf(resp);
		
		
		String proID=promotionInfoService.getPromotionID();
		String ProrealPath=request.getSession().getServletContext().getRealPath("WEB-INF/HtmlPath/SourceHtml")+"/"+proID+".html";
		String HtmlrealPath=request.getSession().getServletContext().getRealPath("WEB-INF/HtmlPath/MyHtml.html");
		
		String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + 
							request.getServerPort()+  request.getContextPath() + "/";
		AdminInfo adminInfo=(AdminInfo) session.getAttribute("adminSession");
		String scenicNo=scenicTeamService.getScenicNoByAccount(adminInfo.getUsername());
		
		
		Date date=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		PromotionInfo promotionInfo=new PromotionInfo();
		
		promotionInfo.setProID(proID);
		promotionInfo.setProTitle(proTitle);
		promotionInfo.setProImage(filename);
		promotionInfo.setProLink("正在美工");
		promotionInfo.setProStartTime(proStart);
		promotionInfo.setProEndTime(proEnd);
		promotionInfo.setProProduceTime(formatter.format(date));
		promotionInfo.setScenicNo(scenicNo);
		promotionInfo.setScenicName(scenicName);
		promotionInfo.setIsMainShow("0");
		promotionInfo.setIsAdmin("0");
		promotionInfo.setProContext(proContext);
		promotionInfo.setHtmlPath(ProrealPath);
		int confirm=0;
		boolean a=promotionInfoService.JspToHtmlFile(basePath, HtmlrealPath, ProrealPath, promotionInfo);
		
		if (a) {
			confirm=promotionInfoService.InsetProInfo(promotionInfo);
		} 
		Map<String, Object> map = new HashMap<>();
		map.put("confirm", confirm);
		return map;
	}

}
