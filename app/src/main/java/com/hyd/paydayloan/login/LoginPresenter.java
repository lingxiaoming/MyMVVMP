package com.hyd.paydayloan.login;


import com.hyd.paydayloan.base.MyObserver;
import com.hyd.paydayloan.bean.User;
import com.hyd.paydayloan.network.HttpRequest;
import com.hyd.paydayloan.tools.LogUtils;
import io.reactivex.annotations.NonNull;

/**
 * 登录presenter
 * Created by lingxiaoming on 2017/7/21 0021.
 */
public class LoginPresenter extends LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";


    public void login(String username, String password){

        HttpRequest.getInstance().login(username, password).subscribe(new MyObserver<User>() {

            @Override
            public void onNext(@NonNull User user) {
                super.onNext(user);
                LogUtils.e(TAG, "onNext:"+user.getName()+" - "+user.getPassword());
                if(getView() != null) getView().loginSuccess(user);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                super.onError(e);
                LogUtils.e(TAG, "onError:"+e.getMessage());
                if(getView() != null) getView().loginFailed(e.getMessage());
            }
        });

    }

}
