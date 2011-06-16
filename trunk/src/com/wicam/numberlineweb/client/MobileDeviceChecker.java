package com.wicam.numberlineweb.client;

import com.google.gwt.user.client.Window;

public class MobileDeviceChecker {
	
    static final String[] MOBILE_SPECIFIC_SUBSTRING = {  
        "iPhone","Android","MIDP","Opera Mobi",  
        "Opera Mini","BlackBerry","HP iPAQ","IEMobile",  
        "MSIEMobile","Windows Phone","HTC","LG",  
        "MOT","Nokia","Symbian","Fennec",  
        "Maemo","Tear","Midori","armv",  
        "Windows CE","WindowsCE","Smartphone","240x320",  
        "176x220","320x320","160x160","webOS",  
        "Palm","Sagem","Samsung","SGH",  
        "SIE","SonyEricsson","MMP","UCWEB"};  
    

  
    
  public static boolean checkMobile() {  
        String userAgent = Window.Navigator.getUserAgent();  
       for (String mobile: MOBILE_SPECIFIC_SUBSTRING){  
             if (userAgent.contains(mobile)  
               || userAgent.contains(mobile.toUpperCase())  
               || userAgent.contains(mobile.toLowerCase())){  
                    return true;  
            }  
       }  
  
       return false;  
  }  

}
