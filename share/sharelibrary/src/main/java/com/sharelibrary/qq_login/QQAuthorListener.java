package com.sharelibrary.qq_login;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sharelibrary.app.ShareConfig;
import com.sharelibrary.entity.Author;
import com.sharelibrary.util.ShareLog;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Title: qq 登录授权监听
 *
 * description:
 * autor:pei
 * created on 2020/10/9
 */
public class QQAuthorListener implements IUiListener {

    private Tencent mTencent;
    private QQInfoListener mQQInfoListener;
    private QQLoginHelper.OnLoginListener mOnLoginListener;
    private Gson mGson;

    public QQAuthorListener(Tencent tencent, QQLoginHelper.OnLoginListener listener){
        this.mTencent=tencent;
        this.mOnLoginListener=listener;
        mGson=new Gson();
        mQQInfoListener=new QQInfoListener(mTencent,mOnLoginListener,mGson);
        //开始申请登录授权
        mOnLoginListener.loginStatus(QQStatus.START_LOGIN_APPLY,"开始申请登录授权",null);
    }

    @Override
    public void onComplete(Object obj) {
        ShareLog.i("========onComplete=======");
        if(mOnLoginListener!=null){
            if (obj != null) {
                String content=obj.toString();
                try {
                    Author author= mGson.fromJson(content, Author.class);
                    //每个qq的openid是唯一的
                    mTencent.setOpenId(author.getOpenid());
                    mTencent.setAccessToken(author.getAccess_token(), String.valueOf(author.getExpires_in()));
                    QQToken qqToken = mTencent.getQQToken();
                    ShareLog.i("========获取登录授权成功=======");
                    mOnLoginListener.loginStatus(QQStatus.LOGIN_APPLY_SUCCESS,"授权成功,正在获取登录信息...",null);
                    //获取用户信息
                    UserInfo info = new UserInfo(ShareConfig.getInstance().getApplication(), qqToken);
                    if (ready()) {
                        info.getUserInfo(mQQInfoListener);
                    }else{
                        ShareLog.i("========尚未登录或已登录过期=======");
                        mOnLoginListener.loginStatus(QQStatus.LOGIN_INVALID,"你尚未登录或已登录过期,请先登录",null);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ShareLog.e("========登录授权解析错误=======");
                    mOnLoginListener.loginStatus(QQStatus.LOGIN_APPLY_PARSE_FAILED,"登录授权解析错误",null);
                }
            } else {
                ShareLog.e("========登录授权解析数据为空=======");
                mOnLoginListener.loginStatus(QQStatus.LOGIN_APPLY_PARSE_FAILED_NULL,"登录授权解析数据失败",null);
            }
        }
    }

    @Override
    public void onError(UiError uiError) {
        ShareLog.i("========onError=======");
        //登录授权失败
        if(mOnLoginListener!=null) {
            if(uiError!=null){
                ShareLog.e("========登录授权失败: errorCode=" + uiError.errorCode + "   errorMessage=" + uiError.errorMessage );
                mOnLoginListener.loginStatus(QQStatus.LOGIN_APPLY_FAILED,"登录授权失败",uiError);
            }else{
                ShareLog.e("========登录授权失败: uiError=null =======");
                mOnLoginListener.loginStatus(QQStatus.LOGIN_APPLY_FAILED_NULL,"登录授权失败",null);
            }
        }
    }

    @Override
    public void onCancel() {
        ShareLog.e("========登录授权取消=======");
        if (mOnLoginListener != null) {
            mOnLoginListener.loginStatus(QQStatus.LOGIN_APPLY_CANCEL,"登录授权取消",null);
        }
    }

    private boolean ready() {
        if (mTencent == null) {
            throw new NullPointerException("=======mTencent为空============");
        }
        boolean ready = mTencent.isSessionValid()
                && mTencent.getQQToken().getOpenId() != null;
        if (!ready) {
            //你尚未登录或已登录过期，请先登录
        }
        return ready;
    }

}
