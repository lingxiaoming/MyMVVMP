package com.hyd.paydayloan.base;


import com.hyd.paydayloan.network.ApiException;
import com.hyd.paydayloan.tools.LogUtils;
import com.hyd.paydayloan.tools.ToastManager;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import java.io.IOException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * rxjava的回调封装下，返回结果做统一处理
 * Created by lingxiaoming on 2017/7/25 0025.
 */

public abstract class MyObserver<T> implements Observer<T> {
    private static final String TAG = "MyObserver";


    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        LogUtils.e(TAG, "onError: "+e.getMessage());
        String errorMessage = e.getMessage();
        if(e instanceof ApiException){

        }else if(e instanceof HttpException){
            errorMessage = "服务器内部错误";
        }else if(e instanceof IOException){
            errorMessage = "请检查您的网络";
        }

        ToastManager.show(errorMessage);

    }
}
