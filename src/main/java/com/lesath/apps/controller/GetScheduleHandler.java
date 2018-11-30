package com.lesath.apps.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.util.LocalDateTimeJsonConvertor;

public class GetScheduleHandler implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
    	LambdaLogger logger = context.getLogger();
		ScheduleDAO dao = new ScheduleDAO();
		
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConvertor())
				.create();
		
		ArrayList<Schedule> schedules;
		JSONObject response = new JSONObject();
		String body;
		Schedule theseSchedules = null;
		JSONParser parser = new JSONParser();
		JSONObject headerJson = new JSONObject();
		
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		response.put("headers", headerJson);
		
		try {
			schedules = dao.getSchedule();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONObject event = (JSONObject) parser.parse(reader);
			body = event.get("queryStringParameters").toString();
			String uuid = ((JSONObject) event.get("queryStringParameters")).get("uuid").toString();
			logger.log("event:" + event.toJSONString());
			//GetScheduleRequest req = gson.fromJson(body, GetScheduleRequest.class);
			
			for(Schedule x : schedules) {
				if(x.getUuid().equals(uuid)) {
					theseSchedules = x;
				}
			}
			
			if(theseSchedules != null) {	// Schedule was found
				response.put("body", gson.toJson(theseSchedules));
			}
			else {	// Schedule was not found
				response.put("body", "error");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.log("Something broke...");
		}
		
		logger.log("end result:" + response.toJSONString());
        logger.log(response.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(response.toJSONString());  
        writer.close();
	}

}
