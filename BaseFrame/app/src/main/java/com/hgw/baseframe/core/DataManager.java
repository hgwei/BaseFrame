package com.hgw.baseframe.core;

import com.hgw.baseframe.app.Constants;
import com.hgw.baseframe.core.prefs.SharedPreferencesUtil;

/**
 * 描述：DataManager
 * @author hgw
 */
public class DataManager {

    public DataManager() {
    }


    /********************** sharedPreferences start *******************/
    /**
     * 保存登录账号
     * @param account
     * */
    public void setLoginAccount(String account) {
        SharedPreferencesUtil.getInstance().saveString(Constants.ACCOUNT,account);
    }

    /**
     * 获取登录账号
     * @return LoginAccount
     * */
    public String getLoginAccount() {
        return SharedPreferencesUtil.getInstance().getString(Constants.ACCOUNT,"");
    }

    /**
     * 保存登录密码（需加密）
     * @param password
     * */
    public void setLoginPassword(String password) {
        SharedPreferencesUtil.getInstance().saveString(Constants.PASSWORD,password);
    }

    /**
     * 获取登录密码
     * @return LoginPassword
     * */
    public String getLoginPassword() {
        return SharedPreferencesUtil.getInstance().getString(Constants.PASSWORD, "");
    }

    /**
     * 保存登录状态
     * @param isLogin （true：已登录；false：未登录）
     * */
    public void setLoginStatus(boolean isLogin) {
        SharedPreferencesUtil.getInstance().saveBoolean(Constants.LOGIN_STATUS,isLogin);
    }

    /**
     * 获取登录状态
     * @return  isLogin （true：已登录；false：未登录）
     * */
    public boolean getLoginStatus() {
        return SharedPreferencesUtil.getInstance().getBoolean(Constants.LOGIN_STATUS, false);
    }

    /**
     * 保存是否第一次启动
     * @param IsFirstStart （true：是；false：否）
     * */
    public void setIsFirstStart(boolean IsFirstStart) {
        SharedPreferencesUtil.getInstance().saveBoolean(Constants.IS_FIRST_START,IsFirstStart);
    }

    /**
     * 获取是否第一次启动
     * @return  isLogin （true：是；false：否）
     * */
    public boolean getIsFirstStart() {
        return SharedPreferencesUtil.getInstance().getBoolean(Constants.IS_FIRST_START, true);
    }
    /********************** sharedPreferences end*******************/

}
