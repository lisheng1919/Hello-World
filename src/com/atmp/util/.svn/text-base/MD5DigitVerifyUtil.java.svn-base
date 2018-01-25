package com.atmp.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;



public class MD5DigitVerifyUtil {
	
	public static final String MD5_SIGN_NAME_VALUE="zk_1234";
	private static final Logger logger= Logger.getLogger(MD5DigitVerifyUtil.class);
	
	@SuppressWarnings("unchecked")
	public boolean checkMD5DigitVerify(Map<String, Object> map) throws Exception{
		boolean is_check_same=false;
		try {
			  List<String> list=new ArrayList();
			  for(Iterator it=map.entrySet().iterator();it.hasNext();){
				  Map.Entry<String,String> entry=(Entry) it.next();
				  String key=entry.getKey();
				  list.add(key);
				 
			  }
			  String all_str="";
			  for (String object : list) {
				  String key=object.toString();
				  String value=map.get(key).toString();
				  if(key.equals("s")||key.equals("timestamp")||key.equals("image1")||key.equals("image2")||key.equals("image3")||key.equals("image4")){
					  continue;
				  }
				  key=key.toLowerCase();
				  all_str+=key+"="+value+"&";
			  }
			  String md5_value=MD5Util.string2MD5(all_str+MD5_SIGN_NAME_VALUE);
			  String s=map.get("s").toString();
			  is_check_same=md5_value.equals(s);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	    return is_check_same;
	}
	
	
}
