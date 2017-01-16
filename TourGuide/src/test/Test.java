package test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


 

public class Test { 
	
    /** 
     * 测试 
     * @param args 
     * @throws Exception 
     */  
	
    public static void main(String[] args) throws Exception {
    	
//    	Calendar calendar = Calendar.getInstance();
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    	calendar.add(Calendar.DAY_OF_YEAR, 2);
//    	Date date = calendar.getTime();
//    	System.out.println(sdf.format(date));
    	Date date1=new Date();
//    	String day1=new SimpleDateFormat("yyyy-MM-dd").format(date1);
//    	
//    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    	
//    	Date date = dateFormat.parse("2017-1-11");
//    	Date date2 = dateFormat.parse(day1);
//    	
//    	long day = (date.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
//
    	String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	System.out.println(timeNow);
    	
    	
    }  
	
}
