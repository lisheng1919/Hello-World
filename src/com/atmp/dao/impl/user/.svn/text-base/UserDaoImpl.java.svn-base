package com.atmp.dao.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.atmp.dao.user.UserDao;
import com.atmp.service.impl.ImageReceiveImpl;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Resource
	public JdbcTemplate jdbcTemplate;
	
	private static final Logger logger= Logger.getLogger(UserDaoImpl.class);
	@Override
	public Map<String, Object> userLogin(Map<String, Object> map){
			Map<String, Object> result=new HashMap<String, Object>();
			
			try{
				String strSQL="select pwd from p_user where username=?";
				List<Map<String, Object>> list=jdbcTemplate.queryForList(strSQL,map.get("username"));
				String pwd =list.get(0).get("pwd").toString();
				result.put("pwd", pwd);
			}catch(Exception e){
				result.put("pwd", "0");
				logger.error(e.getMessage(),e);
			}
			
         	return result;
	}

}
