package com.hyd.paydayloan;

import android.app.Application;

/**
 * application
 * Created by lingxiaoming on 2017/7/25 0025.
 */

public class PaydayLoanApplication extends Application {
    private static PaydayLoanApplication application;

    public static synchronized PaydayLoanApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
