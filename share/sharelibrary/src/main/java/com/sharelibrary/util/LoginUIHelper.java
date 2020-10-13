package com.sharelibrary.util;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Title:
 * description:
 * autor:pei
 * created on 2020/10/12
 */
public class LoginUIHelper {

    /***
     * 设置控件科点击性
     * @param view
     * @param enabled  true：可点击   false: 不可点击
     */
    public static void setBtnEnabled(View view,boolean enabled){
        if(view!=null){
            view.setEnabled(enabled);
        }
    }

    /**给TextView设置要显示的内容**/
    public static void setText(TextView textView,String message){
        if(textView!=null&&StringUtil.isNotEmpty(message)){
            textView.setText(message);
        }

    }
}
