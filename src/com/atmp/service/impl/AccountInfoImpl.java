package com.atmp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.atmp.bean.ResultBean;
import com.atmp.dao.HandleMesDao;
import com.atmp.message.CreateMessage;
import com.atmp.service.AccountInfoInterface;
import com.atmp.util.StringUtil;

@Service("accountInfo")
public class AccountInfoImpl implements AccountInfoInterface{

	private static final Logger logger= Logger.getLogger(AccountInfoImpl.class);
	
	CreateMessage createMessage=new CreateMessage();
	@Resource
	private  HandleMesDao baseDao; 
	/**
	 * 账号信息查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> getAccountInfo(Map<String, Object> map){
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> queryMap=new HashMap<String, Object>();
		
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String TransCode="com.all.sinodata.QAI";
		String MedAcctNo=StringUtil.getPropertyValue(map.get("MedAcctNo"));
		logger.info("******************账号信息查询开始******************");
		if(StringUtils.isNotEmpty(MedAcctNo)){
			if(StringUtils.isNotEmpty(SystemId)){
				if(StringUtils.isNotEmpty(CntId)){
						mHead.put("SystemId", SystemId);
						mHead.put("CntId", CntId);
						mHead.put("TransCode",TransCode);
						mBody.put("MedAcctNo", MedAcctNo);
						try{
							String logName="账号信息查询";
							result=createMessage.cMessage(mHead, mBody,logName);
							String strAcctName=StringUtil.getPropertyValue(result.get("AcctName"));
							queryMap=baseDao.queryMap("select * from p_accinfo where MedAcctNo='"+MedAcctNo+"' ");
							if(queryMap==null||queryMap.size()==0){
								baseDao.insertMapTable("insert into p_accinfo (MedAcctNo,AcctName) values ('"+MedAcctNo+"','"+strAcctName+"')");
								result.put("cellPhone", "");
							}else if(queryMap.size()>0){
								baseDao.insertMapTable("update p_accinfo set AcctName='"+strAcctName+"' where MedAcctNo='"+MedAcctNo+"'");
								result.put("cellPhone", StringUtil.getPropertyValue(queryMap.get("cellPhone")));
							}
						}catch(Exception e){
							logger.error(e.getMessage(),e);
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
						}
					}else{
						logger.info("网点号为空");
						result.put("StatusCode", "111111");
						result.put("ServerStatusCode", "网点号为空");
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
		logger.info("******************账号信息查询结束******************");
		return result;
	}
	
	/**
	 * 回单卡账号信息查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> getAccCardInfo(Map<String, Object> map) {
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String TransCode="com.all.sinodata.C40QueAccCardInqRq";
		String CardCode=StringUtil.getPropertyValue(map.get("CardCode"));
		logger.info("******************回单卡账号信息查询开始******************");
		if(StringUtils.isNotEmpty(CardCode)){
			if(StringUtils.isNotEmpty(SystemId)){
				if(StringUtils.isNotEmpty(CntId)){
						mHead.put("SystemId", SystemId);
						mHead.put("CntId", CntId);
						mHead.put("TransCode",TransCode);
						mBody.put("CardCode", CardCode);
						try{
							logger.info("开始查询回单卡号信息");
							String logName="回单卡号信息查询";
							result=createMessage.cCardMessage(mHead, mBody,logName);
						}catch(Exception e){
							logger.error(e.getMessage(),e);
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
						}
					}else{
						logger.info("网点号为空");
						result.put("StatusCode", "111111");
						result.put("ServerStatusCode", "网点号为空");
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
		logger.info("******************回单卡账号信息查询结束******************");
		return result;
	}
	
	/**
	 * 查询指定账号的电话号码
	 * @param map
	 * @return
	 */
	public Map<String, Object> getCardPhone(Map<String, Object> map){
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String ,Object> queryMap=new HashMap<String, Object>();
		BASE64Decoder d64=new BASE64Decoder();
		logger.info("******************电话号码查询开始******************");
		try{
			String MedAcctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
			String strAcctName=StringUtil.getPropertyValue(map.get("AcctName"));
			byte[] des=d64.decodeBuffer(strAcctName);
			String desAcctName=new String(des,"utf-8");
			queryMap=baseDao.queryMap("select cellPhone from p_accinfo where MedAcctNo='"+MedAcctNo+"' ");
			if(queryMap==null||queryMap.size()==0){
				baseDao.insertMapTable("insert into p_accinfo (MedAcctNo,AcctName) values ('"+MedAcctNo+"','"+desAcctName+"')");
				result.put("shBankPhone", "");
			}else if(queryMap.size()>0){
				baseDao.insertMapTable("update p_accinfo set AcctName='"+desAcctName+"' where MedAcctNo='"+MedAcctNo+"'");
				result.put("shBankPhone",  StringUtil.getPropertyValue(queryMap.get("cellPhone")));
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "系统异常");
		}
		logger.info("******************电话号码查询结束******************");
		return result;
	}
	/**
	 * 支付指令查询（支付凭证号）
	 */
	public Map<String, Object> returnAcctByVoucherNo(Map<String,String> map) {
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String TransCode=StringUtil.getPropertyValue(map.get("TransCode"));
		String draweeAcctNo = StringUtil.getPropertyValue(map.get("inputAccount"));
		String payVoucherNo = StringUtil.getPropertyValue(map.get("vorcherno"));
		String transferType =StringUtil.getPropertyValue(map.get("transferType"));
		logger.info("******************支付指令查询开始******************");
		if(StringUtils.isNotEmpty(payVoucherNo)){
			if(StringUtils.isNotEmpty(draweeAcctNo)){
				mHead.put("SystemId", SystemId);
				mHead.put("CntId", CntId);
				mHead.put("TransCode",TransCode);
				mBody.put("draweeAcctNo", draweeAcctNo);
				mBody.put("payVoucherNo", payVoucherNo);
				mBody.put("transferType", transferType); 
				try{
					String logName="支付指令查询";
					result=createMessage.cMessage(mHead, mBody,logName);
				}catch(Exception e){
					logger.error(e.getMessage(),e);
					result.put("StatusCode", "111111");
					result.put("ServerStatusCode", "系统异常");
				}
			}else{
				logger.info("汇款人账号为空");
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "汇款人账号为空");
			}
		}else{
			logger.info("凭证号为空");
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "凭证号为空");
		}
		logger.info("******************支付指令查询结束******************");
		return result;
	}
	/**
	 *  账户密码信息查询
	 */
	public Map<String, Object> returnAcctByPwd(Map<String,String> map) {
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String TransCode=StringUtil.getPropertyValue(map.get("TransCode"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("MedAcctNo"));
		String PassWd=StringUtil.getPropertyValue(map.get("PassWd"));
		logger.info("******************账户信息密码查询开始******************");
		if(StringUtils.isNotEmpty(MedAcctNo)){
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mBody.put("MedAcctNo", MedAcctNo);
			mBody.put("PassWd", PassWd);
			try{
				String logName="账号信息查询";
				result=createMessage.cMessage(mHead, mBody,logName);
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "系统异常");
			}
		}else{
			logger.info("介质账号为空");
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "介质账号为空");
		}
		logger.info("******************账户信息密码查询结束******************");
		return result;
	}
	/**
	 * 交易明细查询
	 */
	public Map<String, Object> queryDetailData(Map<String,String> map) {
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String TransCode=StringUtil.getPropertyValue(map.get("TransCode"));
		String voucherType=StringUtil.getPropertyValue(map.get("voucherType"));
		String sealPwd=StringUtil.getPropertyValue(map.get("sealPwd"));
		String currency=StringUtil.getPropertyValue(map.get("currency"));
		logger.info("******************交易明细查询开始******************");
		if(StringUtils.isNotEmpty(voucherType)){
			if(StringUtils.isNotEmpty(sealPwd)){
				mHead.put("SystemId", SystemId);
				mHead.put("CntId", CntId);
				mHead.put("TransCode",TransCode);
				mBody.put("voucherType", voucherType);
				mBody.put("sealPwd", sealPwd);
				mBody.put("currency", currency);
				try{
					String logName="交易明细查询";
					result=createMessage.cCardMessage(mHead, mBody,logName);
				}catch(Exception e){
					logger.error(e.getMessage(),e);
					result.put("StatusCode", "111111");
					result.put("ServerStatusCode", "系统异常");
				}
			}else{
				logger.info("印鉴密码标识为空");
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "印鉴密码标识为空");
			}
		}else{
			logger.info("凭证组合码为空");
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "凭证组合码为空");
		}
		logger.info("******************交易明细查询结束******************");
		return result;
	}
	
}
