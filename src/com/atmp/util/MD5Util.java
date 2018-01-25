package com.atmp.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;



public class MD5Util
{
	
	private static final Logger logger= Logger.getLogger(MD5Util.class);
	
	// MD5加码  生成32位md5码
	public static String string2MD5(String inStr)
	{
		MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++)
		{
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	// 加密解密算法     执行一次加密，两次解密  
	public static String convertMD5(String inStr)
	{
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++)
		{
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	

	// 测试主函数
	public static void main(String args[])
	{
		String s = new String("Hello World!");
		 s = new String("123");
		 //202cb962ac59075b964b07152d234b70
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + string2MD5(s));
		System.out.println("md5 length:"+string2MD5(s).length());
		System.out.println("加密的：" + convertMD5(s));
		System.out.println("解密1次：" + convertMD5(convertMD5("202cb962ac59075b964b07152d234b70")));
		System.out.println("解密2次的：" + convertMD5(convertMD5(s)));
	}
}


