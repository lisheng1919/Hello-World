package com.atmp.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atmp.service.AccountInfoInterface;
import com.atmp.util.HttpJsonResult;
import com.atmp.util.MD5DigitVerifyUtil;
import com.atmp.util.MD5Utils;
import com.atmp.util.StringUtil;

@Controller
public class AccountInfoController {
	
	
	private static final Logger logger= Logger.getLogger(AccountInfoController.class);
	
	@Resource
	private AccountInfoInterface accountInfo;
	
	
	/**
	 * 账号登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/accountInfo/getAccountInfo")
	@ResponseBody
	public void checkInfo(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> map=new TreeMap<String, Object>();
		Map<String ,Object> result=new HashMap<String, Object>();
		Enumeration arg_array=request.getParameterNames();
		while(arg_array.hasMoreElements()){
			String arg_key=arg_array.nextElement().toString();
			String value=StringUtil.getPropertyValue(request.getParameter(arg_key));
			map.put(arg_key, value);
		}
		MD5DigitVerifyUtil  checkMD5=new MD5DigitVerifyUtil();
		try {
			boolean is_pass_md5_digit=checkMD5.checkMD5DigitVerify(map);
			if(is_pass_md5_digit==true){
				result=accountInfo.getAccountInfo(map);
			}else{
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "身份校验失败");
			}
		} catch (Exception e) {
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "身份校验失败");
			logger.info("身份校验失败");
		}
		HttpJsonResult.writerMap(result,response);
	}
	
	/**
	 * 回单卡账号信息查询
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/accountCardInfo/getCardInfo")
	@ResponseBody
	public void checkAccCardInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map=new TreeMap<String, Object>();
		Map<String ,Object> result=new HashMap<String, Object>();
		Enumeration arg_array=request.getParameterNames();
		while(arg_array.hasMoreElements()){
			String arg_key=arg_array.nextElement().toString();
			String value=StringUtil.getPropertyValue(request.getParameter(arg_key));
			map.put(arg_key, value);
		}
		result=accountInfo.getAccCardInfo(map);
		HttpJsonResult.writerMap(result,response);
	}
	
	/**
	 * 查询指定账号的电话号码
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/accountCardPhone/getCardPhone")
	@ResponseBody
	public void getCardPhone(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> map=new TreeMap<String, Object>();
		Map<String ,Object> result=new HashMap<String, Object>();
		Enumeration arg_array=request.getParameterNames();
		while(arg_array.hasMoreElements()){
			String arg_key=arg_array.nextElement().toString();
			String value=StringUtil.getPropertyValue(request.getParameter(arg_key));
			map.put(arg_key, value);
		}
				result=accountInfo.getCardPhone(map);
		HttpJsonResult.writerMap(result,response);
	}
	/**
	 * 查询指凭证号的支付指令
	 */
	@RequestMapping(value="/accountInfo/getAccountInfoByVor")
	@ResponseBody
	public void getAccountInfoByVor(HttpServletRequest req,HttpServletResponse res){
		Map<String ,Object> result=new HashMap<String, Object>();
		String draweeAcctNo = req.getParameter("inputAccount");
		String payVoucherNo = req.getParameter("vorcherno");
		String transferType = "1";
		String SystemId=req.getParameter("SystemId");
		String CntId=req.getParameter("CntId");
		String TransCode="com.all.sinodata.vouMulInq";
		Map<String,String> map = new HashMap<String,String>();
		map.put("draweeAcctNo", draweeAcctNo);
		map.put("payVoucherNo", payVoucherNo);
		map.put("transferType", transferType);
		map.put("SystemId", SystemId);
		map.put("CntId", CntId);
		map.put("TransCode", TransCode);
		result = accountInfo.returnAcctByVoucherNo(map);
		HttpJsonResult.writerMap(result,res);
	}
	/**
	 * 根据密码查询用户信息
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/accountInfo/getAccountInfoByPwd")
	@ResponseBody
	public void getAccountInfoByPwd(HttpServletRequest req,HttpServletResponse res){
		Map<String ,Object> result=new HashMap<String, Object>();
		String SystemId=req.getParameter("SystemId");
		String CntId=req.getParameter("CntId");
		String TransCode="com.all.sinodata.QAI";
		String MedAcctNo=req.getParameter("acctNo");
		String Pwd=req.getParameter("pwd");
		String PassWd = MD5Utils.MD5Encode(Pwd);
		Map<String,String> map = new HashMap<String,String>();
		map.put("SystemId", SystemId);
		map.put("CntId", CntId);
		map.put("TransCode", TransCode);
		map.put("MedAcctNo", MedAcctNo);
		map.put("PassWd", PassWd);
		result = accountInfo.returnAcctByPwd(map);
		HttpJsonResult.writerMap(result,res);
	}
	/**
	 * 交易明细查询
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/accountInfo/QueryDetail")
	@ResponseBody
	public void QueryDetail(HttpServletRequest req,HttpServletResponse res){
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("SystemId", req.getParameter("SystemId"));
		map.put("CntId", req.getParameter("CntId"));
		map.put("TransCode", "com.all.sinodata.INQ");
		map.put("voucherType", "074001");
		map.put("sealPwd", req.getParameter("sealPwd"));
		map.put("currency", req.getParameter("currency"));
		result = accountInfo.queryDetailData(map);
		HttpJsonResult.writerMap(result,res);
	}
}
	
