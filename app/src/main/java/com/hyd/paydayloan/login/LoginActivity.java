package com.hyd.paydayloan.login;

import android.os.Bundle;
import android.view.View;

import com.hyd.paydayloan.R;
import com.hyd.paydayloan.base.MVPDataBindingBaseActivity;
import com.hyd.paydayloan.bean.User;
import com.hyd.paydayloan.databinding.ActivityLoginBinding;
import com.hyd.paydayloan.tools.LogUtils;


/**
 * 登录页面
 */
public class LoginActivity extends MVPDataBindingBaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View, View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = new User("18667125335", "123456");//创建对象，赋初始值
        mViewBinding.setUser(user);

        mViewBinding.btnLogin.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void onClick(View view) {
        user.setName("张三");

        mPresenter.login(mViewBinding.etTelephone.getText().toString(), mViewBinding.etPassword.getText().toString());
    }

    @Override
    public void loginSuccess(User user) {
        this.user = user;
        LogUtils.d(TAG, "loginSuccess:" + user.getName());
    }

    @Override
    public void loginFailed(String msg) {
        LogUtils.d(TAG, "loginFailed:" + msg);
        user.setName(msg);
    }
}

