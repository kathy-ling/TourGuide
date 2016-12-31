package com.TourGuide.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {
	
	/**
	 * 将日期格式化为 yyyy-MM-dd HH:mm:ss 的形式
	 * @param date
	 * @return
	 */
	public static String form(Date date){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = dateFormat.format(date);
		return dateString;
	}

}
