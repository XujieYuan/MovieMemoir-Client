package com.m.moviememoir.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ArrayJson {
    public static <T> List<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        List<T> list = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            list.add(new Gson().fromJson(jsonObject, clazz));
        }
        return list;
    }
}
