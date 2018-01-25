package com.atmp.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.atmp.system.CustomDriverManagerConnectionProvider;
import com.atmp.util.DateFormat;
import com.primeton.www.AcctInfoQueryImpService.AcctInfoQueryImpServiceProxy;

public class CreateMessage {
	
	CustomDriverManagerConnectionProvider customConfigInfo=new CustomDriverManagerConnectionProvider();
	private static final Logger logger= Logger.getLogger(CreateMessage.class);
	
	

	/**
	 * 发送报文
	 * @param map
	 * @return
	 */
	public Map<String, Object> cMessage(Map<String, Object> mHead,Map<String, Object> mBody,String logName) {
		Map<String, Object> result=new HashMap<String ,Object>();
		try{
			Document doc=DocumentHelper.createDocument();
			Element root=doc.addElement("Pbank");
			//请求报文头
			DateFormat mes=new DateFormat();
			Element head=root.addElement("CommonRqHdr");
			Element TranDate=head.addElement("TranDate");
			TranDate.addText(mes.getCurrentDate());
			Element TranTime=head.addElement("TranTime");
			TranTime.addText(mes.getCurrentDateTime());
			Set sHead = mHead.entrySet();     
			Iterator i = sHead.iterator();     
			while(i.hasNext()){  
			    Map.Entry<String, String> entryMap=(Map.Entry<String, String>)i.next(); 
			    String strEntryMapValue =entryMap.getValue();
			    if(StringUtils.isNotEmpty(strEntryMapValue)){
			    	strEntryMapValue=strEntryMapValue.toString();
			    }
				Element voucherType=head.addElement(entryMap.getKey());
				voucherType.addText(entryMap.getValue());
			}
			
			//请求报文体
			Element body=root.addElement("CommonRqBdy");
			Set sBody = mBody.entrySet();     
			Iterator j = sBody.iterator();     
			while(j.hasNext()){  
			    Map.Entry<String, String> entryMap=(Map.Entry<String, String>)j.next(); 
			    String strEntryMapValue =entryMap.getValue();
			    if(StringUtils.isNotEmpty(strEntryMapValue)){
			    	strEntryMapValue=strEntryMapValue.toString();
			    }
				Element voucherType=body.addElement(entryMap.getKey());
				voucherType.addText(entryMap.getValue());
			}
			String iMessage=doc.asXML();
			logger.info(logName+"请求报文"+iMessage);
			//发报文
			String endPoint = customConfigInfo.getContextProperty("endPoint");
			AcctInfoQueryImpServiceProxy serviceProxy = new AcctInfoQueryImpServiceProxy(endPoint);
			String getMessage=serviceProxy.getAcctInfoQueryImpService().acctInfoQuery(iMessage);
			logger.info(logName+"响应报文"+getMessage);
			//解析报文
			ParserXml parserXml=new ParserXml();
			result=parserXml.getXml(getMessage);
		}catch(Exception e){
			logger.info("发送"+logName+"请求报文失败");
			logger.error(e.getMessage(), e);
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "发送"+logName+"请求报文失败");
		}
		return result;
	}
	
	
	/**
	 * 发送回单卡查询报文
	 * @param map
	 * @return
	 */
	public Map<String, Object> cCardMessage(Map<String, Object> mHead,Map<String, Object> mBody,String logName) {
		Map<String,Object> result=new HashMap<String,Object>();
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> errorMap=new HashMap<String ,Object>();
		try{
			Document doc=DocumentHelper.createDocument();
			Element root=doc.addElement("Pbank");
			//请求报文头
			DateFormat mes=new DateFormat();
			Element head=root.addElement("CommonRqHdr");
			Element TranDate=head.addElement("TranDate");
			TranDate.addText(mes.getCurrentDate());
			Element TranTime=head.addElement("TranTime");
			TranTime.addText(mes.getCurrentDateTime());
			Set sHead = mHead.entrySet();     
			Iterator i = sHead.iterator();     
			while(i.hasNext()){  
			    Map.Entry<String, String> entryMap=(Map.Entry<String, String>)i.next(); 
			    String strEntryMapValue =entryMap.getValue();
			    if(StringUtils.isNotEmpty(strEntryMapValue)){
			    	strEntryMapValue=strEntryMapValue.toString();
			    }
				Element voucherType=head.addElement(entryMap.getKey());
				voucherType.addText(entryMap.getValue());
			}
			
			//请求报文体
			Element body=root.addElement("CommonRqBdy");
			Set sBody = mBody.entrySet();     
			Iterator j = sBody.iterator();     
			while(j.hasNext()){  
			    Map.Entry<String, String> entryMap=(Map.Entry<String, String>)j.next(); 
			    String strEntryMapValue =entryMap.getValue();
			    if(StringUtils.isNotEmpty(strEntryMapValue)){
			    	strEntryMapValue=strEntryMapValue.toString();
			    }
				Element voucherType=body.addElement(entryMap.getKey());
				voucherType.addText(entryMap.getValue());
			}
			String iMessage=doc.asXML();
			logger.info(logName+"请求报文"+iMessage);
			//发报文
			String endPoint = customConfigInfo.getContextProperty("endPoint");
			AcctInfoQueryImpServiceProxy serviceProxy = new AcctInfoQueryImpServiceProxy(endPoint);
			String getMessage=serviceProxy.getAcctInfoQueryImpService().acctInfoQuery(iMessage);
			logger.info(logName+"响应报文"+getMessage);
			//解析报文
			ParserXml parserXml=new ParserXml();
			result=parserXml.getListXml(getMessage);
		}catch(Exception e){
			logger.info("发送"+logName+"请求报文失败");
			logger.error(e.getMessage(),e);
			errorMap.put("StatusCode", "111111");
			errorMap.put("ServerStatusCode", "发送失败");
			list.add(errorMap);
			result.put("datahead",list);
		}
		return result;
	}
}
