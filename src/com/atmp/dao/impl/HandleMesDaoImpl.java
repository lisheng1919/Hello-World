package com.atmp.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atmp.dao.HandleMesDao;
import com.atmp.util.StringUtil;

@Repository
public class HandleMesDaoImpl implements HandleMesDao{
	
	private static final Logger logger=Logger.getLogger(HandleMesDaoImpl.class);
	
	@Resource
	public JdbcTemplate jdbcTemplate;
	
	
	public int editObject(String sql, Object[] obj) {
		int index = 0;
		try {
			index = jdbcTemplate.update(sql,obj);
		} catch (DataAccessException e) {
			logger.info(e);
		}
		return index;
	}
	
	/**
	 * 执行插入语句
	 * @param map
	 * @param tableName
	 * @return
	 */
	public int insertMapToTable(Map<String ,Object> map,String tableName){
		int index = 0;
		String sql = "insert into "+ tableName 
			+"(ID,MedAcctNo,AcctNo,cellPhone,ReleaseTime,SystemId,CntId,CompanyCode,JJID,QJBS,ElectType,"
			+"ButtonType,TRANTYPE,FrontImage,FrontImagePath,BackImage,BackImagePath,VoucherImage,"
			+"VoucherImagePath,DataSendState,IamgeSendState,IamgeSendRemark,billNoRet,TranUid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String ID=StringUtil.getPropertyValue(map.get("ID"));   
		String MedAcctNo=StringUtil.getPropertyValue(map.get("MedAcctNo"));
		String cellPhone=StringUtil.getPropertyValue(map.get("cellPhone"));
		String SystemId=StringUtil.getPropertyValue(map.get("SystemId")); 
		String CntId=StringUtil.getPropertyValue(map.get("CntId")); 
		String QJBS="0";
		String DataSendState="0";
		String IamgeSendState=StringUtil.getPropertyValue(map.get("IamgeSendState")); 
		String IamgeSendRemark=StringUtil.getPropertyValue(map.get("IamgeSendRemark")); 
		String CompanyCode=StringUtil.getPropertyValue(map.get("CompanyCode")); 
		String JJID=StringUtil.getPropertyValue(map.get("deviceNum")); 
		String AcctNo=StringUtil.getPropertyValue(map.get("AcctNo")); 
		String ElectType=StringUtil.getPropertyValue(map.get("electType"));
		String ButtonType=StringUtil.getPropertyValue(map.get("buttonType"));
		String TRANTYPE=StringUtil.getPropertyValue(map.get("trantype"));
		String FrontImage=StringUtil.getPropertyValue(map.get("FrontImage")); 
		String FrontImagePath=StringUtil.getPropertyValue(map.get("FrontImagePath")); 
		String BackImage=StringUtil.getPropertyValue(map.get("BackImage")); 
		String BackImagePath=StringUtil.getPropertyValue(map.get("BackImagePath"));  
		String VoucherImage=StringUtil.getPropertyValue(map.get("VoucherImage")); 
		String VoucherImagePath=StringUtil.getPropertyValue(map.get("VoucherImagePath")); 
		String billNoRet=StringUtil.getPropertyValue(map.get("billNoRet")); 
		String TranUid=StringUtil.getPropertyValue(map.get("TranUid")); 
		
		try {
			index = jdbcTemplate.update(sql,new Object[]{ID,MedAcctNo,AcctNo,cellPhone,new Date(),SystemId,CntId,CompanyCode,JJID,QJBS,ElectType,ButtonType,TRANTYPE,FrontImage,FrontImagePath,BackImage,BackImagePath,VoucherImage,VoucherImagePath,DataSendState,IamgeSendState,IamgeSendRemark,billNoRet,TranUid});
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return index;
	}
	
	/**
	 * 生成update语句set后面并且where前面部分的sql，如set a=?,b=?  两个问号生成参数数组返回
	 * value为null的不做处理，为""（长度为0的空串）会处理
	 * @param paramMap
	 * @return
	 */
	
	public Map<String,Object> getUpdateSetParam(Map<String,Object> paramMap){
		Object numObj[] = null;//set里的值，与setSql里的字段值顺序对应
		String setSql = "";//生成set预编译子句
		//生成set子句，规则：key作为字段名，value作为字段查询值
		if(paramMap != null && paramMap.keySet() != null && paramMap.keySet().size() > 0){
			String[] param = new String[paramMap.keySet().size()];
			int n = 0;
			for(Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();){
				String key = (String)iter.next();
				Object value = (Object)paramMap.get(key);
				if(key != null && !key.trim().equals("") && value != null){//value为null的不做处理，为""（长度为0的空串）会处理
					setSql = setSql + key + "=?,";
					param[n] = value.toString();
					n++;
				}
			}
			numObj = new Object[n];
			for(int i = 0; i < numObj.length; i++){
				numObj[i] = param[i];
			}
		}
		if(setSql != null && setSql.trim().endsWith(",")){
			setSql = setSql.trim().substring(0,setSql.trim().length() - 1);
		}
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("set", setSql);
		param.put("param", numObj);
		return param;
	}
	
	/**
	 * 执行更新语句
	 * @param map
	 * @param tableName
	 * @return
	 */
	public int updateImageToTable(Map<String ,Object> map,String tableName){
		int index = 0;
		Map<String,Object> setResultMap = getUpdateSetParam(map);
		String setSql = (String)setResultMap.get("set");//获取set子句
		Object setnumObj[] = (Object[])setResultMap.get("param");//获取set子句的参数
		Object numObj[] = null;
		int numlength = 0;
		if(setnumObj != null){
			numlength = numlength + setnumObj.length;
		}
		numObj = new Object[numlength];//定义总参数数组
		if(setnumObj != null && setnumObj.length > 0){//将set参数放在总的参数数组中
			for (int i = 0; i < setnumObj.length; i++) {
				numObj[i] = setnumObj[i];
			}
		}
		
		String sql = "update " + tableName + " set " + setSql;
		logger.info("sql:"+sql);
		logger.info("param:"+java.util.Arrays.toString(numObj));
		try {
			index = jdbcTemplate.update(sql, numObj);
		} catch (DataAccessException e) {
			logger.info(e);
		}
		return index;
		
	}
	
	public List<Map<String, Object>> getTransStatus(Map<String, Object> map){
		logger.info("第三方流水号查询");
		String ID=StringUtil.getPropertyValue(map.get("ID"));
		String sql="select ReleaseTime,FlwCommonId from p_imageinfo where ID='"+ID+"' ";
			return jdbcTemplate.queryForList(sql);
	}
	
	/**
	 * 传入seq名称获得seq
	 */
	public int querySeq(String seqname) {
		
		return this.jdbcTemplate.queryForInt("SELECT "+seqname+".NEXTVAL FROM DUAL");
	}
	
	
	public List<Map<String, Object>> queryList(String sql) {
		List<Map<String,Object>> array = null;
		try {
			array = jdbcTemplate.queryForList(sql);
			if(array != null){
				logger.info("query list size:"+array.size());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			if((e instanceof IncorrectResultSizeDataAccessException)
                    &&((IncorrectResultSizeDataAccessException)e).getActualSize()==0) {
				return null;
			}
		}
		return array;
	}
	public Map<String, Object> queryMap(String sql) {
		List<Map<String, Object>> list = queryList(sql);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 执行sql语句
	 * @param paramList
	 * @param tableName
	 * @return
	 */
	public int insertMapTable(String sql){
		int index = 0;
		try {
			index = jdbcTemplate.update(sql);
		} catch (DataAccessException e) {
			logger.info(e);
		}
		return index;
	}
	/**
	 * sql语句Where后面部分的sql，如Where a=? and b=?  两个问号生成参数数组返回
	 * value为空的不做处理
	 * 需要添加相同的字段作为查询字段键值时，可在字段后面加‘#’，解决Map不能添加相同键值的问题
	 * 查询条件不支持in，not in，exist等负责条件
	 * @param paramMap
	 * @param relationship 查询条件逻辑关系，有 and和or
	 * @return
	 */
	
	public Map<String,Object> getWhereParam(Map<String,Object> paramMap,String relationship){
		Object numObj[] = null;//whereSql里的值，与whereSqlSql里的字段值顺序对应
		String whereSql = "";//生成whereSql预编译子句
		relationship = relationship == null || relationship.trim().equals("") ? "and" : relationship;//查询条件逻辑关系，有 and和or
		//生成whereSql子句，规则：key作为字段名，value作为字段查询值
		if(paramMap != null && paramMap.keySet() != null && paramMap.keySet().size() > 0){
			String[] param = new String[paramMap.keySet().size()];
			int n = 0;
			for(Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();){
				String key = (String)iter.next();
				Object value = paramMap.get(key);
				Object[] valueArray = null;
				if (value != null && value instanceof Object[]) {
					valueArray = (Object[]) value;
				}else if(value != null){
					valueArray = new Object[]{value};
				}
				if(key != null && !key.trim().equals("") && value != null){
					key = key.replaceAll("#", "");//由于Map不能放重复的键，故需要加重复字段做查询条件时可以在字段后面加一个或者多个‘#’，用来区分不同键值
					if(valueArray != null && valueArray.length > 0){
						if(valueArray.length > 1){
							if(valueArray[0] != null && !valueArray[0].toString().trim().equals("")){//value为null的不做处理
								String cond = valueArray[1] == null ? "=" : valueArray[1].toString();
								if(cond.equalsIgnoreCase("like")){
									whereSql = whereSql + key + " " + cond + " ? "+relationship+" ";
									param[n] = "%"+valueArray[0].toString()+"%";
								}else{
									whereSql = whereSql + key + cond + "? "+relationship+" ";
									param[n] = valueArray[0].toString();
								}
								n++;
							}
						}else{//没有关系条件默认=
							if(valueArray[0] != null && !valueArray[0].toString().trim().equals("")){//value为null的不做处理
								whereSql = whereSql + key + " = ? "+relationship+" ";
								param[n] = valueArray[0].toString();
								n++;
							}
						}
					}
				}
			}
			numObj = new Object[n];
			for(int i = 0; i < numObj.length; i++){
				numObj[i] = param[i];
			}
		}
		if(whereSql != null && whereSql.trim().endsWith(relationship)){
			whereSql = whereSql.trim().substring(0,whereSql.trim().length() - relationship.length());
		}
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("where", whereSql);
		param.put("param", numObj);
		return param;
	}

	/**
	 * 执行update语句
	 * @param setParamMap(Map<String,value>) set关键字后面要更新的字段和值    如set a=1,b=2  则设置Map为key=a,value=1;key=b,value=2;
	 * @param whereParamMap(Map<String,new Object[]{value,condition}>) where关键字后面要查询的字段和值   如where a=1 and b>2  则设置Map为key=a,value={1,'='};key=b,value={2,'>'};
	 * 需要添加相同的字段作为查询字段键值时，可在字段后面加‘#’，解决Map不能添加相同键值的问题
	 * @param tableName 更新表名称
	 * @param relationship 每个where条件后面的关系，and或者or
	 */
	public int updateByWhere(Map<String, Object> setParamMap,
			Map<String, Object> whereParamMap,String tableName,String relationship) {
		Map<String,Object> setResultMap = getUpdateSetParam(setParamMap);
		Map<String,Object> whereResultMap = getWhereParam(whereParamMap,relationship);
		String setSql = (String)setResultMap.get("set");//获取set子句
		String whereSql = (String)whereResultMap.get("where");//获取where子句
		Object setnumObj[] = (Object[])setResultMap.get("param");//获取set子句的参数
		Object wherenumObj[] = (Object[])whereResultMap.get("param");//获取where子句的参数
		Object numObj[] = null;
		int numlength = 0;
		if(setnumObj != null){
			numlength = numlength + setnumObj.length;
		}
		if(wherenumObj != null){
			numlength = numlength + wherenumObj.length;
		}
		numObj = new Object[numlength];//定义总参数数组
		int j = 0;//作为where参数的起始下标
		if(setnumObj != null && setnumObj.length > 0){//将set参数放在总的参数数组中
			j = setnumObj.length;
			for (int i = 0; i < setnumObj.length; i++) {
				numObj[i] = setnumObj[i];
			}
		}
		if(wherenumObj != null && wherenumObj.length > 0){//将where参数放在总的参数数组中
			for (int i = 0; i < wherenumObj.length; i++) {
				numObj[j+i] = wherenumObj[i];
			}
		}
		
		String sql = "update " + tableName + " set " + setSql + " where " + whereSql;
		logger.info("sql:"+sql);
		logger.info("param:"+java.util.Arrays.toString(numObj));
		return this.editObject(sql, numObj);
	}
	
	/**
	 * 查询交易序列号
	 */
	public List<Map<String,Object>>  queryTranUid(String strSQL) {
		
		return this.jdbcTemplate.queryForList(strSQL);
	}
}
