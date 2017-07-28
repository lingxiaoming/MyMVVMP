package com.hyd.paydayloan.network;

import com.hyd.paydayloan.bean.Token;
import com.hyd.paydayloan.bean.User;
import java.util.HashMap;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public class HttpRequest extends HttpBaseRequest{

    protected static HttpRequest mHttpRequest;
    public String mToken;//公用token

    public static HttpRequest getInstance() {
        if(mHttpRequest == null){
            mHttpRequest = new HttpRequest();
        }
        return mHttpRequest;
    }

    private HttpRequest(){
        super();
    }




    public Observable<User> login(String username, String password){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(RequestConstants.USERNAME, username);
        hashMap.put(RequestConstants.PASSWORD, password);

        return getProxy(HttpInterface.class).login(hashMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Token> getToken(){
        HashMap<String, String> hashMap = new HashMap<>();
        return getProxy(HttpInterface.class).getToken(hashMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
