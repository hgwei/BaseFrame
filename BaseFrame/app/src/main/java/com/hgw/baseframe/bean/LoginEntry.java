package com.hgw.baseframe.bean;

import com.hgw.baseframe.bean.login.LoginBean;
import java.io.Serializable;

/**
 * 描述：用户登录个人信息保存实体（单例模式）
 * @author hgw
 */
public class LoginEntry implements Serializable{
    private static LoginEntry loginEntry = null;
    private boolean isLogin = false; 	/*用户登录状态，默认为false*/
    private String token;       /*登录TOKEN*/

    private LoginBean.Data user; /*用户信息对象*/

    /**
     * 实现单例模式，获取LoginEntry类的入口方法
     */
    public static synchronized LoginEntry Instance() {
        if (loginEntry == null) {
            loginEntry = new LoginEntry();
        }
        return loginEntry;
    }

    /**
     * 注销
     */
    public void logout() {
        this.isLogin = false;
        this.token = null;
        this.user = null;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginBean.Data getUser() {
        return user;
    }

    public void setUser(LoginBean.Data user) {
        this.user = user;
    }
}
