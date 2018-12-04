package com.lesath.apps.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class HTTPMethodConvertor implements JsonDeserializer<HTTPMethod> {
    @Override
    public HTTPMethod deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return HTTPMethod.valueOf(json.getAsString());
    }
}
