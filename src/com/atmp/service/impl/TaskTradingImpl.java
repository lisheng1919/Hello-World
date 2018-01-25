package com.atmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.atmp.common.UImageUploadFile;
import com.atmp.dao.HandleMesDao;
import com.atmp.message.CreateMessage;
import com.atmp.message.ImgHandle;
import com.atmp.service.TaskTradingInterface;
import com.atmp.util.DateFormat;
import com.atmp.util.StringUtil;

@Service("taskTrading")
public class TaskTradingImpl implements TaskTradingInterface {
	
	private static final Logger logger= Logger.getLogger(TaskTradingImpl.class);
	
	CreateMessage createMessage=new CreateMessage();
	ImgHandle imgHandle=new ImgHandle();
	UImageUploadFile uImageUp=new UImageUploadFile();
	DateFormat dateFormat=new DateFormat();
	
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
		
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("MedAcctNo"));
		String TransCode="com.all.sinodata.QAI";
		
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mBody.put("MedAcctNo", MedAcctNo);
		logger.info("******************业务办理账号查询开始******************");
		try{
			String logName="业务办理账号查询";
			result=createMessage.cMessage(mHead, mBody,logName);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "系统异常");
		}
		logger.info("******************业务办理账号查询结束******************");
		return result;
	}
	
	/**
	 * 汇兑业务
	 * @param map
	 * @return
	 */
	public Map<String, Object> getAgiotageNum(Map<String, Object> map){
		Map<String , Object> result=new HashMap<String , Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> setAMap=new HashMap<String, Object>();
		Map<String ,Object> getAMap=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		
		//报文头信息
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
		String cellPhone =StringUtil.getPropertyValue(map.get("cellPhone"));
		String strID=StringUtil.getPropertyValue(map.get("ID"));
		String TransCode="com.0200.sinodata.exchange";
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mHead.put("cellPhone", cellPhone);
		logger.info("******************汇兑业务"+strID+"办理开始******************");	
			//查询账户，取得相关信息
			setAMap.put("SystemId", SystemId);
			setAMap.put("CntId", CntId);
			setAMap.put("MedAcctNo", MedAcctNo);
			getAMap=this.getAccountInfo(setAMap);
			
			String exstatuscode =StringUtil.getPropertyValue(getAMap.get("StatusCode"));
		if(exstatuscode.equals("000000")){
			String sealPwd =StringUtil.getPropertyValue(getAMap.get("SealPwd"));
			String custId =StringUtil.getPropertyValue(getAMap.get("CustId"));
			String acctName =StringUtil.getPropertyValue(getAMap.get("AcctName"));
			String draweeBankNo =StringUtil.getPropertyValue(getAMap.get("DraweeBankNo"));
			String acctChar =StringUtil.getPropertyValue(getAMap.get("MediumType"));
			String postingRestrict =StringUtil.getPropertyValue(getAMap.get("PostingRestrict"));
			String currency ="CNY";//默认
			
			String voucherType ="020001";//待定
			String draweeAcctNo =StringUtil.getPropertyValue(map.get("AcctNo"));//待定
			String businessType ="0200";//待定
			String TranUid =StringUtil.getPropertyValue(map.get("TranUid"));
			String priority ="1";//1：普通、2：加急。默认普通
			String inactiveMarker =StringUtil.getPropertyValue(map.get("inactiveMarker"));
			String gzs ="0";//公转私默认0
			
			//影像上传
			String imgCode ="";
			String mainPageNum ="1";
			String indexNo ="0001";
			String indexMaxNo ="0002";
			String attachPageNum ="0";
			try{
				Map<String , Object> imageMap=new HashMap<String , Object>();
				imgCode=uImageUp.uploadFile(map);
				imageMap.put("TIamgeSendState","1");
				imageMap.put("fileType",imgCode);
				whereParamMap.put("ID",strID);
					int upstate=baseDao.updateByWhere(imageMap,whereParamMap, "p_imageinfo","and");
					if(upstate==1){
						logger.info("影像上传成功");
						mBody.put("voucherType", voucherType);
						mBody.put("sealPwd", sealPwd);
						mBody.put("currency", currency);
						mBody.put("custId", custId);
						mBody.put("draweeAcctNo", draweeAcctNo);
						mBody.put("acctName",acctName);
						mBody.put("draweeBankNo", draweeBankNo);
						mBody.put("acctChar",acctChar);
						mBody.put("businessType",businessType);
						mBody.put("inactiveMarker", inactiveMarker);
						mBody.put("gzs", gzs);
						mBody.put("postingRestrict", postingRestrict);
						
						mBody.put("imgCode", imgCode);
						mBody.put("mainPageNum",mainPageNum);
						mBody.put("indexNo", indexNo);
						mBody.put("indexMaxNo", indexMaxNo);
						mBody.put("attachPageNum", attachPageNum);
						
						mBody.put("TranUid", TranUid);
						mBody.put("priority", priority);
						String logName="汇兑业务";
						result=createMessage.cMessage(mHead, mBody,logName);
						String strStatusCode=StringUtil.getPropertyValue(result.get("StatusCode"));
						if(strStatusCode.equals("000000")){
							logger.info("汇兑业务办理成功");
							Map<String , Object> mesMap=new HashMap<String , Object>();
							mesMap.put("DataSendState", "1");
							mesMap.put("FlwCommonId", StringUtil.getPropertyValue(result.get("FlwCommonId")));
							mesMap.put("ScanSeqNo", StringUtil.getPropertyValue(result.get("ScanSeqNo")));
							mesMap.put("BillTime", dateFormat.getCurrentDTime());
							baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
						}else{
							logger.info("汇兑业务办理失败");
							baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
						}
					}else{
						logger.info("影像参数异常");
						baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
					}
			}catch(Exception e){
				logger.info("影像上传失败");
				logger.error(e.getMessage(),e);
				baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
			}
		}else{
			logger.info("业务办理账号信息错误");
			baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
		}
		logger.info("******************汇兑业务"+strID+"办理结束******************");
		return result;
	}
	
	/**
	 * 交易状态查询
	 * @param map
	 * @return
	 */
	public Map<String, Object> getTransStatus(Map<String, Object> map){
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));//系统id 数据库无此字段
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));//柜员号
		String strID=StringUtil.getPropertyValue(map.get("ID"));//id
		String BillTime=StringUtil.getPropertyValue(map.get("BillTime"));//受理时间
		if(BillTime.length()>10){
			BillTime=BillTime.substring(0,10);
		}
		BillTime=BillTime.replaceAll("-", "/");
		String FlwCommonId=StringUtil.getPropertyValue(map.get("FlwCommonId"));
		String TransCode="com.all.sinodata.QTS";
			mHead.put("SystemId", SystemId);//系统id
			mHead.put("CntId", CntId);//柜员号
			mHead.put("TransCode",TransCode);//交易代码
			
		logger.info("******************"+strID+"交易状态查询开始******************");	
		Map<String ,Object> mBody=new HashMap<String ,Object>();
			mBody.put("accptDate",BillTime);//受理日期
			mBody.put("flwCommonId", FlwCommonId);//流水号
			try{
				String logName="交易状态查询";
				result=createMessage.cMessage(mHead, mBody,logName);//发送报文
				Map<String , Object> mesMap=new HashMap<String , Object>();
				mesMap.put("BillState",StringUtil.getPropertyValue(result.get("tranStatus")));//billstate pbstatus 人行状态 ,1230
				whereParamMap.put("ID",strID);//id
				baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
			}catch(Exception e){
				logger.info("交易查询失败:系统异常");
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "交易查询失败:系统异常");
			}
		logger.info("******************业务"+strID+"交易状态查询结束******************");
		return result;
	}

	/**
	 * 上海同城借记
	 * @param map
	 * @return
	 */
	public Map<String, Object> getDebit(Map<String, Object> map){
		Map<String , Object> result=new HashMap<String , Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> setAMap=new HashMap<String, Object>();
		Map<String ,Object> getAMap=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		
		//报文头信息
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
		String cellPhone =StringUtil.getPropertyValue(map.get("cellPhone"));
		String strID=StringUtil.getPropertyValue(map.get("ID"));
		String TransCode="com.1600.sinodata.debit";
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mHead.put("cellPhone", cellPhone);
		logger.info("******************上海同城借记"+strID+"办理开始******************");
			//查询账户，取得相关信息
			setAMap.put("SystemId", SystemId);
			setAMap.put("CntId", CntId);
			setAMap.put("MedAcctNo", MedAcctNo);
			getAMap=this.getAccountInfo(setAMap);
			String exstatuscode =StringUtil.getPropertyValue(getAMap.get("StatusCode"));
			if(exstatuscode.equals("000000")){
				String sealPwd =StringUtil.getPropertyValue(getAMap.get("SealPwd"));
				String custId =StringUtil.getPropertyValue(getAMap.get("CustId"));
				String payeeAcctNo =StringUtil.getPropertyValue(map.get("AcctNo"));//待定;
				String payeeName =StringUtil.getPropertyValue(getAMap.get("AcctName"));
				String payeeBkNo =StringUtil.getPropertyValue(getAMap.get("CompanyCode"));
				String payeeAcctType =StringUtil.getPropertyValue(getAMap.get("MediumType"));
				String postingRestrict =StringUtil.getPropertyValue(getAMap.get("PostingRestrict"));
				String currency ="CNY";//默认
				
				
				String voucherType ="160003";//待定
				String businessType ="1600";//待定
				String TranUid =StringUtil.getPropertyValue(map.get("TranUid"));
				String priority ="1";//1：普通、2：加急。默认普通
				String inactiveMarker =StringUtil.getPropertyValue(map.get("inactiveMarker"));
				String gzs ="0";//公转私默认0
				
				//影像上传
				String imgCode ="";
				String mainPageNum ="1";
				String mainPageIndex="0001";
				String indexNo ="0001";
				String indexMaxNo ="0003";
				String attachPageNum ="0";
				String ElectType=StringUtil.getPropertyValue(map.get("ElectType"));
				if(ElectType.equals("3")){
					indexMaxNo ="0001";
				}
				try{
					Map<String , Object> imageMap=new HashMap<String , Object>();
					imgCode=uImageUp.uploadFile(map);
					imageMap.put("TIamgeSendState","1");
					imageMap.put("fileType",imgCode);
					whereParamMap.put("ID",strID);
					int upstate=baseDao.updateByWhere(imageMap,whereParamMap, "p_imageinfo","and");
					if(upstate==1){
						logger.info("影像上传成功");
						mBody.put("voucherType", voucherType);
						mBody.put("sealPwd", sealPwd);
						mBody.put("currency", currency);
						mBody.put("custId", custId);
						mBody.put("payeeAcctNo", payeeAcctNo);
						mBody.put("payeeName",payeeName);
						mBody.put("payeeBkNo", payeeBkNo);
						mBody.put("payeeAcctType",payeeAcctType);
						mBody.put("businessType",businessType);
						mBody.put("inactiveMarker", inactiveMarker);
						mBody.put("gzs", gzs);
						mBody.put("postingRestrict", postingRestrict);
						
						mBody.put("imgCode", imgCode);
						mBody.put("mainPageNum",mainPageNum);
						mBody.put("mainPageIndex",mainPageIndex);
						mBody.put("indexNo", indexNo);
						mBody.put("indexMaxNo", indexMaxNo);
						mBody.put("attachPageNum", attachPageNum);
						
						mBody.put("TranUid", TranUid);
						mBody.put("priority", priority);
						
						String logName="同城借记";
						result=createMessage.cMessage(mHead, mBody,logName);
						String strStatusCode=StringUtil.getPropertyValue(result.get("StatusCode"));
						if(strStatusCode.equals("000000")){
							logger.info("同城借记办理成功");
							Map<String , Object> mesMap=new HashMap<String , Object>();
							mesMap.put("DataSendState", "1");
							mesMap.put("FlwCommonId", StringUtil.getPropertyValue(result.get("FlwCommonId")));
							mesMap.put("ScanSeqNo", StringUtil.getPropertyValue(result.get("ScanSeqNo")));
							mesMap.put("BillTime", dateFormat.getCurrentDTime());
							baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
						}else{
							logger.info("同城借记办理失败");
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
							baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
						}
					}else{
						logger.info("影像参数异常");
						baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
					}
				}catch(Exception e){
					logger.info("影像上传失败");
					logger.error(e.getMessage(),e);
					baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
				}
			}else{
				logger.info("业务办理账号信息错误");
				baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
			}
		logger.info("******************上海同城借记"+strID+"办理结束******************");
		return result;
	}
	
	/**
	 * 本票签发记账
	 * @param map
	 * @return
	 */
	public Map<String, Object> getPropniss(Map<String, Object> map){
		Map<String , Object> result=new HashMap<String , Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> setAMap=new HashMap<String, Object>();
		Map<String ,Object> getAMap=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		
		//报文头信息
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
		String cellPhone =StringUtil.getPropertyValue(map.get("cellPhone"));
		String strID=StringUtil.getPropertyValue(map.get("ID"));
		String TransCode="com.0301.sinodata.propniss";
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mHead.put("cellPhone", cellPhone);
		logger.info("******************本票签发记账"+strID+"办理开始******************");
			//查询账户，取得相关信息
			setAMap.put("SystemId", SystemId);
			setAMap.put("CntId", CntId);
			setAMap.put("MedAcctNo", MedAcctNo);
			getAMap=this.getAccountInfo(setAMap);
			String exstatuscode =StringUtil.getPropertyValue(getAMap.get("StatusCode"));
			if(exstatuscode.equals("000000")){
				String voucherType ="030101";//待定
				String sealPwd =StringUtil.getPropertyValue(getAMap.get("SealPwd"));
				String currency ="CNY";//默认
				String custId =StringUtil.getPropertyValue(getAMap.get("CustId"));
				String draweeAcctNo =StringUtil.getPropertyValue(map.get("AcctNo"));;
				String draweeName =StringUtil.getPropertyValue(getAMap.get("AcctName"));
				String draweeAcctType =StringUtil.getPropertyValue(getAMap.get("MediumType"));
				String choiceTag="NO";
				String postingRestrict =StringUtil.getPropertyValue(getAMap.get("PostingRestrict"));
				
				String businessType ="0301";//待定
				String TranUid =StringUtil.getPropertyValue(map.get("TranUid"));//待定
				String priority ="1";//1：普通、2：加急。默认普通
				
				//影像上传
				String imgCode ="";
				String mainPageNum ="1";
				String mainPageIndex="0001";
				String indexNo ="0001";
				String indexMaxNo ="0003";
				String attachPageNum ="0";
				String ElectType=StringUtil.getPropertyValue(map.get("ElectType"));
				if(ElectType.equals("3")){
					indexMaxNo ="0001";
				}
				try{
					Map<String , Object> imageMap=new HashMap<String , Object>();
					imgCode=uImageUp.uploadFile(map);
					imageMap.put("TIamgeSendState","1");
					imageMap.put("fileType",imgCode);
					whereParamMap.put("ID",strID);
					int upstate=baseDao.updateByWhere(imageMap,whereParamMap, "p_imageinfo","and");
					if(upstate==1){
						logger.info("影像上传成功");
						mBody.put("voucherType", voucherType);
						mBody.put("sealPwd", sealPwd);
						mBody.put("currency", currency);
						mBody.put("custId", custId);
						mBody.put("draweeAcctNo", draweeAcctNo);
						mBody.put("draweeName",draweeName);
						mBody.put("draweeAcctType", draweeAcctType);
						mBody.put("choiceTag",choiceTag);
						mBody.put("postingRestrict", postingRestrict);
						mBody.put("businessType",businessType);
					
						
						
						mBody.put("imgCode", imgCode);
						mBody.put("mainPageNum",mainPageNum);
						mBody.put("mainPageIndex",mainPageIndex);
						mBody.put("indexNo", indexNo);
						mBody.put("indexMaxNo", indexMaxNo);
						mBody.put("attachPageNum", attachPageNum);
						
						mBody.put("TranUid", TranUid);
						mBody.put("priority", priority);
						
						String logName="本票签发";
						result=createMessage.cMessage(mHead, mBody,logName);
						String strStatusCode=StringUtil.getPropertyValue(result.get("StatusCode"));
						if(strStatusCode.equals("000000")){
							logger.info("本票签发记账成功");
							Map<String , Object> mesMap=new HashMap<String , Object>();
							mesMap.put("DataSendState", "1");
							mesMap.put("FlwCommonId", StringUtil.getPropertyValue(result.get("FlwCommonId")));
							mesMap.put("ScanSeqNo", StringUtil.getPropertyValue(result.get("ScanSeqNo")));
							mesMap.put("BillTime", dateFormat.getCurrentDTime());
							baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
						}else{
							logger.info("本票签发记账失败");
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
							baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
						}
					}else{
						logger.info("影像参数异常");
						baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
					}
				}catch(Exception e){
					logger.info("影像上传失败");
					logger.error(e.getMessage(),e);
					baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
				}
			}else{
				logger.info("业务办理账号信息错误");
				baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
			}
		logger.info("******************本票签发记账"+strID+"办理结束******************");
		return result;
		
	}
	
	/**
	 * 票据存管待办
	 * @param map
	 * @return
	 */
	public Map<String, Object> getDepositWait(Map<String, Object> map){
		Map<String , Object> result=new HashMap<String , Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		List<Map<String,Object>> listTrading=new ArrayList<Map<String,Object>>();
		Map<String ,Object> listMap=new HashMap<String, Object>();
		
		//报文头信息
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String strID=StringUtil.getPropertyValue(map.get("ID"));
		String TransCode="com.all.sinodata.DepositWait";
		String phoneNo=StringUtil.getPropertyValue(map.get("cellPhone"));
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mHead.put("cellPhone", phoneNo);
		String tranUid =StringUtil.getPropertyValue(map.get("TranUid"));
		String acctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
		String acctName="";
		logger.info("******************票据存管待办"+strID+"办理开始******************");
				try{
					listTrading=baseDao.queryTranUid("select * from p_accinfo where MedAcctNo='"+acctNo+"'");
					if(listTrading.size()>0){
						listMap=listTrading.get(0);
						acctName=StringUtil.getPropertyValue(listMap.get("AcctName"));
					}
					String billNo=StringUtil.getPropertyValue(map.get("billNoRet"));
					String billType=StringUtil.getPropertyValue(map.get("TRANTYPE"));
							
							//影像上传
							String imgCode ="";
							String mainPageNum ="1";
							String attachPageNum ="0";
					Map<String , Object> imageMap=new HashMap<String , Object>();
					imgCode=uImageUp.uploadFile(map);
					imageMap.put("TIamgeSendState","1");
					imageMap.put("fileType",imgCode);
					whereParamMap.put("ID",strID);
					int upstate=baseDao.updateByWhere(imageMap,whereParamMap, "p_imageinfo","and");
					if(upstate==1){
						logger.info("影像上传成功");
						
						mBody.put("acctNo", acctNo);
						mBody.put("imgCode", imgCode);
						mBody.put("tranUid", tranUid);
						mBody.put("acctName", acctName);
						mBody.put("billNo", billNo);
						mBody.put("billType", billType);
						mBody.put("mainPageNum", mainPageNum);
						mBody.put("attachPageNum ", attachPageNum);
						
						String logName="票据存管待办";
						result=createMessage.cMessage(mHead, mBody,logName);
						String strStatusCode=StringUtil.getPropertyValue(result.get("StatusCode"));
						if(strStatusCode.equals("000000")){
							logger.info("票据存管待办办理成功");
							Map<String , Object> mesMap=new HashMap<String , Object>();
							mesMap.put("DataSendState", "1");
							mesMap.put("FlwCommonId", StringUtil.getPropertyValue(result.get("FlwCommonId")));
							mesMap.put("ScanSeqNo", StringUtil.getPropertyValue(result.get("ScanSeqNo")));
							mesMap.put("BillTime", dateFormat.getCurrentDTime());
							baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
						}else{
							logger.info("票据存管待办办理失败");
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
							baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
						}
					}else{
						logger.info("影像参数异常");
						baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
					}
				}catch(Exception e){
					logger.info("影像上传失败");
					logger.error(e.getMessage(),e);
					baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
				}
		logger.info("******************票据存管待办"+strID+"办理结束******************");
		return result;
		
	}
    
	/**
	 * 上海同城贷记
	 * @param map
	 * @return
	 */
	public Map<String, Object> getCredit(Map<String, Object> map) {
		Map<String , Object> result=new HashMap<String , Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> setAMap=new HashMap<String, Object>();
		Map<String ,Object> getAMap=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		
		//报文头信息
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
		String cellPhone =StringUtil.getPropertyValue(map.get("cellPhone"));
		String strID=StringUtil.getPropertyValue(map.get("ID"));
		String TransCode="com.0600.sinodata.Credit";
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mHead.put("cellPhone", cellPhone);
		logger.info("******************上海同城贷记"+strID+"办理开始******************");	
			//查询账户，取得相关信息
			setAMap.put("SystemId", SystemId);
			setAMap.put("CntId", CntId);
			setAMap.put("MedAcctNo", MedAcctNo);
			getAMap=this.getAccountInfo(setAMap);
			String exstatuscode =StringUtil.getPropertyValue(getAMap.get("StatusCode"));
			if(exstatuscode.equals("000000")){
				String sealPwd =StringUtil.getPropertyValue(getAMap.get("sealPwd"));
				String currency ="CNY";
				String draweeAcctNo =StringUtil.getPropertyValue(map.get("AcctNo"));;
				String draweeName =StringUtil.getPropertyValue(getAMap.get("draweeName"));
				String draweeBankNo =StringUtil.getPropertyValue(getAMap.get("draweeBankNo"));
				String acctChar =StringUtil.getPropertyValue(getAMap.get("MediumType"));
				
				String voucherType ="060001";//待定
				String businessType ="0600";//待定
				String TranUid =StringUtil.getPropertyValue(map.get("TranUid"));
				
				//影像上传
				String imgCode ="";
				String mainPageNum ="1";
				String mainPageIndex="0001";
				String indexNo ="0001";
				String indexMaxNo ="0003";
				String attachPageNum ="0";
				String ElectType=StringUtil.getPropertyValue(map.get("ElectType"));
				if(ElectType.equals("3")){
					indexMaxNo ="0001";
				}
				try{
					Map<String , Object> imageMap=new HashMap<String , Object>();
					imgCode=uImageUp.uploadFile(map);
					imageMap.put("TIamgeSendState","1");
					imageMap.put("fileType",imgCode);
					whereParamMap.put("ID",strID);
					int upstate=baseDao.updateByWhere(imageMap,whereParamMap, "p_imageinfo","and");
					if(upstate==1){
						logger.info("影像上传成功");
						mBody.put("voucherType", voucherType);
						mBody.put("sealPwd", sealPwd);
						mBody.put("currency", currency);
						mBody.put("draweeAcctNo", draweeAcctNo);
						mBody.put("draweeName", draweeName);
						mBody.put("draweeBankNo",draweeBankNo);
						mBody.put("acctChar", acctChar);
						mBody.put("businessType",businessType);
						
						mBody.put("imgCode", imgCode);
						mBody.put("mainPageNum",mainPageNum);
						mBody.put("mainPageIndex",mainPageIndex);
						mBody.put("indexNo", indexNo);
						mBody.put("indexMaxNo", indexMaxNo);
						mBody.put("attachPageNum", attachPageNum);
						
						mBody.put("TranUid", TranUid);
						
						String logName="同城贷记";
						result=createMessage.cMessage(mHead, mBody,logName);
						String strStatusCode=StringUtil.getPropertyValue(result.get("StatusCode"));
						if(strStatusCode.equals("000000")){
							logger.info("同城贷记办理成功");
							Map<String , Object> mesMap=new HashMap<String , Object>();
							mesMap.put("DataSendState", "1");
							mesMap.put("FlwCommonId", StringUtil.getPropertyValue(result.get("FlwCommonId")));
							mesMap.put("ScanSeqNo", StringUtil.getPropertyValue(result.get("ScanSeqNo")));
							mesMap.put("BillTime", dateFormat.getCurrentDTime());
							baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
						}else{
							logger.info("同城贷记办理失败");
							baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
						}
					}else{
						logger.info("影像参数异常");
						baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
					}
				}catch(Exception e){
					logger.info("影像上传失败");
					logger.error(e.getMessage(), e);
					baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
				}
			}else{
				logger.info("业务办理账号信息错误");
				baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
			}
		
		logger.info("******************上海同城贷记"+strID+"办理结束******************");	
		return result;
	}
	
	/**
	 * 行内转账
	 * @param map
	 * @return
	 */
	public Map<String, Object> getTNbosFtBill(Map<String, Object> map) {
		Map<String , Object> result=new HashMap<String , Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> setAMap=new HashMap<String, Object>();
		Map<String ,Object> getAMap=new HashMap<String, Object>();
		Map<String ,Object> whereParamMap=new HashMap<String, Object>();
		
		//报文头信息
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String MedAcctNo=StringUtil.getPropertyValue(map.get("AcctNo"));
		String cellPhone =StringUtil.getPropertyValue(map.get("cellPhone"));
		String strID=StringUtil.getPropertyValue(map.get("ID"));
		String TransCode="com.0700.sinodata.TNbosFtBill";
			mHead.put("SystemId", SystemId);
			mHead.put("CntId", CntId);
			mHead.put("TransCode",TransCode);
			mHead.put("cellPhone", cellPhone);
		logger.info("******************行内转账"+strID+"办理开始******************");	
			//查询账户，取得相关信息
			setAMap.put("SystemId", SystemId);
			setAMap.put("CntId", CntId);
			setAMap.put("MedAcctNo", MedAcctNo);
			getAMap=this.getAccountInfo(setAMap);
			String exstatuscode =StringUtil.getPropertyValue(getAMap.get("StatusCode"));
			if(exstatuscode.equals("000000")){
				String sealPwd =StringUtil.getPropertyValue(getAMap.get("sealPwd"));
				String currency ="CNY";
				String draweeAcctNo =StringUtil.getPropertyValue(map.get("AcctNo"));;
				String draweeName =StringUtil.getPropertyValue(getAMap.get("draweeName"));
				String draweeBankNo =StringUtil.getPropertyValue(getAMap.get("draweeBankNo"));
				String acctChar =StringUtil.getPropertyValue(getAMap.get("MediumType"));
				
				String voucherType ="070001";//待定
				String businessType ="0700";//待定
				String TranUid =StringUtil.getPropertyValue(map.get("TranUid"));
				
				//影像上传
				String imgCode ="";
				String mainPageNum ="1";
				String mainPageIndex="0001";
				String indexNo ="0001";
				String indexMaxNo ="0003";
				String attachPageNum ="0";
				String ElectType=StringUtil.getPropertyValue(map.get("ElectType"));
				if(ElectType.equals("3")){
					indexMaxNo ="0001";
				}
				try{
					Map<String , Object> imageMap=new HashMap<String , Object>();
					imgCode=uImageUp.uploadFile(map);
					imageMap.put("TIamgeSendState","1");
					imageMap.put("fileType",imgCode);
					whereParamMap.put("ID",strID);
					int upstate=baseDao.updateByWhere(imageMap,whereParamMap, "p_imageinfo","and");
					if(upstate==1){
						logger.info("影像上传成功");
						mBody.put("voucherType", voucherType);
						mBody.put("sealPwd", sealPwd);
						mBody.put("currency", currency);
						mBody.put("draweeAcctNo", draweeAcctNo);
						mBody.put("draweeName", draweeName);
						mBody.put("draweeBankNo",draweeBankNo);
						mBody.put("acctChar", acctChar);
						mBody.put("businessType",businessType);
						
						mBody.put("imgCode", imgCode);
						mBody.put("mainPageNum",mainPageNum);
						mBody.put("mainPageIndex",mainPageIndex);
						mBody.put("indexNo", indexNo);
						mBody.put("indexMaxNo", indexMaxNo);
						mBody.put("attachPageNum", attachPageNum);
						
						mBody.put("TranUid", TranUid);
						
						String logName="行内转账";
						result=createMessage.cMessage(mHead, mBody,logName);
						String strStatusCode=StringUtil.getPropertyValue(result.get("StatusCode"));
						if(strStatusCode.equals("000000")){
							logger.info("行内转账办理成功");
							Map<String , Object> mesMap=new HashMap<String , Object>();
							mesMap.put("DataSendState", "1");
							mesMap.put("FlwCommonId", StringUtil.getPropertyValue(result.get("FlwCommonId")));
							mesMap.put("ScanSeqNo", StringUtil.getPropertyValue(result.get("ScanSeqNo")));
							mesMap.put("BillTime", dateFormat.getCurrentDTime());
							baseDao.updateByWhere(mesMap,whereParamMap, "p_imageinfo","and");
						}else{
							logger.info("行内转账办理失败");
							baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
							result.put("StatusCode", "111111");
							result.put("ServerStatusCode", "系统异常");
						}
					}else{
						logger.info("影像参数异常");
						baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
					}
				}catch(Exception e){
					logger.info("影像上传失败");
					logger.error(e.getMessage(),e);
					baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
				}
			}else{
				logger.info("业务办理账号信息错误");
				baseDao.insertMapTable("delete from p_businessid where id='"+strID+"'");
			}
		
		logger.info("******************行内转账"+strID+"办理结束******************");	
		return result;
	}
}
