package com.sharelibrary.mob_sms_verify;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mob.MobSDK;
import com.sharelibrary.entity.SmsError;
import com.sharelibrary.entity.ZoneData;
import com.sharelibrary.util.ShareLog;
import com.sharelibrary.util.StringUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Title: 短信验证封装类
 * description:
 * autor:pei
 * created on 2021/6/6
 */
public class MobSMS {

    //sms监听
    private EventHandler mEventHandler;
    private SMSHandler mSMSHandler;

    //注册短信回调
    public void register(MobSMSListener listener) {
        mSMSHandler = new SMSHandler(MobSMS.this, listener);
        if (mEventHandler == null) {
            mEventHandler = new EventHandler() {
                @Override
                public void afterEvent(int event, int result, Object data) {
                    Message message = new Message();
                    if (result == SMSSDK.RESULT_COMPLETE) {//回调完成
                        message.what = SMSSDK.RESULT_COMPLETE;
                        if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                            message.arg1 = SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES;
                            message.obj = data;
                            mSMSHandler.sendMessage(message);
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            message.arg1 = SMSSDK.EVENT_GET_VERIFICATION_CODE;
                            mSMSHandler.sendMessage(message);
                        } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                            //获取语音验证码成功
                            message.arg1 = SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE;
                            mSMSHandler.sendMessage(message);
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            message.arg1 = SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE;
                            mSMSHandler.sendMessage(message);
                        }
                    } else {
                        ((Throwable) data).printStackTrace();

                        message.what = event;
                        message.obj = ((Throwable) data).getMessage();
                        mSMSHandler.sendMessage(message);
                    }

                }
            };
        }
        //注册短信监听
        SMSSDK.registerEventHandler(mEventHandler);
    }

    /***
     * 提交隐私协议
     *
     * 注:在点击同意用户协议时调用
     */
    public void submitPolicy(){
        //提交隐私协议
        MobSDK.submitPolicyGrantResult(true,null);
    }

    /**获取支持的国家代码列表**/
    public void getSupportedCountries(){
        SMSSDK.getSupportedCountries();
        ShareLog.i("=====获取国家代码======");
    }

    /***
     * 请求验证码
     *
     * @param country: 国家代码，如“86”
     * @param phone:手机号码，如“13800138000”
     */
    public void getVerifyCode(String country, String phone) {
        if (StringUtil.isNotEmpty(country) && StringUtil.isNotEmpty(phone)) {
            SMSSDK.getVerificationCode(country, phone);
        }
    }

    /***
     * 请求语音验证码
     *
     * @param country: 国家代码，如“86”
     * @param phone:手机号码，如“13800138000”
     */
    public void getVoiceVerifyCode(String country, String phone){
        if (StringUtil.isNotEmpty(country) && StringUtil.isNotEmpty(phone)) {
            SMSSDK.getVoiceVerifyCode(country,phone);
        }
    }


    /***
     * 提交验证码
     *
     * @param country: 国家代码，如“86”
     * @param phone: 手机号码，如“13800138000”
     * @param code: 验证码,4位或6位的字符串
     */
    public void submitVerifyCode(String country, String phone, String code) {
        if (StringUtil.isNotEmpty(country) && StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(code)) {
            SMSSDK.submitVerificationCode(country, phone, code);
        }
    }

    /**注销短信监听**/
    public void unRegister() {
        if (mEventHandler != null) {
            SMSSDK.unregisterEventHandler(mEventHandler);
            ShareLog.i("=======短信验证监听注销=======");
        }
        mEventHandler=null;
    }

    /**mob短信监听**/
    public interface MobSMSListener{
        //返回支持发送验证码的国家列表
        void getSupportCounties(List<ZoneData> list);

        //获取验证码成功
        void getVerifySuccess();

        //获取语音验证码成功
        void getVoiceVerifySuccess();

        //提交验证码成功
        void submitVerifySuccess();

        //发生错误
        void verifyFailed(String message);

    }

    private class SMSHandler<T> extends Handler {

        //弱引用(引用外部类)
        private WeakReference<T> weakReference;
        private MobSMSListener mobSMSListener;

        public SMSHandler(Object obj, MobSMSListener listener) {
            if(obj==null){
                throw new NullPointerException("=====传入obj不能为空=====");
            }
            //构造弱引用
            weakReference = new WeakReference<>((T) obj);
            this.mobSMSListener=listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //通过弱引用获取外部类.
            Object obj = weakReference.get();
            //进行非空再操作
            if (obj != null && (obj instanceof MobSMS) && mobSMSListener != null) {
                int what = msg.what;
                if (what == SMSSDK.RESULT_COMPLETE) {
                    switch (msg.arg1) {
                        case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES://返回支持发送验证码的国家列表
                            List<ZoneData>list=getZoneList(msg.obj);
                            mobSMSListener.getSupportCounties(list);
                            break;
                        case SMSSDK.EVENT_GET_VERIFICATION_CODE://获取验证码成功
                            mobSMSListener.getVerifySuccess();
                            break;
                        case SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE://获取语音验证码成功
                            mobSMSListener.getVoiceVerifySuccess();
                            break;
                        case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE://提交验证码成功
                            mobSMSListener.submitVerifySuccess();
                            break;
                        default:
                            break;
                    }
                } else {
                    //失败
                    SmsError smsError=getSmsError(msg.obj.toString());
                    mobSMSListener.verifyFailed(smsError.getDetail());
                }
            }
            //移除消息
            removeMessages(msg.what);
        }
    }

    private List<ZoneData> getZoneList(Object data){
        Gson gson=new Gson();
        String json= gson.toJson(data);
        List<ZoneData> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, ZoneData.class));
        }
        return list;
    }

    private SmsError getSmsError(String json){
        Gson gson=new Gson();
        SmsError smsError = gson.fromJson(json, SmsError.class);
        return smsError;
    }

}
