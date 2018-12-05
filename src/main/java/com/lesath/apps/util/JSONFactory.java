package com.lesath.apps.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.lesath.apps.controller.LambdaHandler;
import org.json.simple.parser.ParseException;

public class JSONFactory {
    public static JsonPrimitive objectToJsonObject(Object o) throws ParseException {
        JsonElement jsonElement = LambdaHandler.gson.toJsonTree(o);
        return (JsonPrimitive) jsonElement;
    }
}
