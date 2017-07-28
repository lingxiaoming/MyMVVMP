package com.hyd.paydayloan.network;

import com.hyd.paydayloan.bean.Token;
import com.hyd.paydayloan.bean.User;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求接口定义
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public interface HttpInterface {

    @FormUrlEncoded
    @POST(RequestConstants.LOGIN)
    Observable<User> login(@FieldMap HashMap<String,String> para);

    @FormUrlEncoded
    @POST(RequestConstants.GETTOKEN)
    Observable<Token> getToken(@FieldMap HashMap<String,String> para);
}
