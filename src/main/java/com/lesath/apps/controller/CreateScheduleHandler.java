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

public class CreateScheduleHandler extends Handler<ScheduleConfig, CreateScheduleResponse> {
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	init(input, output, context, "Create Schedule Controller");
    	
    	CreateScheduleResponse res;
    	ScheduleConfig sc;
    	
    	try {
    		sc = parseInput(ScheduleConfig.class);
    	} catch (ParseException e) {
    		res = new CreateScheduleResponse(400);
    		
    		respond(res);
    		return;
    	}
	    	
    	if(new CreateScheduleRequest(sc).execute()) {
	    	res = new CreateScheduleResponse(204);
    	} else {
	    	res = new CreateScheduleResponse(500);
    	}
	    
	    respond(res);
    }
}