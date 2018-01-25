package com.atmp.bean;

public class ResultBean<T>{
	
	public String status;
	public String message;
	public T data;
	
	public ResultBean(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public ResultBean() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	

}
