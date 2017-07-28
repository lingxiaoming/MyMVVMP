package com.hyd.paydayloan.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

/**
 * MVP的Activity，注册和销毁view在这里，简单页面请继承用BaseActivity
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public abstract class MVPDataBindingBaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingBaseActivity<B> {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
