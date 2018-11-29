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
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.util.LocalDateTimeJsonConvertor;

public class CreateScheduleHandler implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	LambdaLogger logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestStreamHandler");

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConvertor())
				.create();
		
		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		CreateScheduleResponse response = null;
		
		// extract body from incoming HTTP POST request. If any error, then return 422 error
		String body;
		boolean processed = false;
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());
			
			String method = event.get("httpMethod").toString();
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request");
				responseJson.put("body", "{}");  // nothing needs to be sent back.
		        processed = true;
		        body = null;
			} else {
				body = event.get("body").toString();
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
				}
			}
    	} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new CreateScheduleResponse(422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}
    	
    	if (!processed) {
    		ScheduleConfig sc = gson.fromJson(body, ScheduleConfig.class);
    		String uuid = new CreateScheduleRequest(sc).execute();
    		
    		CreateScheduleResponse res;
    		if(!uuid.isEmpty()) {
    			res = new CreateScheduleResponse(204, uuid);
    		} else {
    			res = new CreateScheduleResponse(500);
    		}
    		
    		responseJson.put("body", gson.toJson(res));
    	}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
    	OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();		
    }
}