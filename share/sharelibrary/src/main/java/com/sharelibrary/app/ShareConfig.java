package com.sharelibrary.app;

import android.app.Application;

import com.sharelibrary.util.ShareLog;

/**
 * Title:SharePro初始化类
 * description:
 * autor:pei
 * created on 2019/12/17
 */
public class ShareConfig {

    private Application mApplication;

    private ShareConfig(){}

    private static class Holder {
        private static ShareConfig instance = new ShareConfig();
    }

    public static ShareConfig getInstance() {
        return Holder.instance;
    }

    /**初始化赋值(在项目的自定义Application中初始化)**/
    public ShareConfig init(Application application){
        this.mApplication=application;
        return ShareConfig.this;
    }

    /**
     * 是否打开Log打印
     * 打印,tag="share"
     *
     * @param print true:打开调试log,  false:关闭调试log
     * @return
     */
    public void setDebug(boolean print){
        //设置自定义网络打印开关
        ShareLog.setDebug(print);
    }

    /**获取项目上下文**/
    public Application getApplication() {
        if(mApplication==null){
            throw new NullPointerException("====SharePro需要初始化：ShareConfig.getInstance.init(Application application)===");
        }
        return mApplication;
    }

}
