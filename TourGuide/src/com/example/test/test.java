package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.expression.ParseException;

import com.TourGuide.common.DateConvert;
import com.TourGuide.model.WeixinOauth2Token;
import com.TourGuide.weixin.util.Oauth2Util;

public class test {
	
	

	public static void main(String[] args) throws java.text.ParseException {
		
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date=null;
//		try {
//		date = sdf.parse("2012-07-25 21:00:00");
//		} catch (ParseException e) {
//		// TODO 自动生成 catch 块
//		e.printStackTrace();
//		}
//		Calendar ca=Calendar.getInstance();
//		ca.setTime(date);
//		ca.add(Calendar.HOUR_OF_DAY, -3);
//		String time = sdf.format(ca.getTime());
<<<<<<< HEAD
		System.out.println(DateConvert.addHourToTime("2012-7-25 12:00:00", -3));
=======
		System.out.println(Oauth2Util.getOauth2Url());
>>>>>>> 2a27099552508e47d9105293bcfb90d0ee89063c
	}
	
	/**
	 * 比较两个字符串格式日期的大小
	 * @param dateFrom  开始日期: yyyy-MM-dd
	 * @param dateTo  结束日期: yyyy-MM-dd
	 * @return  true: dateFrom <= dateTo 
	 */
	public static Boolean DateCompare(String dateFrom, String dateTo) {
		Boolean isTrue = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			java.util.Date df = sdf.parse(dateFrom);
			java.util.Date dt = sdf.parse(dateTo);
			if(df.before(dt)) {
				isTrue = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return isTrue;
	}

}
