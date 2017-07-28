package com.hyd.paydayloan.network;

import com.hyd.paydayloan.base.MyObserver;
import com.hyd.paydayloan.bean.Token;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.http.Query;

/**
 * 用代理的方式拦截方法，做token刷新和重新请求数据
 * Created by lingxiaoming on 2017/7/26 0026.
 */

public class ProxyHandler implements InvocationHandler {
    private Object mProxyObject;
    private HttpBaseRequest mHttpBaseRequest;
    private boolean isNeedRefreshToken;//是否需要更新token

    public ProxyHandler(Object proxyObject, HttpBaseRequest httpBaseRequest) {
        this.mProxyObject = proxyObject;
        this.mHttpBaseRequest = httpBaseRequest;
    }


    @Override
    public Object invoke(Object o, final Method method, final Object[] args) throws Throwable {
        return Observable.just("").flatMap(new Function<Object, Observable<?>>() {
            @Override
            public Observable<?> apply(@NonNull Object o) throws Exception {
                try {
                    if (isNeedRefreshToken) {
                        updateMethodToken(method, args);
                    }
                    return (Observable<?>) method.invoke(mProxyObject, args);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
                return observable.flatMap(new Function<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof ApiException) {
                            ApiException apiException = (ApiException) throwable;
                            if (apiException.errorCode == 2001) {//TODO token过期
                                return refreshTokenWhenTokenInvalid();
                            }
                        }
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }

    private Throwable throwable;//请求token是否成功
    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ProxyHandler.class) {

            HttpRequest.getInstance().getToken().subscribe(new MyObserver<Token>() {
                @Override
                public void onNext(@NonNull Token token) {
                    super.onNext(token);
                    HttpRequest.getInstance().mToken = token.token;
                    isNeedRefreshToken = true;
                    throwable = null;
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    super.onError(e);
                    throwable = e;
                }
            });
            return Observable.just(throwable==null);
        }
    }

    private void updateMethodToken(Method method, Object[] args) {
        Annotation[][] annotationsArray = method.getParameterAnnotations();
        Annotation[] annotations;
        if (annotationsArray != null && annotationsArray.length > 0) {
            for (int i = 0; i < annotationsArray.length; i++) {
                annotations = annotationsArray[i];
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Query) {
                        if ("token".equals(((Query) annotation).value())) {
                            args[i] = HttpRequest.getInstance().mToken;
                        }
                    }
                }
            }
            isNeedRefreshToken = false;
        }
    }

}
