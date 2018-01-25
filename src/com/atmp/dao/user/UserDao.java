package com.atmp.dao.user;

import java.util.Map;

public interface UserDao {
	
	
	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public Map<String, Object> userLogin(Map<String, Object> map);

}
