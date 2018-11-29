package com.lesath.apps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesath.apps.util.LocalDateTimeJsonConvertor;

public abstract class Handler<Request, Response> implements RequestStreamHandler {

	public LambdaLogger logger = null;
	protected static Gson gson;	
	
	JSONObject responseJson;
	
	private InputStream input;
	private OutputStream output;
	private Context context;
	
	private String controllerName;
	
	static {
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConvertor())
				.create();
	}
	
	protected void init(InputStream input, OutputStream output, Context context, String controllerName) {
		this.input = input;
		this.output = output;
		this.context = context;
		this.controllerName = controllerName;
		
		logger = context.getLogger();
		
		if (logger != null) logger.log("In handle request for " + controllerName);
		
		JSONObject header = new JSONObject();
    	header.put("Content-Type",  "application/json");  // not sure if needed anymore?
		header.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    header.put("Access-Control-Allow-Origin",  "*");
	    
	    responseJson = new JSONObject();
	    responseJson.put("headers", header);
	}
	
	protected Request parseInput(Class<Request> clazz) throws ParseException, IOException {
		String body;
		
		try {
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	    	JSONParser parser = new JSONParser();
	    	JSONObject event = (JSONObject) parser.parse(reader);
	    	
	    	logger.log("event: " + event.toJSONString());
	    	
	    	String method = (String) event.get("httpMethod");
	    	
	    	System.out.println("METHOD: " + method);
	    	
			body = event.get("body").toString();
	    } catch(ParseException e) {
	    	logger.log(e.toString());
	    	
	    	throw new ParseException(0);
	    }
		
		return gson.fromJson(body, clazz);
	}
	
	protected void respond(Response r) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
}
