package com.lesath.apps.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class CreateScheduleHandler implements RequestStreamHandler {

	private static final JSONObject JSONObject = null;
	public LambdaLogger logger = null;
	
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	logger = context.getLogger();
    	
    	if (logger != null) logger.log("In CreateSchedule handleRequest");
    	
 
    }
}
