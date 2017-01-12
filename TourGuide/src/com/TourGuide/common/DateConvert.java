package com.TourGuide.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert {
	
	/**
	 * 将TimeStamp类型的时间转换为yyyy-MM-dd HH:mm格式的日期类型
	 * @param timestamp 时间
	 * @return DateTimeString: yyyy-MM-dd HH:mm
	 */
	public static String timeStamp2DateTime(Timestamp timestamp) {
		
		String datetime = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			datetime = df.format(timestamp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return datetime;
	}
	
	
	/**
	 * 计算两个字符串类型的日期间的天数
	 * @param str1     日期1，字符串类型
	 * @param str2   日期2，字符串类型
	 * @return
	 */
	public static int getDaysBetweenDate(String str1, String str2){
		
		long day = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		
		try {
			date1 = dateFormat.parse(str1);
			date2 = dateFormat.parse(str2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        	
    	day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		return (int)day;
	}
}
