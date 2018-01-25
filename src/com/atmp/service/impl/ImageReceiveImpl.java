package com.atmp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.atmp.dao.HandleMesDao;
import com.atmp.message.ImgHandle;
import com.atmp.service.ImageReceiveInterface;
import com.atmp.system.CustomDriverManagerConnectionProvider;
import com.atmp.util.DateFormat;
import com.atmp.util.StringUtil;

@Service("imageReceive")
public class ImageReceiveImpl implements ImageReceiveInterface {
	
	private static final Logger logger= Logger.getLogger(ImageReceiveImpl.class);
	
	@Resource
	private  HandleMesDao baseDao; 
	CustomDriverManagerConnectionProvider customConfigInfo=new CustomDriverManagerConnectionProvider();
	
	/**
	 * 存储上送数据
	 * @param map
	 * @return
	 */
	public Map<String, Object> addReceiveInfo(Map<String, Object> map){
		List<Map<String,Object>> listTrading=new ArrayList<Map<String,Object>>();
		Map<String ,Object> result=new HashMap<String, Object>();
		Map<String ,Object> imgHandMap=new HashMap<String, Object>();
		Map<String ,Object> listMap=new HashMap<String, Object>();
		
		ImgHandle imgHandle=new ImgHandle();
		DateFormat mes=new DateFormat();
		logger.info("******************存储上送数据开始******************");
		try{
			String dirname=mes.getCurrentDate();
			String logoPathDir = customConfigInfo.getContextProperty("imgpath")+dirname;
			int ID=baseDao.querySeq("IMAGEINFOSEQ");
			String deviceNum=StringUtil.getPropertyValue(map.get("deviceNum"));
			String sql="select max(TranUid) from p_imageinfo where  trunc(ReleaseTime)=to_date('"+dirname+"','yyyy-mm-dd') and JJID='"+deviceNum+"'";
			deviceNum=deviceNum.substring(7,13);
			String TranUid=dirname+deviceNum+"001";
			listTrading=baseDao.queryTranUid(sql);
			String tranUid="";
			if(listTrading.size()>0){
				listMap=listTrading.get(0);
					tranUid=StringUtil.getPropertyValue(listMap.get("max(TranUid)"));
			}
			if(tranUid!=null&&!tranUid.equals("")){
				Long s=Long.parseLong(tranUid)+1;
				TranUid=String.valueOf(s);
			}
				map.put("logoPathDir",logoPathDir);
				map.put("ID",ID);
				map.put("TranUid",TranUid);
			    imgHandMap=imgHandle.getImgHandle(map);
			    map.putAll(imgHandMap);
			    String MedAcctNo=StringUtil.getPropertyValue(map.get("MedAcctNo"));
			    String cellPhone=StringUtil.getPropertyValue(map.get("cellPhone"));
			    baseDao.insertMapTable("update p_accinfo set cellPhone='"+cellPhone+"' where MedAcctNo='"+MedAcctNo+"'");
			    int message=baseDao.insertMapToTable(map, "p_imageinfo");
				
				if(message==1){
					logger.info("数据存储成功");
					result.put("StatusCode", "000000");
					result.put("ServerStatusCode", "数据存储成功");
					result.put("slid",TranUid);
				}else{
					logger.info("数据存储失败");
					result.put("StatusCode", "111111");
					result.put("ServerStatusCode", "数据存储失败");
				}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "系统异常");
		}
		logger.info("******************存储上送数据结束******************");
		return result;
		
	}

	/**
	 * OCX版本查询
	 * @param request
	 * @param response
	 */
	public Map<String, Object> getBtmOcxUpdate(Map<String, Object> map) {
		Map<String ,Object> result=new HashMap<String, Object>();
		
		String EquipmentType=StringUtil.getPropertyValue(map.get("EquipmentType"));
		String sql="select * from p_ocxversion where EquipmentType='"+EquipmentType+"'";
		logger.info("******************版本查询开始******************");
		logger.info("请求"+map);
		try{
			result=baseDao.queryMap(sql);
			if(result==null||result.size()<1){
				result=new HashMap<String, Object>();
				result.put("VERSIONCODE", "");
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("VERSIONCODE", "");
		}
		logger.info("******************版本查询结束******************");
		return result;
	}
	
}
