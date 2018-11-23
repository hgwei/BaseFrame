package com.hgw.baseframe.constants;

import com.hgw.baseframe.bean.LoginEntry;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 描述：请求数据封装
 * @author hgw
 */
public class RequestData {

    /**
     * 请求头部 Header
     *
     * @return
     */
    public static HashMap<String, String> getHeaders() {
        //header不支持中文，不允许有特殊字符
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
//        long timeMillis = System.currentTimeMillis();//时间戳
////        headers.put("timestamp", timeMillis + "");//当前请求时间戳
////        headers.put("clientType", "android");//客户端类型
        return headers;
    }

    /**
     * Common：公共请求对象（用于post jsonString请求）
     * @return JSONObject
     */
    public static JSONObject getCommonObject() {
        JSONObject mJSONObject = new JSONObject();
//        try {
//            mJSONObject.put("appId", UrlConstants.APPID);
//            mJSONObject.put("token", LoginEntry.Instance().getToken());
//            mJSONObject.put("version", "1");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return mJSONObject;
    }

    /**
     * Common：公共请求对象（用于post key value请求）
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> getCommonHashMap() {
        HashMap<String, String> params = new HashMap<>();
        if(LoginEntry.Instance().getToken()!=null)
            params.put("token", LoginEntry.Instance().getToken());
        return params;
    }

    /*********************下面是每个请求接口的参数配置***************************/

    /**
     * 获取系统时间
     * @return
     */
    public static HashMap<String, String> postGetSystemTime() {
        HashMap<String, String> params = getCommonHashMap();
        return params;
    }

    /**
     * 登录 (Post Key Value 请求参数示例)
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static HashMap<String, String> getLogin(String username, String password) {
        HashMap<String, String> params = getCommonHashMap();
        params.put("username", username);
        params.put("password", password);
        return params;
    }

    /**
     * 登录 (Post String 请求参数示例)
     * @param username 用户名
     * @param password 密码
     * @return
     */

    public static String getLogin2(String username, String password) {
        JSONObject mJSONObject = getCommonObject();
        try {
            mJSONObject.put("username", username);
            mJSONObject.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mJSONObject.toString();
    }

}