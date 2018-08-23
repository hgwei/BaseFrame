package com.hgw.baseframe.core.prefs;

/**
 * 描述：SharedPreferences helper
 * @author hgw
 */
public class PreferenceHelper {
    /**账号*/
    public static final String ACCOUNT = "account";
    /**密码*/
    public static final String PASSWORD = "password";
    /**登录状态（true：已登录；false：未登录）*/
    public static final String LOGIN_STATUS = "login_status";
    /**是否第一次启动（true：是；false：否）*/
    public static final String IS_FIRST_START = "is_first_start";

    private static PreferenceHelper mPreferenceHelper=null;
    public static PreferenceHelper getInstance(){
        if (mPreferenceHelper == null) {
            mPreferenceHelper = new PreferenceHelper();
        }
        return mPreferenceHelper;
    }

    /**
     * 保存登录账号
     * @param account
     * */
    public void setLoginAccount(String account) {
        SharedPreferencesUtil.getInstance().saveString(ACCOUNT,account);
    }

    /**
     * 获取登录账号
     * @return LoginAccount
     * */
    public String getLoginAccount() {
        return SharedPreferencesUtil.getInstance().getString(ACCOUNT,"");
    }

    /**
     * 保存登录密码（需加密）
     * @param password
     * */
    public void setLoginPassword(String password) {
        SharedPreferencesUtil.getInstance().saveString(PASSWORD,password);
    }

    /**
     * 获取登录密码
     * @return LoginPassword
     * */
    public String getLoginPassword() {
        return SharedPreferencesUtil.getInstance().getString(PASSWORD, "");
    }

    /**
     * 保存登录状态
     * @param isLogin （true：已登录；false：未登录）
     * */
    public void setLoginStatus(boolean isLogin) {
        SharedPreferencesUtil.getInstance().saveBoolean(LOGIN_STATUS,isLogin);
    }

    /**
     * 获取登录状态
     * @return  isLogin （true：已登录；false：未登录）
     * */
    public boolean getLoginStatus() {
        return SharedPreferencesUtil.getInstance().getBoolean(LOGIN_STATUS, false);
    }

    /**
     * 保存是否第一次启动
     * @param IsFirstStart （true：是；false：否）
     * */
    public void setIsFirstStart(boolean IsFirstStart) {
        SharedPreferencesUtil.getInstance().saveBoolean(IS_FIRST_START,IsFirstStart);
    }

    /**
     * 获取是否第一次启动
     * @return  isLogin （true：是；false：否）
     * */
    public boolean getIsFirstStart() {
        return SharedPreferencesUtil.getInstance().getBoolean(IS_FIRST_START, true);
    }
}
