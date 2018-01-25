package com.atmp.system;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


import sun.misc.BASE64Decoder;


public class DriverManagerConnection extends PropertyPlaceholderConfigurer{

	public DriverManagerConnection() {
		
	}
	private static final Log logger = LogFactory.getLog(DriverManagerConnection.class);
	
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
			return "atmp";
		}
		return des;
	}
//	public static void main(String args[]){
//		BASE64Encoder dd=new BASE64Encoder();
//		System.out.println(dd.encodeBuffer("zzjj".getBytes()));
//		System.out.println(dd.encodeBuffer("abcd1234".getBytes()));
//		
//	}
}