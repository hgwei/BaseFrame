package com.hgw.baseframe.core.http;

import com.hgw.baseframe.bean.BannerData;
import com.hgw.baseframe.bean.BaseResponse;
import com.hgw.baseframe.bean.LoginData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * 描述：
 *
 * @author hgw
 */

public interface GeeksApis {

    /**
     * 获取广告列表
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json") //这里的{id} 表示是一个变量
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username 账号
     * @param password 密码
     * @param repassword 确认密码
     * @return 注册数据
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

}
