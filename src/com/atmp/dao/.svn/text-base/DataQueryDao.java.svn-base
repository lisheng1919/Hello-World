package com.atmp.dao;

import java.util.List;
import java.util.Map;

public interface DataQueryDao {

	/**
	 * 查询轧帐的头信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> gettitleListQuery(Map<String, Object> map);

    
	/**
	 * 查询轧体信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getDateListQuery(Map<String, Object> map);

	/**
	 * 开始清机
	 * @param map
	 * @return
	 */
	int startClean(Map<String, Object> map);

	/**
	 * 查询特定日期轧账信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getDateTimeListQuery(Map<String, Object> map);

	/**
	 * 查询特定日期存管票据信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getDepositoryInfo(Map<String, Object> map);

}
