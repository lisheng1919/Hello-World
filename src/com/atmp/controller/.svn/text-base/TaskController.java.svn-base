package com.atmp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.atmp.dao.HandleMesDao;
import com.atmp.service.TaskTradingInterface;
import com.atmp.system.CustomDriverManagerConnectionProvider;
import com.atmp.util.DateFormat;
import com.atmp.util.StringUtil;

@Controller
public class TaskController {
	
	private static final Logger logger=Logger.getLogger(TaskController.class);
	
	@Resource 
	private JdbcTemplate jdbcTemplate;
	@Resource
	private  HandleMesDao baseDao; 
	@Resource
	private TaskTradingInterface taskTradingInterface;
	CustomDriverManagerConnectionProvider customConfigInfo=new CustomDriverManagerConnectionProvider();
	/**
	 * 业务处理
	 */
	@Scheduled(cron="0 0/1 07-22 * * ?")
	public void getTrading(){
		List<Map<String,Object>> listTrading=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		String strSQL="select * from p_imageinfo where DataSendState='0' order by ReleaseTime";
		logger.info("******************查询未处理业务开始******************");
		try{
			listTrading=jdbcTemplate.queryForList(strSQL);
			if(listTrading.size()>0){
				for(int i=0;i<listTrading.size();i++){
					map=listTrading.get(i);
					String ID=StringUtil.getPropertyValue(map.get("ID"));
					int sult=baseDao.insertMapTable("select * from p_businessid where ID='"+ID+"'");
					if(sult==0){
						int insult=baseDao.insertMapTable("insert into p_businessid (ID) values('"+ID+"')");
						if(insult>0){
							String ButtonType=StringUtil.getPropertyValue(map.get("ButtonType"));
							if(ButtonType.equals("1")){
								taskTradingInterface.getAgiotageNum(map);
							}else if(ButtonType.equals("2")){
								taskTradingInterface.getPropniss(map);
							}else if(ButtonType.equals("5")){
								taskTradingInterface.getDebit(map);
							}else if(ButtonType.equals("7")){
								taskTradingInterface.getDepositWait(map);
							}else if(ButtonType.equals("8")){
								taskTradingInterface.getCredit(map);
							}else if(ButtonType.equals("9")){
								taskTradingInterface.getTNbosFtBill(map);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			logger.info("未处理业务办理异常");
			logger.error(e.getMessage(), e);
		}
		logger.info("******************查询未处理业务结束******************");
	}
	
	/**
	 * 交易查询
	 */
	@Scheduled(cron="0 0/10 07-22 * * ?")
//	@Scheduled(cron="0/10 * 07-22 * * ?")
	public void getTransStatus(){
		List<Map<String,Object>> listTrading=new ArrayList<Map<String,Object>>();
		Map<String ,Object> map=new HashMap<String, Object>();
		String strSQL="select * from p_imageinfo where ButtonType in ('1','5') and (BillState = '2' or BillState ='null' or BillState is null) and " +
				"substr(billtime,0,10)>to_char(sysdate-3,'yyyy-mm-dd')"+
				"and substr(billtime,0,10)<=to_char(sysdate,'yyyy-mm-dd')";
		/*String strSQL1 =" select * " +
								"  from p_imageinfo " +
									" where ButtonType in ('1', '5') " +
									  " and (BillState = '2'" +
									   " or BillState = 'null' " +
									   " or BillState is null)"+
									   "and substr(billtime, 0, 10) > to_char(sysdate - 3, 'yyyy-mm-dd')" +
									   "and substr(billtime, 0, 10) <= to_char(sysdate, 'yyyy-mm-dd')";
		*/
		logger.info("******************交易状态查询开始******************");
		try{
			listTrading=jdbcTemplate.queryForList(strSQL);
			if(listTrading.size()>0){
				for(int i=0;i<listTrading.size();i++){
					map=listTrading.get(i);
					taskTradingInterface.getTransStatus(map);
				}
			}
		}catch (Exception e) {
			logger.info("交易状态查询异常");
			logger.error(e.getMessage(), e);
		}
		logger.info("******************交易状态查询结束******************");
	}
	
	/**
	 * 删除过期文件
	 */
	@Scheduled(cron="0 0 23 * * ?")
	public void deleteImageFile(){
		DateFormat mes=new DateFormat();
		String dirname=mes.getBeforeTime();
		logger.info("******************删除过期文件开始******************");
		try{
			File imageFile=new File(customConfigInfo.getContextProperty("imgpath")+dirname);
			logger.info("开始删除"+imageFile+"影像文件");
			this.deleteFiles(imageFile);
			File logFile=new File(customConfigInfo.getContextProperty("logPath")+dirname);
			logger.info("开始删除"+logFile+"日志");
			this.deleteFiles(logFile);
		}catch (Exception e) {
			logger.info("删除文件异常");
			logger.error(e.getMessage(), e);
		}
		logger.info("******************删除过期文件结束******************");
	}
	
	public void deleteFiles(File filePath){
		if(!filePath.exists()){
			return;
		}
		if(filePath.isFile()){
			filePath.delete();
			return;
		}
		File[] files=filePath.listFiles();
		for(int i=0;i<files.length;i++){
			deleteFiles(files[i]);
		}
		filePath.delete();
	}
}
