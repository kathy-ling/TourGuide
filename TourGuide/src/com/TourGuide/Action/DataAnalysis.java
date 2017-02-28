package com.TourGuide.Action;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.TourGuide.model.WeixinUserList;
import com.TourGuide.weixin.util.TokenUtil;
import com.TourGuide.weixin.util.UserListUtil;


@Controller
@SessionAttributes("adminSession")
@RequestMapping(value="DataAnalysis")
public class DataAnalysis {
	
	public Object getAttentionPerson() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String accessToken = TokenUtil.getToken().getAccessToken();

		WeixinUserList weixinUserList = UserListUtil.getUserList(accessToken,
				"");

		for (int i = 0; i < weixinUserList.getOpenIdList().size(); i++) {

			String openID = weixinUserList.getOpenIdList().get(i);
			String time = UserListUtil.getSubscribeTime(accessToken, openID);

			System.out.println(openID + ":"
					+ format.format(Long.parseLong(time) * 1000L));
		}
		
		return null;
	}

}
