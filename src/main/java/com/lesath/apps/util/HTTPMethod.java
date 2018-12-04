package com.lesath.apps.util;

public enum HTTPMethod {
    OPTIONS("OPTIONS"),
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");


    private final String method;

    HTTPMethod(final String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return this.method;
    }
}
