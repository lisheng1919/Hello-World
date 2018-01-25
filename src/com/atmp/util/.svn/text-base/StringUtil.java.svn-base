package com.atmp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;


public class StringUtil {
	
	private static final Logger logger= Logger.getLogger(StringUtil.class);
	
	/**
	 * 判断一个String对象是否为null，为null返回""，否则返回str自身。
	 * @param str            要判断的String对象 
	 * @return str自身或""
	 */
	public static String getPropertyValue(Object str) {
          return str==null?"":str.toString();
	}
	
	 /**
	  * 创建文件
	  * @param fileName
	  * @return
	  */
	 public static boolean createFile(String fileName)throws Exception{
	  boolean flag=false;
	  try{
	   File file = new File(fileName);
	   if(!file.exists()){
		   file.createNewFile();
		   flag=true;
	   }
	  }catch(Exception e){
		  logger.error(e.getMessage(),e);
	  }
	  return true;
	 } 
	
	 public static boolean writeTxtFile(String content,String  name)throws Exception{
		 
	  boolean flag=false;
	  File fileName = new File(name);
	  FileOutputStream o=null;
	  try {
	   o = new FileOutputStream(fileName);
	      o.write(content.getBytes("GBK"));
	      o.close();
	   flag=true;
	  } catch (Exception e) {
		  logger.error(e.getMessage(),e);
	  }finally{
	   
	  }
	  return flag;
	 }

	  /**  
	     * 追加文件：使用FileWriter  
	     *   
	     * @param fileName  
	     * @param content  
	     */  
	    public static void writeFileAppend(String fileName, String content) { 
	    	FileWriter writer = null;
	        try {   
	            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件   
	            writer = new FileWriter(fileName, true);   
	            writer.write(content);     
	        } catch (IOException e) {   
	        		logger.error(e.getMessage(),e);
	        } finally {   
	            try {   
	            	if(writer != null){
	            		writer.close();   
	            	}
	            } catch (IOException e) {   
	            	logger.error(e.getMessage(),e);  
	            }   
	        } 
	    }   
}
