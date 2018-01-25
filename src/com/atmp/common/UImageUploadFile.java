package com.atmp.common;


import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.atmp.system.CustomDriverManagerConnectionProvider;
import com.atmp.util.StringUtil;

import cn.net.sinodata.uimage.common.message.IMessage;
import cn.net.sinodata.uimage.common.message.MessageParamVO;
import cn.net.sinodata.uimage.common.message.MinaExternalMessage;

public class UImageUploadFile {
	
	CustomDriverManagerConnectionProvider customConfigInfo=new CustomDriverManagerConnectionProvider();
	private String imageIp =customConfigInfo.getContextProperty("imageIp");
	private int imagePort =Integer.parseInt(customConfigInfo.getContextProperty("imagePort"));
	private String imageUser =customConfigInfo.getContextProperty("imageUser");
	private String imagePwd =customConfigInfo.getContextProperty("imageUser");
	private IMessage messsage;
	private String fileType ;
	/**
	 * 获取影像套号
	 * @throws Exception
	 */
	public void genFileType() throws Exception{
		messsage = new MinaExternalMessage(imageIp, imagePort,	30, imageUser, imagePwd);
		MessageParamVO sndInfoVo=new MessageParamVO();
		sndInfoVo.setMateData("11:|11:|99:|:|");//元素据信息
		sndInfoVo.setSts("001"); //系统编号
		fileType = messsage.getInformation(sndInfoVo).getFileType();
	}
	
	/**
	 * 
	 * @param sequence 序号
	 * @param FilePath 影像路径
	 * @param filename 影像名
	 * @param ItemNo   机具ID
	 * @return 
	 * @throws Exception
	 */
	public String uploadFile(Map<String , Object> map) throws Exception{
		this.genFileType();
		String ItemNo=customConfigInfo.getContextProperty("ItemNo");
		String FrontImage=StringUtil.getPropertyValue(map.get("FrontImage"));
		String FrontImagePath=StringUtil.getPropertyValue(map.get("FrontImagePath"));
		String BackImage=StringUtil.getPropertyValue(map.get("BackImage"));
		String BackImagePath=StringUtil.getPropertyValue(map.get("BackImagePath"));
		String VoucherImage=StringUtil.getPropertyValue(map.get("VoucherImage"));
	    String VoucherImagePath=StringUtil.getPropertyValue(map.get("VoucherImagePath"));
	    MessageParamVO vo=new MessageParamVO();
		vo.setType(IMessage.upload_file);
		vo.setMateData("fds:|fas:|");
		vo.setImgType("0002");
		vo.setFileType(fileType);
		vo.setItemNo(ItemNo);
		if(StringUtils.isNotEmpty(FrontImage)){
			vo.setFilePath(FrontImagePath);
			vo.setFileName(FrontImage);
			vo.setSequence("01");
			messsage.upload(vo);
		}
		if(StringUtils.isNotEmpty(BackImage)){
			vo.setFileName(BackImage);
			vo.setFilePath(BackImagePath);
			vo.setSequence("02");
			messsage.upload(vo);
		}
		if(StringUtils.isNotEmpty(VoucherImage)){
			vo.setFileName(VoucherImage);
			vo.setFilePath(VoucherImagePath);
			vo.setSequence("03");
			messsage.upload(vo);
		}
		return fileType;

	}
}