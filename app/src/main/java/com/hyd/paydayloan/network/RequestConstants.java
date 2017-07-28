package com.hyd.paydayloan.network;

/**
 * 请求方法名，参数名
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public class RequestConstants {
    /**
     * 服务器地址
     */
    private static final String MAIN_URL = "https://m.huayingbaolicai.com";//正式服务器地址
    private static final String MAIN_URL2 = "http://test.huayingbaolicai.com:8002";//测试服务器地址
    public static final String BASE_URL = MAIN_URL2 + "/api/";




    /**
     * 接口名称
     */
    public static final String LOGIN = "login/checkPasswd";
    public static final String GETTOKEN = "token/getToken";







    /**
     * 接口参数
     */
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
}
