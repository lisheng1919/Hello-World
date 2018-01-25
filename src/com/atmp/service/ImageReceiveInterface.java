package com.atmp.service;

import java.util.Map;

public interface ImageReceiveInterface {
	
	
	/**
	 * 存储上送数据
	 * @param map
	 * @return
	 */
	public Map<String, Object> addReceiveInfo(Map<String, Object> map);
	/**
	 * OCX版本查询
	 * @param request
	 * @param response
	 */
	public Map<String, Object> getBtmOcxUpdate(Map<String, Object> map);
	
}
