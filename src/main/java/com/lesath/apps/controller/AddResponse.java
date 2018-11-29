package com.lesath.apps.controller;

public class AddResponse {
	public final double value;
	public final int httpCode;
	public final String errorMessage;
	
	public AddResponse (double v, int code, String msg) {
		this.value = v;
		this.httpCode = code;
		errorMessage = msg;
	}
	
	// 200 means success
	public AddResponse (double v) {
		this.value = v;
		this.httpCode = 200;
		this.errorMessage = "";
	}
	
	public String toString() {
		return "Value(" + value + ")";
	}
}
