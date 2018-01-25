package com.atmp.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.atmp.service.impl.ImageReceiveImpl;



public class HttpJsonResult<T> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8637111820477625638L;
    private static final Logger logger= Logger.getLogger(ImageReceiveImpl.class);
    public HttpJsonResult() {

    }

    public HttpJsonResult(T data) {
        this.data = data;
    }

    public HttpJsonResult(String errorMessage) {
        this.success = false;
        this.message = errorMessage;
    }

    private Boolean success = true;

    public Boolean getSuccess() {
        return this.success;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.success = false;
        this.message = message;
    }

    private Integer totalCount = 0;

    public void setTotalCount(Integer count) {
        this.totalCount = count;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }
    
  
    
    /**
     * 返回json对象形式
     * 接收方式示例：JSONObject.getString("name")
     * @param map map对象
     * @param response
     */
    public static void writerMap(Map<String, Object> map, HttpServletResponse response){
    	String json = JSONObject.valueToString(map);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        }
        out.print(json);
        out.flush();
        out.close();
    }
    
    /**
     * 返回对象形式
     * @param obj=json.get("key"),string,int,object等
     * @param response
     */
    public static void writerObj(Object obj, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        }
        out.print(obj);
        out.flush();
        out.close();
    }
}
