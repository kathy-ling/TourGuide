package test;

import com.TourGuide.dao.IntroFeeAndMaxNumDao;


 

public class Test { 
	
    /** 
     * 测试 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {
    	
//        Weather weatherInfo = getWeatherByCity.getCityWeather("西安");
//        
//        System.out.println(new Gson().toJson(weatherInfo)); 
    	
    	IntroFeeAndMaxNumDao dao = new IntroFeeAndMaxNumDao();
    	System.out.println(dao.getIntroFee("2016-12-31", "19743"));
    }  
	
}
