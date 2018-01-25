package com.atmp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atmp.service.DataQueryInterface;
import com.atmp.util.HttpJsonResult;
/**
 * 清机轧帐数据查询
 * @author ligen
 *
 */
@Controller
public class DataQueryController {

	private static final Logger logger= Logger.getLogger(DataQueryController.class);
	@Resource
	private DataQueryInterface dataQuery;
	/**
	 * 轧帐数据查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/dataQuery/Showaccount")
	@ResponseBody
	public void showaccount(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String ,Object> restitle=new HashMap<String, Object>();
		Map<String ,Object> result=new HashMap<String, Object>();
		
		String isclean=request.getParameter("isclean");
		map.put("devicenum", request.getParameter("devicenum"));//机具ID
		if(isclean.equals("0")){
			List<Map<String, Object>> titleList=dataQuery.gettitleListQuery(map);
			List<Map<String, Object>> titlemap=new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
			Date d=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sftime=new SimpleDateFormat("HH:mm");
			SimpleDateFormat sfymd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			restitle.put("acceptnotenum",titleList.get(0).get("acceptnotenum"));
			restitle.put("wtsNum" , titleList.get(0).get("wtsNum"));
			restitle.put("zpNum" , titleList.get(0).get("zpNum"));
			restitle.put("jzdNum" , titleList.get(0).get("jzdNum"));
			restitle.put("date",sf.format(d));
			restitle.put("time",sftime.format(d));
			titlemap.add(restitle);
			Map<String ,Object> resMap=new HashMap<String, Object>();
			resMap.put("titlemap", titlemap);
			
			List<Map<String, Object>> dataList=dataQuery.getDateListQuery(map);
			
			if(dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					Map<String ,Object> resdate=new HashMap<String, Object>();
					resdate.put("INSTANCEID", dataList.get(i).get("id"));
					resdate.put("PAYEEACCNO", dataList.get(i).get("AcctNo"));
					resdate.put("PAYEENAME", dataList.get(i).get("AcctName"));
					resdate.put("TRANTYPENAME" , dataList.get(i).get("ElectType"));
					resdate.put("time" ,sfymd.format(dataList.get(i).get("ReleaseTime")));
					resdate.put("billNoRet" ,dataList.get(i).get("billNoRet"));
					resdate.put("BillState" ,dataList.get(i).get("BillState"));
					data.add(resdate);
				}
			}
			resMap.put("data", data);
			HttpJsonResult.writerMap(resMap,response);
		}else if(isclean.equals("1")){
			map.put("qjuser", request.getParameter("qjuser"));
			try{
				int res=dataQuery.startClean(map);
				if(res>0){
					result.put("StatusCode", "000000");
					result.put("ServerStatusCode", "清机成功");
				}else{
					result.put("StatusCode", "111111");
					result.put("ServerStatusCode", "清机失败");
				}
			}catch(Exception e){
				result.put("StatusCode", "111111");
				result.put("ServerStatusCode", "系统异常");
				logger.error(e.getMessage(), e);
			}
			HttpJsonResult.writerMap(result,response);
		}
	}
	
	/**
	 * 查询特定日期轧账信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/dataQuery/Printaccount")
	@ResponseBody
	public void printaccount(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("devicenum", request.getParameter("devicenum"));//机具ID
		map.put("time", request.getParameter("time"));//票据受理时间
			List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
			SimpleDateFormat sfymd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Map<String ,Object> resMap=new HashMap<String, Object>();
		try{
			List<Map<String, Object>> dataList=dataQuery.getDateTimeListQuery(map);
			if(dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					Map<String ,Object> resdate=new HashMap<String, Object>();
					resdate.put("INSTANCEID", dataList.get(i).get("id"));
					resdate.put("PAYEEACCNO", dataList.get(i).get("AcctNo"));
					resdate.put("PAYEENAME", dataList.get(i).get("AcctName"));
					resdate.put("TRANTYPENAME" , dataList.get(i).get("ElectType"));
					resdate.put("time" ,sfymd.format(dataList.get(i).get("ReleaseTime")));
					resdate.put("billNoRet" ,dataList.get(i).get("billNoRet"));
					resdate.put("BillState" ,dataList.get(i).get("BillState"));
					resdate.put("ButtonType" ,dataList.get(i).get("ButtonType"));
					resdate.put("cellPhone" ,dataList.get(i).get("cellPhone"));
					data.add(resdate);
				}
			}
			resMap.put("data", data);
		}catch(Exception e){
			resMap.put("data", "");
			logger.error(e.getMessage(), e);
		}
		HttpJsonResult.writerMap(resMap,response);
	}
	
	/**
	 * 查询特定日期存管票据信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/dataQuery/PrintDepInfo")
	@ResponseBody
	public void printDepInfo(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("devicenum", request.getParameter("devicenum"));//机具ID
		map.put("time", request.getParameter("time"));//票据受理时间
			List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
			SimpleDateFormat sfymd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Map<String ,Object> resMap=new HashMap<String, Object>();
		try{
			List<Map<String, Object>> dataList=dataQuery.getDepositoryInfo(map);
			if(dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					Map<String ,Object> resdate=new HashMap<String, Object>();
					resdate.put("INSTANCEID", dataList.get(i).get("id"));
					resdate.put("PAYEEACCNO", dataList.get(i).get("AcctNo"));
					resdate.put("PAYEENAME", dataList.get(i).get("AcctName"));
					resdate.put("time" ,sfymd.format(dataList.get(i).get("ReleaseTime")));
					resdate.put("billNoRet" ,dataList.get(i).get("billNoRet"));
					resdate.put("cellPhone" ,dataList.get(i).get("cellPhone"));
					data.add(resdate);
				}
			}
			resMap.put("data", data);
		}catch(Exception e){
			resMap.put("data", "");
			logger.error(e.getMessage(), e);
		}
		HttpJsonResult.writerMap(resMap,response);
	}
}
