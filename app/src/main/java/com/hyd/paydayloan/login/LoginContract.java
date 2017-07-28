package com.hyd.paydayloan.login;

import com.hyd.paydayloan.base.BasePresenter;
import com.hyd.paydayloan.base.BaseView;
import com.hyd.paydayloan.bean.User;

/**
 * 登录p,v集合
 * Created by lingxiaoming on 2017/7/25 0025.
 */

public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess(User user);
        void loginFailed(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String name, String pass);
    }
}
