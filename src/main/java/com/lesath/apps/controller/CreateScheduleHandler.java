package com.lesath.apps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.util.LocalDateTimeJsonConvertor;

public class CreateScheduleHandler implements RequestStreamHandler {

	private static final JSONObject JSONObject = null;
	public LambdaLogger logger = null;

	private static Gson gson;
	
	static {
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConvertor())
				.create();
	}
	
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	logger = context.getLogger();
    	
    	if (logger != null) logger.log("In CreateSchedule handleRequest");
    	
    	JSONObject header = new JSONObject();
    	header.put("Content-Type",  "application/json");  // not sure if needed anymore?
		header.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    header.put("Access-Control-Allow-Origin",  "*");
	    
	    JSONObject responseJson = new JSONObject();
	    responseJson.put("headers", header);
	    
	    String body = null;
	    boolean processed = false;
	    
	    try {
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	    	JSONParser parser = new JSONParser();
	    	JSONObject event = (JSONObject) parser.parse(reader);
	    	
	    	logger.log("event: " + event.toJSONString());
	    	
	    	String method = (String) event.get("httpMethod");
	    	
	    	System.out.println("METHOD: " + method);
	    	if (event.get("body") != null) {
    			body = event.get("body").toString();
    		}
	    } catch(ParseException e) {
	    	logger.log(e.toString());
	    	processed = true;
	    	body = null;
	    }
	    
	    if (!processed) {
	    	ScheduleConfig sc = gson.fromJson(body, ScheduleConfig.class);
	    	
	    	if(new CreateScheduleRequest(sc).execute()) {
	    		
	    	} else {
	    		
	    	}
	    }
    }
}
