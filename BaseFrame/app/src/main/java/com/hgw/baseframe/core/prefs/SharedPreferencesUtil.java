package com.hgw.baseframe.core.prefs;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import com.hgw.baseframe.app.BaseFrameApp;
import com.hgw.baseframe.constants.Constants;

/**
 * 描述：SharedPreferences
 * @author hgw
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil mSPHelper=null;
    private final String PREFERENCE_NAME = Constants.PREFERENCE_NAME;
    private final SharedPreferences mPreferences;

    /**
     * 实现单例模式
     * */
    public static synchronized SharedPreferencesUtil getInstance(){
        if (mSPHelper == null) {
            mSPHelper = new SharedPreferencesUtil();
        }
        return mSPHelper;
    }

    public SharedPreferencesUtil() {
        mPreferences = BaseFrameApp.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存字符串数据
     *
     * @param key
     * @param value
     */
    public void saveString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    /**
     * 获取字符串数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    /**
     * 保存整型数据
     *
     * @param key
     * @param value
     */
    public void saveInt(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    /**
     * 获取整型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    /**
     * 保存布尔值数据
     *
     * @param key
     * @param value
     */
    public void saveBoolean(String key, Boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取布尔值数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public Boolean getBoolean(String key, Boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    /**
     * 移出数据
     */
    public boolean remove(String key) {
        return mPreferences.edit().remove(key).commit();
    }

    /**
     * 清除所有数据
     */
    public boolean clear(String key) {
        return mPreferences.edit().clear().commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return mPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }
}
