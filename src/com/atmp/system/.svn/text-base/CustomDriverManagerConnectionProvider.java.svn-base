package com.atmp.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


import sun.misc.BASE64Decoder;


public class CustomDriverManagerConnectionProvider extends PropertyPlaceholderConfigurer{

	public CustomDriverManagerConnectionProvider() {
		
	}
	private static Map<String, Object> map;
	private static final Logger logger=Logger.getLogger(CustomDriverManagerConnectionProvider.class);
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		String originalUsername = props.getProperty("user");
		String originalPassword = props.getProperty("password");
		if (originalUsername != null){
			String newUsername = deEncryptUsername(originalUsername);
			props.put("user", newUsername);
		}
		if (originalPassword != null){
			String newPassword = deEncryptPassword(originalPassword);
			props.put("password", newPassword);
		}
		map = new HashMap<String, Object>(); 
        for (Object key : props.keySet()) { 
            String keyStr = key.toString(); 
            String value = props.getProperty(keyStr); 
            map.put(keyStr, value); 
        }
		super.processProperties(beanFactoryToProcess, props);
	}
	private String deEncryptUsername(String originalUsername){
		return deEncryptString(originalUsername);
	}
	
	private String deEncryptPassword(String originalPassword){
		return deEncryptString(originalPassword);
	}

	private String deEncryptString(String originalString){
		BASE64Decoder d64=new BASE64Decoder();
		String des=null; 
		try {
			des=new String(d64.decodeBuffer(originalString));
		} catch (IOException e) {
			logger.error(e.toString(), e);
			return "shyh";
		}
		return des;
	}
	
	public  String getContextProperty(String key) { 
		String result=(String) map.get(key);
        return result; 
    }
}