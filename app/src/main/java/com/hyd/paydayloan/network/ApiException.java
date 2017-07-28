package com.hyd.paydayloan.network;


/**
 * 接口返回的数据有问题
 * Created by lingxiaoming on 2017/7/25 0025.
 */

public class ApiException extends RuntimeException{
    public int errorCode;

    public ApiException( int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }




}
