package com.sharelibrary.qq_login;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sharelibrary.entity.LoginInfo;
import com.sharelibrary.util.ShareLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Title: qq授权成功后，拉取用户信息的监听
 * description:
 * autor:pei
 * created on 2020/10/9
 */
public class QQInfoListener implements IUiListener {

    private Tencent mTencent;
    private QQLoginHelper.OnLoginListener mOnLoginListener;
    private Gson mGson;

    public QQInfoListener(Tencent tencent,QQLoginHelper.OnLoginListener listener,Gson gson){
        this.mTencent=tencent;
        this.mOnLoginListener=listener;
        this.mGson=gson;
    }

    @Override
    public void onComplete(Object obj) {
        if(mOnLoginListener!=null){
            if (obj != null) {
                String userString=obj.toString();
                try {
                    ShareLog.i("======userString======"+userString);
                    LoginInfo loginInfo=mGson.fromJson(userString,LoginInfo.class);
                    ShareLog.i("======loginInfo======"+loginInfo);
                    loginInfo.setOpenId(mTencent.getOpenId());
                    ShareLog.i("========拉取登录信息成功=======");
                    mOnLoginListener.loginStatus(QQStatus.LOGIN_INFO_SUCCESS,"获取登录信息成功",loginInfo);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ShareLog.e("========拉取登录信息解析错误=======");
                    mOnLoginListener.loginStatus(QQStatus.LOGIN_INFO_PARSE_FAILED,"登录信息解析错误",obj);
                }
            } else {
                ShareLog.e("========拉取登录信息解析数据为空=======");
                mOnLoginListener.loginStatus(QQStatus.LOGIN_INFO_PARSE_FAILED_NULL,"登录信息解析失败",null);
            }
        }
    }

    @Override
    public void onError(UiError uiError) {
        //拉取用户登录信息失败
        if(mOnLoginListener!=null) {
            if(uiError!=null){
                ShareLog.e("拉取用户登录信息失败: errorCode=" + uiError.errorCode + "   errorMessage=" + uiError.errorMessage);
                mOnLoginListener.loginStatus(QQStatus.LOGIN_INFO_FAILED,"获取用户登录信息失败",uiError);
            }else{
                ShareLog.e("拉取用户登录信息失败: uiError=null");
                mOnLoginListener.loginStatus(QQStatus.LOGIN_INFO_FAILED_NULL,"获取用户登录信息失败",null);
            }
        }
    }

    @Override
    public void onCancel() {
        ShareLog.e("========拉取用户登录信息已被取消=======");
        if (mOnLoginListener != null) {
            mOnLoginListener.loginStatus(QQStatus.LOGIN_INFO_CANCEL,"拉取用户登录信息已被取消",null);
        }
    }
}
