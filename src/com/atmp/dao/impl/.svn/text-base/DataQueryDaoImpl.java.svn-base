package com.atmp.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atmp.dao.DataQueryDao;
import com.atmp.service.impl.ImageReceiveImpl;

@Repository
public class DataQueryDaoImpl  implements DataQueryDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger logger= Logger.getLogger(DataQueryDaoImpl.class);

	
	public List<Map<String, Object>> gettitleListQuery(Map<String, Object> map) {
		
		String sql="select sum(case electtype when '1' then 1 end) as wtsNum,sum(case electtype when '2' then 1  end) as zpNum,";
			   sql+="sum(case electtype when '3' then 1  end) as jzdNum,count(*) as acceptnotenum from p_imageinfo ";  
			   sql+=" where  jjid='"+map.get("devicenum")+"' and qjbs='0'";
		logger.info("轧帐头信息查询"+sql);
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getDateListQuery(Map<String, Object> map) {
		String sql="select a.id,a.AcctNo,b.AcctName,a.ElectType,a.ReleaseTime,a.billNoRet,a.BillState from p_imageinfo a,p_accinfo b where a.medacctno =b.medacctno and  a.jjid='"+map.get("devicenum")+"' and a.qjbs='0' order by a.releasetime";
		logger.info("轧帐体信息查询"+sql);
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getDateTimeListQuery(Map<String, Object> map) {
		String sql="select a.id,a.AcctNo,b.AcctName,a.ElectType,a.ReleaseTime,a.billNoRet,a.BillState,a.ButtonType,a.cellPhone from p_imageinfo a,p_accinfo b where a.medacctno =b.medacctno and  a.jjid='"+map.get("devicenum")+"' and trunc(ReleaseTime)=to_date('"+map.get("time")+"','yyyy-mm-dd') order by a.releasetime";
		logger.info("查询特定日期轧账信息"+sql);
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getDepositoryInfo(Map<String, Object> map) {
		String sql="select a.id,a.AcctNo,b.AcctName,a.ReleaseTime,a.billNoRet,a.BillState,a.cellPhone from p_imageinfo a,p_accinfo b where a.medacctno =b.medacctno and  a.jjid='"+map.get("devicenum")+"' and trunc(ReleaseTime)=to_date('"+map.get("time")+"','yyyy-mm-dd') and a.ElectType='3' order by a.releasetime";
		logger.info("查询特定日期存管票据信息"+sql);
		return jdbcTemplate.queryForList(sql);
	}
	
	public int startClean(Map<String, Object> map){

		String  sql="update p_imageinfo set qjbs='1',qjuser='"+map.get("qjuser")+"' where jjid='"+map.get("devicenum")+"' and qjbs='0'";
		logger.info("开始清机"+sql);
		return jdbcTemplate.update(sql);
	}
	
}
