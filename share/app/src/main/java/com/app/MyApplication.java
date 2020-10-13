package com.app;

import android.app.Application;
import com.sharelibrary.app.ShareConfig;
import com.sharelibrary.qq_login.QQLoginHelper;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/10/9
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //设置application
        ShareConfig.getInstance().init(this)
                .setDebug(true);
        //初始化登录设置
        QQLoginHelper.getInstance().setAppID("101905675")
                .setPackageName("com.testdemo")
                .init();
    }

}
