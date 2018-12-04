package com.lesath.apps.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesath.apps.util.HTTPMethod;
import com.lesath.apps.util.HTTPMethodConvertor;
import com.lesath.apps.util.LocalDateTimeJsonConvertor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public abstract class LambdaHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;

	public static Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonConvertor())
				.registerTypeAdapter(HTTPMethod.class, new HTTPMethodConvertor())
				.create();

	protected LambdaResponse response;

	protected String controllerName;
	protected List<HTTPMethod> handledMethods;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		this.init();

		logger = context.getLogger();
		logger.log("Loading Java Lambda Handler for " + controllerName);

		this.response = new LambdaResponse();
		response.addHeader("Content-Type",  "application/json");
		response.addHeader("Access-Control-Allow-Methods", this.generateMethodsString());
		response.addHeader("Access-Control-Allow-Origin",  "*");

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);

			System.out.println(event.toJSONString());

			APIGatewayRequest request = gson.fromJson(event.toJSONString(), APIGatewayRequest.class);

			switch (request.httpMethod) {
				case OPTIONS:
					handleOPTIONS();
					break;
				case PUT:
					handlePUT(request);
					break;
				case POST:
					handlePOST(request);
					break;
				case GET:
					handleGET(request);
					break;
				case DELETE:
					handleDELETE(request);
					break;
				default:
					response.setStatusCode(500);
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
			logger.log(pe.getStackTrace().toString());

			response.setStatusCode(400);
		}

		String responseJson = gson.toJson(response);
		logger.log("Lambda Response: " + responseJson);

		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson);
		writer.close();
	}

	private String generateMethodsString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < handledMethods.size(); i++) {
			sb.append(handledMethods.get(i).toString());
			sb.append(",");
		}

		sb.append("OPTIONS");

		return(sb.toString());
	}

	protected void handleOPTIONS() {
		logger.log("Options Request");

		response.body = "{}";
	}

	protected abstract boolean init();
	protected abstract boolean handlePUT(APIGatewayRequest request);
	protected abstract boolean handlePOST(APIGatewayRequest request);
	protected abstract boolean handleGET(APIGatewayRequest request);
	protected abstract boolean handleDELETE(APIGatewayRequest request);
}
