package com.lesath.apps.controller.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesath.apps.controller.Handler;
import com.lesath.apps.util.HTTPMethod;
import com.lesath.apps.util.LocalDateTimeJsonConvertor;

public class APIGatewayRequest {
	String path = "/";
	boolean isBase64Encoded = false;
	
	Map<String, String> headers;
	Map<String, String> pathParameters = null;
	Map<String, String> queryStringParameters = null;
	Map<String, String> stageVariables = null;
	
	String body = null;
	
	HTTPMethod httpMethod;
	String resource = "test";
	
	public APIGatewayRequest() {
		headers = new HashMap<>();
		
		headers.put("Accept", "*/*");
	    headers.put("CloudFront-Viewer-Country", "US");
	    headers.put("CloudFront-Forwarded-Proto", "https");
	    headers.put("CloudFront-Is-Tablet-Viewer", "false");
	    headers.put("origin", "https://schedulerbucket2.s3.us-east-2.amazonaws.com");
	    headers.put("CloudFront-Is-Mobile-Viewer", "false");
	    headers.put("Referer", "https://schedulerbucket2.s3.us-east-2.amazonaws.com/index.html?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20181130T030020Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604796&X-Amz-Credential=AKIAIKKEDML7KJBAQLYQ%2F20181130%2Fus-east-2%2Fs3%2Faws4_request&X-Amz-Signature=4633ad98eb02f3794685e1429f6e19f50a82856d061f0c70ed738d903816ed37");
	    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36 Vivaldi/2.1.1337.36");
	    headers.put("X-Forwarded-Proto", "https");
	    headers.put("CloudFront-Is-SmartTV-Viewer", "false");
	    headers.put("Host", "wasu526ybc.execute-api.us-east-2.amazonaws.com");
	    headers.put("Accept-Encoding", "gzip, deflate, br");
	    headers.put("X-Forwarded-Port", "443");
	    headers.put("X-Amzn-Trace-Id", "Root=1-5c05cb22-a5b351dcef6b5500dfdf9b20");
	    headers.put("Via", "2.0 3ab520e9829594afd161e678c0434f7b.cloudfront.net (CloudFront)");
	    headers.put("X-Amz-Cf-Id", "3czxhcwDm0fDN14cfHWN9az1Ax1JPKl15Dput6bOz6JZEZuOEzIO0Q==");
	    headers.put("X-Forwarded-For", "130.215.208.197, 70.132.21.81");
	    headers.put("content-type", "application/json");
	    headers.put("Accept-Language", "en-US,en;q=0.9");
	    headers.put("CloudFront-Is-Desktop-Viewer", "true");
	}
	
	public InputStream generateRequest(HTTPMethod method) {
		this.httpMethod = method;

		String rep = Handler.gson.toJson(this);

		return new ByteArrayInputStream(rep.getBytes());
	}

	public boolean addHeader(String key, String value) {
		if (this.headers == null) {
			this.headers = new HashMap<>();
		}

		this.headers.put(key, value);
		return true;
	}

	public boolean addPathParamater(String key, String value) {
		if (this.pathParameters == null) {
			this.pathParameters = new HashMap<>();
		}

		this.pathParameters.put(key, value);
		return true;
	}

	public boolean addQueryParameter(String key, String value) {
		if (this.queryStringParameters == null) {
			this.queryStringParameters = new HashMap<>();
		}

		this.queryStringParameters.put(key, value);
		return true;
	}

	public boolean addStageVariable(String key, String value) {
		if (this.stageVariables == null) {
			this.stageVariables = new HashMap<>();
		}

		this.stageVariables.put(key, value);
		return true;
	}

	public boolean setBody(String body) {
		this.body = body;

		return true;
	}
}
