package com.atmp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.atmp.service.ImageReceiveInterface;
import com.atmp.system.CustomDriverManagerConnectionProvider;
import com.atmp.util.HttpJsonResult;
import com.atmp.util.MD5DigitVerifyUtil;
import com.atmp.util.StringUtil;

@Controller
public class ImageReceiveController {
	
	private static final Logger logger=Logger.getLogger(ImageReceiveController.class);
	CustomDriverManagerConnectionProvider customConfigInfo=new CustomDriverManagerConnectionProvider();
	private String btmlogpath =customConfigInfo.getContextProperty("btmlogpath");
	
	@Resource
	private ImageReceiveInterface imageReceive;
	
	/**
	 * 业务受理
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/image/imageReceive")
	@ResponseBody
	public void imageReceive(HttpServletRequest request,HttpServletResponse response){
		Map<String ,Object> result=new HashMap<String, Object>();
		
		Map<String, Object> map=new TreeMap<String, Object>();
		Enumeration arg_array=request.getParameterNames();
		while(arg_array.hasMoreElements()){
			String arg_key=arg_array.nextElement().toString();
			String value=StringUtil.getPropertyValue(request.getParameter(arg_key));
			map.put(arg_key, value);
		}
		MD5DigitVerifyUtil  checkMD5=new MD5DigitVerifyUtil();
		try{
			boolean is_pass_md5_digit=checkMD5.checkMD5DigitVerify(map);
			if(is_pass_md5_digit==true){
					String electType=StringUtil.getPropertyValue(map.get("electType"));
					String MedAcctNo=StringUtil.getPropertyValue(map.get("MedAcctNo"));
					String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
					String CntId=StringUtil.getPropertyValue(map.get("CntId"));
					String CompanyCode=StringUtil.getPropertyValue(map.get("CompanyCode"));
					String JJID=StringUtil.getPropertyValue(map.get("deviceNum"));
					
					if(StringUtils.isNotEmpty(electType)){
						if(StringUtils.isNotEmpty(MedAcctNo)){
							if(StringUtils.isNotEmpty(SystemId)){
								if(StringUtils.isNotEmpty(CntId)){
									if(StringUtils.isNotEmpty(CompanyCode)){
										if(StringUtils.isNotEmpty(JJID)){
											result=imageReceive.addReceiveInfo(map);
										 }else{
											 logger.info("机具ID为空");
											 result.put("StatusCode", "111111");
											 result.put("ServerStatusCode", "机具ID为空");
										 }
									 }else{
										 logger.info("网点号为空");
										 result.put("StatusCode", "111111");
										 result.put("ServerStatusCode", "网点号为空");
									 }
								 }else{
									 logger.info("柜员号为空");
									 result.put("StatusCode", "111111");
									 result.put("ServerStatusCode", "柜员号为空");
								 }
							 }else{
								 logger.info("发起系统号为空");
								 result.put("StatusCode", "111111");
								 result.put("ServerStatusCode", "发起系统号为空");
							 }
						}else{
							logger.info("介质账号为空");
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "介质账号为空");
						}
					}else{
						logger.info("业务类型为空");
						result.put("StatusCode", "111111");
						result.put("ServerStatusCode", "业务类型为空");
					}
			}else{
				logger.info("身份验证失败");
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "身份验证失败");
			}
		}catch(Exception e){
			logger.info("系统异常");
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "系统异常");
		}
		
		HttpJsonResult.writerMap(result,response);
			
	}
	
	/**
	 * 接收机具日志
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/btmlog/btmlogReceive")
	@ResponseBody
	public void getBtmLog(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map=new TreeMap<String, Object>();
		Enumeration arg_array=request.getParameterNames();
		while(arg_array.hasMoreElements()){
			String arg_key=arg_array.nextElement().toString();
			String value=StringUtil.getPropertyValue(request.getParameter(arg_key));
			map.put(arg_key, value);
		}
		String content=StringUtil.getPropertyValue(map.get("content"));
		String deviceNum=StringUtil.getPropertyValue(map.get("deviceNum"));
		String logname=StringUtil.getPropertyValue(map.get("logname"));
		BASE64Decoder d64=new BASE64Decoder();
		try {
			byte[] bytedes=d64.decodeBuffer(content);
			String des=new String(bytedes,"gbk");
			String logPath=btmlogpath+deviceNum+"/";
			File filePath=new File(logPath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			String fileName =logPath+logname;
			try {
				StringUtil.createFile(fileName);
				StringUtil.writeFileAppend(fileName,des);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * OCX版本查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/ocxupdate/btmocxUpdate")
	@ResponseBody
	public void getBtmOcxUpdate(HttpServletRequest request,HttpServletResponse response){
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String, Object> map=new TreeMap<String, Object>();
		String equipmentType=StringUtil.getPropertyValue(request.getParameter("equipmentType"));
			map.put("EquipmentType", equipmentType);
			result=imageReceive.getBtmOcxUpdate(map);
		
		HttpJsonResult.writerMap(result,response);
		
	}
	

}
