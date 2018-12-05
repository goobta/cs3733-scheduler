package com.lesath.apps.controller;

import java.util.HashMap;
import java.util.Map;

public class LambdaResponse {
    public Map<String, String> headers = null;
    // public int statusCode;
    public String body;

    public boolean addHeader(String key, String value) {
        if(this.headers == null) {
            this.headers = new HashMap<>();
        }

        this.headers.put(key, value);
        return true;
    }

//    public boolean setStatusCode(int code) {
//        this.statusCode = code;
//        return true;
//    }
//
    public boolean setBody(String body) {
        this.body = body;
        return true;
    }
}
