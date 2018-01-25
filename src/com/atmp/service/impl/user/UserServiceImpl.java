package com.atmp.service.impl.user;

import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.atmp.message.CreateMessage;
import com.atmp.service.user.UserServiceInterface;
import com.atmp.util.StringUtil;

@Service("userService")
public class UserServiceImpl implements UserServiceInterface {
	
	private static final Logger logger= Logger.getLogger(UserServiceImpl.class);
	
	CreateMessage createMessage=new CreateMessage();

	@Override
	public Map<String, Object> userLogin(Map<String, Object> map){
		
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> mHead=new HashMap<String, Object>();
		Map<String ,Object> mBody=new HashMap<String, Object>();
		Map<String ,Object> queryMap=new HashMap<String, Object>();
		
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId"));
		String CntId=StringUtil.getPropertyValue(map.get("CntId"));
		String TransCode="com.all.sinodata.CheckTeller";
		String teller=StringUtil.getPropertyValue(map.get("teller"));
		if(StringUtils.isNotEmpty(teller)){
			if(StringUtils.isNotEmpty(SystemId)){
				if(StringUtils.isNotEmpty(CntId)){
						mHead.put("SystemId", SystemId);
						mHead.put("CntId", CntId);
						mHead.put("TransCode",TransCode);
						mBody.put("teller", teller);
						try{
							logger.info("开始柜员身份验证");
							String logName="柜员身份验证";
							result=createMessage.cMessage(mHead, mBody,logName);
						}catch(Exception e){
							logger.error("系统异常");
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
			logger.info("柜员号号为空");
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "介质账号为空");
		}
		return result;

	}
}
