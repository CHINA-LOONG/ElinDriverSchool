package com.elin.elindriverschool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: KingLone
 * @��   ˵   ��:	
 * @version 1.0
 * @����ʱ�䣺2015-11-8 ����2:03:24
 * 
 */
public class MyDateUtils {

	   private static SimpleDateFormat sf = null;
	/*获取系统时间 格式为："yyyy/MM/dd "*/
	    public static String getCurrentDateTime() {
	        Date d = new Date();
	         sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        return sf.format(d);
	    }
	    
	       /**
         * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14 16:09:00"）
         * 
         * @param time
         * @return
         */
        public static String getStringToDate(String time) {
                SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                @SuppressWarnings("unused")
                long lcc = Long.valueOf(time);
                int i = Integer.parseInt(time);
                String times = sdr.format(new Date(i * 1000L));
                return times;
        }

	/*将字符串转为时间戳*/
	    public static String getDateToString(String time) {
	        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
			String times = null;
	        try{
	            date = sf.parse(time);
	        	long l = date.getTime();
				String stf = String.valueOf(l);
				times = stf.substring(0,10);
			} catch(ParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return times;
	    }
}
