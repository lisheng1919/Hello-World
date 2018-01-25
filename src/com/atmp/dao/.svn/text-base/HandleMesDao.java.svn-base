package com.atmp.dao;

import java.util.List;
import java.util.Map;

public interface HandleMesDao {
	
	/**
	 * 执行插入语句
	 * @param map
	 * @param tableName
	 * @return
	 */
	public int insertMapToTable(Map<String ,Object> map,String tableName);
	
	/**
	 * 执行更新语句
	 * @param map
	 * @param tableName
	 * @return
	 */
	public int updateImageToTable(Map<String ,Object>map,String tableName);
	
	/**
	 * 第三方流水号查询
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getTransStatus(Map<String, Object> map);
	
	/**
	 * 获取Seq序列
	 * @param seqname
	 * @return
	 */
	public int querySeq(String seqName);
	
	/**
	 * 查询交易序列号
	 * @param seqname
	 * @return
	 */
	public List<Map<String,Object>> queryTranUid(String strDate);
	
	/**
	 * 查询单条，返回Map<String,Object>
	 * @param sql
	 * @return
	 */
	public Map<String,Object> queryMap(String strSQL);
	
	/**
	 * 执行sql语句
	 * @param paramList
	 * @param tableName
	 * @return
	 */
	public int insertMapTable(String sql);
	
	/**
	 * 执行update语句
	 * @param setParamMap(Map<String,value>) set关键字后面要更新的字段和值    如set a=1,b=2  则设置Map为key=a,value=1;key=b,value=2;
	 * @param whereParamMap(Map<String,new Object[]{value,condition}>) where关键字后面要查询的字段和值   如where a=1 and b>2  则设置Map为key=a,value={1,'='};key=b,value={2,'>'};
	 * @param tableName 更新表名称
	 * @param relationship 每个where条件后面的关系，and或者or
	 */
	public int updateByWhere(Map<String, Object> setParamMap,
			Map<String, Object> whereParamMap,String tableName,String relationship);
	
}
