package com.lesath.apps.controller;

import com.lesath.apps.util.HTTPMethod;

import java.util.HashMap;
import java.util.Map;

public class APIGatewayRequest {
    String path;
    boolean isBase64Encoded;

    public Map<String, String> headers;
    public Map<String, String> pathParameters = null;
    public Map<String, String> queryStringParameters = null;
    public Map<String, String> stageVariables = null;

    public String body = null;

    public HTTPMethod httpMethod;
    public String resource;

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
