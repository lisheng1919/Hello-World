package com.atmp.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.atmp.util.StringUtil;

public class ImgHandle {
	
	private static final Logger logger= Logger.getLogger(ImgHandle.class);
	
	
	public Map<String, Object> getImgHandle(Map<String, Object> map){
		Map<String ,Object> insertMap= new HashMap<String ,Object>();
		BASE64Decoder decoder=new sun.misc.BASE64Decoder();
		String logoPathDir=StringUtil.getPropertyValue(map.get("logoPathDir"));//图像存储路径
		String image1=StringUtil.getPropertyValue(map.get("image1"));//正面图像
		String image2=StringUtil.getPropertyValue(map.get("image2"));//反面图像
		String image3=StringUtil.getPropertyValue(map.get("image3"));//凭证图像
		
		String path1="";
		String path2="";
		String path3="";
		String fileName1=StringUtil.getPropertyValue(map.get("image1name"));
		String fileName2=StringUtil.getPropertyValue(map.get("image2name"));
		String fileName3=StringUtil.getPropertyValue(map.get("image3name"));
		byte[] bt1=new byte[20000];
		byte[] bt2=new byte[20000];
		byte[] bt3=new byte[20000];
		
		if(StringUtils.isNotEmpty(fileName1)){
			fileName1=fileName1.replaceAll("\\\\", "/");
			fileName1=fileName1.replaceAll("\\?", "/");
			fileName1=fileName1.substring(fileName1.lastIndexOf("/")+1,fileName1.lastIndexOf("."));
			insertMap.put("FrontImage", fileName1+".jpg");
			insertMap.put("FrontImagePath", logoPathDir);
		}else{
			insertMap.put("FrontImage", "");
			insertMap.put("FrontImagePath", "");
		}
		if(StringUtils.isNotEmpty(fileName2)){
			fileName2=fileName2.replaceAll("\\\\", "/");
			fileName2=fileName2.replaceAll("\\?", "/");
			fileName2=fileName2.substring(fileName2.lastIndexOf("/")+1,fileName2.lastIndexOf("."));
			insertMap.put("BackImage", fileName2+".jpg");
			insertMap.put("BackImagePath", logoPathDir);
		}else{
			insertMap.put("BackImage", "");
			insertMap.put("BackImagePath", "");
		}
		if(StringUtils.isNotEmpty(fileName3)){
			fileName3=fileName3.replaceAll("\\\\", "/");
			fileName3=fileName3.replaceAll("\\?", "/");
			fileName3=fileName3.substring(fileName3.lastIndexOf("/")+1,fileName3.lastIndexOf("."));
			insertMap.put("VoucherImage", fileName3+".jpg");
			insertMap.put("VoucherImagePath", logoPathDir);
		}else{
			insertMap.put("VoucherImage", "");
			insertMap.put("VoucherImagePath","");
		}
		try{
			if(StringUtils.isNotEmpty(fileName1)){
				bt1=decoder.decodeBuffer(image1);
				// 调整异常数据
				for(int i=0;i<bt1.length;i++){
					if(bt1[i]<0){
						bt1[i]+=256;
					}
				}
				
				File dir = new File(logoPathDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (!image1.equals("") && image1 != null) {
					path1 = dir + "/"
							+ fileName1;
				}
				OutputStream fos = new FileOutputStream(
						new File(path1 + ".jpg"), false);
				
				fos.write(bt1);
				fos.flush();  
				fos.close();
				
			}
			if(StringUtils.isNotEmpty(fileName2)){
				bt2=decoder.decodeBuffer(image2);
				// 调整异常数据
				for(int i=0;i<bt2.length;i++){
					if(bt2[i]<0){
						bt2[i]+=256;
					}
				}
				File dir = new File(logoPathDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (!image2.equals("") && image2 != null) {
					path2 = dir + "/"
							+ fileName2;
				}
				
				OutputStream fos = new FileOutputStream(
						new File(path2 + ".jpg"), false);
				
				fos.write(bt2);
				fos.flush();
				fos.close();
				
			}
			
			
			if(StringUtils.isNotEmpty(fileName3)){
				bt3=decoder.decodeBuffer(image3);
				// 调整异常数据
				for(int i=0;i<bt3.length;i++){
					if(bt3[i]<0){
						bt3[i]+=256;
					}
				}
				File dir = new File(logoPathDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (!image3.equals("") && image3 != null) {
					path3 = dir + "/"
							+ fileName3;
				}
				OutputStream fos = new FileOutputStream(
						new File(path3 + ".jpg"), false);
				
				fos.write(bt3);
				fos.flush();
				fos.close();
				
			}
			insertMap.put("IamgeSendState","1");
			insertMap.put("IamgeSendRemark","");
		}catch(FileNotFoundException e){
			insertMap.put("IamgeSendState","0");
			logger.error(e.getMessage(),e);
		}catch(IOException e){
			insertMap.put("IamgeSendState","0");
			logger.error(e.getMessage(),e);
		}
	  return insertMap;
	}
}
