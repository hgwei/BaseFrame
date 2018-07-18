package com.hgw.baseframe.core.prefs;

/**
 * 描述：SharedPreferences helper
 * @author hgw
 */
public interface PreferenceHelper {

    /**
     * 保存账号
     * @param account Account
     */
    void setLoginAccount(String account);

    /**
     * 取出账号
     * @return account
     */
    String getLoginAccount();

    /**
     * 保存密码
     * @param password Password
     */
    void setLoginPassword(String password);

    /**
     * 取出密码
     * @return password
     */
    String getLoginPassword();

    /**
     * 保存登录状态
     * @param isLogin IsLogin
     */
    void setLoginStatus(boolean isLogin);

    /**
     * 取出登录状态
     * @return login status
     */
    boolean getLoginStatus();
}
