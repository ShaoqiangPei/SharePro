package com.sharelibrary.util;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

/**
 * Title: 倒计时帮助类
 *
 * description:
 * autor:pei
 * created on 2021/6/10
 */
public class CountDownTimerHelper extends CountDownTimer {

    private Button mBtn;
    private int mEnabledColor;//按钮可点击背景色
    private int mUnEnabledColor; //按钮不可点击背景色
    private OnFinishListener mOnFinishListener;

    /***
     * 初始化
     *
     * @param btn 需要设置倒计时的button
     * @param millisInFuture 倒计时总时间以，单位为毫秒(如要进行10秒倒计时，则为10000)
     * @param countDownInterval 时间间隔，单位毫秒
     */
    public CountDownTimerHelper(Button btn,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mBtn=btn;
    }

    /***
     * 设置按钮点击和非点击时背景色(可选方法)
     *
     * @param enabledColor: 可点击背景色,如: R.color.red 或 R.drawable.bg_identify_code_normal
     * @param unEnabledColor: 不可点击背景色,如: R.color.gray 或 R.drawable.bg_identify_code_press
     * @return
     */
    public CountDownTimerHelper setBtnColor(int enabledColor,int unEnabledColor){
        this.mEnabledColor=enabledColor;
        this.mUnEnabledColor=unEnabledColor;
        return this;
    }

    /**计时器结束监听**/
    public void setOnFinishListener(OnFinishListener listener){
        this.mOnFinishListener=listener;
    }

    /**开启倒计时**/
    public void startTimer() {
        mBtn.setEnabled(false);
        super.start();
    }

    /**关闭倒计时**/
    public void cancelTimer(){
        super.cancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(mUnEnabledColor!=0) {
            mBtn.setBackgroundResource(mUnEnabledColor);//设置按钮为灰色(不可点击)
        }
        mBtn.setText("重新发送("+millisUntilFinished/1000+"秒)");

        //设置按钮上的文字，获取截取设置为红色
        String text=mBtn.getText().toString();
        ForegroundColorSpan span=new ForegroundColorSpan(Color.RED);
        SpannableString spannableString=new SpannableString(text);
        int firstIndex=text.indexOf("(")+1;
        int lastIndex=text.indexOf("秒");
        spannableString.setSpan(span, firstIndex, lastIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mBtn.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mBtn.setText("重新获取验证码");
        mBtn.setEnabled(true);//重新获得点击
        if (mEnabledColor != 0) {
            mBtn.setBackgroundResource(mEnabledColor);  //还原背景色
        }
        if(mOnFinishListener!=null){
            mOnFinishListener.onFinish();
        }
    }

    public interface OnFinishListener{
        void onFinish();
    }

}
