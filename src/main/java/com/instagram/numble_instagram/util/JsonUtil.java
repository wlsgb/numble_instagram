package com.instagram.numble_instagram.util;

import com.google.gson.Gson;

public class JsonUtil {

    /**
     * Object to JsonString
     */
    public static String objectToJsonStr(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * JsonString to Object
     */
    public static <T> T JsonStrToObject(String jsonStr, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }
}
