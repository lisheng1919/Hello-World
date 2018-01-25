package com.atmp.service;

import java.util.Map;

import com.atmp.bean.ResultBean;


public interface AccountInfoInterface {
	
	/**
	 * 账号信息查询
	 * @param mHead
	 * @param mBody
	 * @return
	 */
	public Map<String, Object> getAccountInfo(Map<String, Object> map);
	
	/**
	 * 回单卡账号信息查询
	 * @param mHead
	 * @param mBody
	 * @return
	 */
	public Map<String, Object> getAccCardInfo(Map<String, Object> map);
	
	/**
	 * 查询指定账号的电话号码
	 * @param map
	 * @return
	 */
	public Map<String, Object> getCardPhone(Map<String, Object> map);
	
	/**
	 * 支付指令查询（支付凭证号）
	 */
	public Map<String, Object> returnAcctByVoucherNo(Map<String,String> map);
	/**
	 * 账户密码信息查询
	 */
	public Map<String, Object> returnAcctByPwd(Map<String,String> map);
	
	/**
	 * 交易明细查询
	 * @param map
	 * @return
	 */
	public Map<String,Object> queryDetailData(Map<String,String> map);
}
