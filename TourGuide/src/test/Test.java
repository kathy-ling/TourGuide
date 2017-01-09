package test;

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
    	
    	Calendar calendar = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	calendar.add(Calendar.DAY_OF_YEAR, 2);
    	Date date = calendar.getTime();
    	System.out.println(sdf.format(date));
    	Date date1=new Date();
    	String day1=new SimpleDateFormat("yyyy-MM-dd").format(date1);
    	System.out.println(day1);
    }  
	
}
