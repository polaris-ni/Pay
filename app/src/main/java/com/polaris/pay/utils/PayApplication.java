package com.polaris.pay.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;


/**
 * @Date 2021/2/24 21:39
 * @Author toPolaris
 * @Description 提供全局context获取，并对记录详情页面需要的数组进行初始化
 */
public class PayApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
