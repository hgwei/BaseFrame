package com.hgw.baseframe.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：Json的封装（依赖于Gson库）
 * @author hgw
 * */
public class JSONUtil {

    /**
     * jsonString 转换为 JavaBean
     * 
     * @param jsonStr
     * @param clazz 不能是abstract类
     * @return
     */
    public static final <T> T parseToJavaBean(String jsonStr, Class<T> clazz) {
        try{
            if(jsonStr == null){
                return null;
            }
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * jsonString 转换为 List
     * 
     * @param jsonStr
     * @param clazz 不能是abstract类
     * @return
     */
    public static <T> ArrayList<T> parseToList(String jsonStr, Class<T> clazz) {
        Type type = new TypeToken<List<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjs = new Gson().fromJson(jsonStr, type);

        ArrayList<T> listOfT = new ArrayList<T>();
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(new Gson().fromJson(jsonObj, clazz));
        }

        return listOfT;
    }

    /**
     * javaBean 转换为 jsonString
     *
     * @param javaBean
     * @param clazz 不能是abstract类
     * @return
     */
    public static <T> String parseToJSONString(Object javaBean, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.toJson(javaBean, clazz);
    }

    /**
     * 将List<?> 转换为 jsonString
     *
     * @param list
     * @param clazz 不能是abstract类
     * @return
     */
    public static <T> String listToJSONString(List<?> list, Class<T> clazz) {
        List<T> listT = listToList(list, clazz);
        JSONArray jsonArray = new JSONArray();
        for (T t : listT) {
            jsonArray.put(parseToJSONObject(t, clazz));
        }
        return jsonArray.toString();
    }

    /**
     * 对象转JSON对象
     *
     * @param javaBean
     * @param clazz 不能是abstract类
     * @return
     */
    public static <T> JSONObject parseToJSONObject(Object javaBean, Class<T> clazz) {
        JSONObject json = null;
        try {
            json = new JSONObject(parseToJSONString(javaBean, clazz));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * 将List<?>转换为List<T>
     * 
     * @param list
     * @param clazz 不能是abstract类
     * @return
     */
    public static <T> ArrayList<T> listToList(List<?> list, Class<T> clazz) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjs = gson.fromJson(gson.toJson(list), type);

        ArrayList<T> listOfT = new ArrayList<T>();
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(gson.fromJson(jsonObj, clazz));
        }
        return listOfT;
    }
}
