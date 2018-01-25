package com.atmp.service.user;

import java.util.Map;

public interface UserServiceInterface {

	/**
	 * 用户登录
	 * @param map
	 * @return
	 */
	public Map<String, Object> userLogin(Map<String, Object> map);
}
