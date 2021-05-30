package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class JsonMap {
    private final JsonObject map;

    public JsonMap() {
        this.map = new JsonObject();
    }

    public static JsonMap fromJson(String json) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.fromJson(json, JsonMap.class);
    }

    public <T> void put(String key, T value, Type type) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        map.add(key, gson.toJsonTree(value, type));
    }

    public <T> T get(String key, Type type) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.fromJson(map.get(key), type);
    }

    public boolean contains(String key) {
        return map.has(key);
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(this);
    }
}
