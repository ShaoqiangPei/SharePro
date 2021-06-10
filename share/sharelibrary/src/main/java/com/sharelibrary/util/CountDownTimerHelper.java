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
    private Context mContext;

    public CountDownTimerHelper(Context context,Button btn,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mContext=context;
        this.mBtn=btn;
    }

    /***
     * 设置按钮点击和非点击时背景色
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
        mBtn.setText(millisUntilFinished/1000+"秒后可重新发送");

        //设置按钮上的文字，获取截取设置为红色
        ForegroundColorSpan span=new ForegroundColorSpan(Color.RED);
        SpannableString spannableString=new SpannableString(mBtn.getText().toString());
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mBtn.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mBtn.setText("重新获取验证码");
        mBtn.setEnabled(true);//重新获得点击
        if (mEnabledColor != 0) {
            mBtn.setBackgroundResource(mEnabledColor);  //还原背景色
        }
    }


}
