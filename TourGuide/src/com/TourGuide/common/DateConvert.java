package com.TourGuide.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
	
}
