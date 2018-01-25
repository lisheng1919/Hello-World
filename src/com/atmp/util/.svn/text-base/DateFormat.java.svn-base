package com.atmp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.atmp.system.CustomDriverManagerConnectionProvider;



public class DateFormat {
	CustomDriverManagerConnectionProvider customConfigInfo=new CustomDriverManagerConnectionProvider();
	/**
	 * 获取8位当前日期yyyyMMdd
	 * @return
	 */
	public String getCurrentDate(){
		SimpleDateFormat sDate=new SimpleDateFormat("yyyyMMdd");
		String date=sDate.format(new Date());
		return date;
	}
	/**
	 * 获取6位当前时间HHMMSS
	 * @return
	 */
	public String getCurrentDateTime(){
		SimpleDateFormat sTime=new SimpleDateFormat("HHmmss");
		String date=sTime.format(new Date());
		return date;
	}
	
	/**
	 * 获取当前时间yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public String getCurrentDTime(){
		SimpleDateFormat sTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sTime.format(new Date());
		return date;
	}
	
	/**
	 * 获取n天前日期
	 * @return
	 */
	public String getBeforeTime(){
		SimpleDateFormat sTime=new SimpleDateFormat("yyyyMMdd");
		Calendar c=Calendar.getInstance();
		int idate=Integer.parseInt(customConfigInfo.getContextProperty("deleteDate"));
		c.add(Calendar.DATE, -idate);
		Date monday=c.getTime();
		String date=sTime.format(monday);
		return date;
	}
}
