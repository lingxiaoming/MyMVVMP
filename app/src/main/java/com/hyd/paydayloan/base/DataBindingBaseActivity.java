package com.hyd.paydayloan.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.hyd.paydayloan.R;
import com.hyd.paydayloan.tools.ActivityManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基activity，用于处理页面统一事件，复杂页面请用MVPBaseActivity
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public abstract class DataBindingBaseActivity<B extends ViewDataBinding> extends Activity {
    public B mViewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);

        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().removeActivity(this);
    }
}
