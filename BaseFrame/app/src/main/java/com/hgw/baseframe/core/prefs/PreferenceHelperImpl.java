package com.hgw.baseframe.core.prefs;

import com.hgw.baseframe.app.Constants;

/**
 * 描述：SharedPreferences 实现
 * @author hgw
 */

public class PreferenceHelperImpl implements PreferenceHelper {
    private SharedPreferencesUtil mPreferences;

    public PreferenceHelperImpl(){
        //mPreferences=new SharedPreferencesUtil();
        mPreferences=SharedPreferencesUtil.getInstance();
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferences.saveString(Constants.ACCOUNT,account);
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT,"");
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferences.saveString(Constants.PASSWORD,password);
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferences.saveBoolean(Constants.LOGIN_STATUS,isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }
}
