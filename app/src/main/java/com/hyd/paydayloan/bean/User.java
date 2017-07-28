package com.hyd.paydayloan.bean;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.hyd.paydayloan.BR;


/**
 * 用户登录后信息
 * Created by lingxiaoming on 2017/7/21 0021.
 */

public class User extends BaseObservable{
    public String name;

    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR._all);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR._all);
    }
}
