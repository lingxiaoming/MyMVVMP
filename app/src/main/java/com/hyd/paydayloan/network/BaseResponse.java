package com.hyd.paydayloan.network;

/**
 * 返回数据基类
 * Created by lingxiaoming on 2017/7/25 0025.
 */

public class BaseResponse<T> {
    public int rtn_code;
    public String rtn_msg;
    public T data;


    public boolean isSuccess(){
        return rtn_code == 1;
    }
}
