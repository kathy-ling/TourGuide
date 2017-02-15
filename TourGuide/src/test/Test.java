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
    	
    	Long subscribeTime = Long.parseLong("1486978338");

        
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 String date = sdf.format(new Date(subscribeTime*1000L));
    	System.out.println(date);
    }  
	
}
