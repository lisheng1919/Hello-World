package com.atmp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.atmp.dao.DataQueryDao;
import com.atmp.service.DataQueryInterface;
@Service("dataQuery")
public class DataQueryImpl implements DataQueryInterface {

	@Resource
	private  DataQueryDao dataQueryDao;

	@Override
	public List<Map<String, Object>> gettitleListQuery(Map<String, Object> map) {
		
		return dataQueryDao.gettitleListQuery(map);
	}
	
	@Override
	public List<Map<String, Object>> getDateListQuery(Map<String, Object> map){
		
		return dataQueryDao.getDateListQuery(map);
	}
	
	@Override
	public List<Map<String, Object>> getDateTimeListQuery(Map<String, Object> map){
		
		return dataQueryDao.getDateTimeListQuery(map);
	}
	
	@Override
	public int startClean(Map<String, Object> map){

		return dataQueryDao.startClean(map);
	}

	@Override
	public List<Map<String, Object>> getDepositoryInfo(Map<String, Object> map) {
		return dataQueryDao.getDepositoryInfo(map);
	}

}
