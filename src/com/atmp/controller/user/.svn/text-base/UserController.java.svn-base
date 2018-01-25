package com.atmp.controller.user;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atmp.controller.AccountInfoController;
import com.atmp.service.user.UserServiceInterface;
import com.atmp.util.HttpJsonResult;
import com.atmp.util.StringUtil;

@Controller
public class UserController {
	
	public UserController(){}
	
	private static final Logger logger= Logger.getLogger(UserController.class);
	
	@Resource 
	private UserServiceInterface userService;
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/atmc/UserName")
	@ResponseBody
	public void userLogin(HttpServletRequest request,HttpServletResponse response){
		Map<String ,Object> result=new HashMap<String,Object>();
		Map<String, Object> map=new TreeMap<String, Object>();
		Enumeration arg_array=request.getParameterNames();
		while(arg_array.hasMoreElements()){
			String arg_key=arg_array.nextElement().toString();
			String value=StringUtil.getPropertyValue(request.getParameter(arg_key));
			map.put(arg_key, value);
		}
		String username=StringUtil.getPropertyValue(map.get("teller"));
		if(StringUtils.isNotEmpty(username)){
				result=userService.userLogin(map);
		}else{
			result.put("StatusCode", "111111");
			result.put("ServerStatusCode", "柜员号不能为空");
			result.put("checkStatus","false");
			logger.info("柜员号不能为空");
		}
		
		HttpJsonResult.writerMap(result,response);
	}

}
