package com.sharepro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.sharelibrary.entity.LoginInfo;
import com.sharelibrary.qq_login.QQLoginHelper;
import com.sharelibrary.qq_login.QQStatus;
import com.sharelibrary.util.LoginUIHelper;
import com.sharelibrary.util.ShareLog;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private Button mBtnLogin;
    private Button mBtnLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShareLog.i("======我是测试=======");

        mTv = findViewById(R.id.tv);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLoginOut = findViewById(R.id.btn_login_out);

        //登录
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                login(mTv,mBtnLogin);
            }
        });

        //退出登录
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出
                QQLoginHelper.getInstance().loginOut(MainActivity.this);
                ShareLog.i("=======退出登录了=======");
            }
        });
    }

    /**登录**/
    private void login(TextView textView,Button btnLogin){
        //登录
        QQLoginHelper.getInstance().login(MainActivity.this, new QQLoginHelper.OnLoginListener() {
            @Override
            public void loginStatus(int code, String message, Object obj) {

                switch (code) {
                    case QQStatus.START_LOGIN_APPLY://正在申请登录授权
                        LoginUIHelper.setText(textView,"正在申请登录授权,请稍后...");
                        //禁止按钮点击
                        LoginUIHelper.setBtnEnabled(btnLogin,false);
                        break;
                    case QQStatus.LOGIN_APPLY_CANCEL://登录授权取消
                        LoginUIHelper.setText(textView,"登录授权取消...");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_APPLY_FAILED_NULL://登录授权失败
                    case QQStatus.LOGIN_APPLY_FAILED://登录授权失败
                        LoginUIHelper.setText(textView,"登录授权失败");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_APPLY_PARSE_FAILED_NULL://登录授权数据解析失败
                    case QQStatus.LOGIN_APPLY_PARSE_FAILED://登录授权数据解析失败
                        LoginUIHelper.setText(textView,"登录授权数据解析失败");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_APPLY_SUCCESS://登录授权成功,正在获取登录信息
                        LoginUIHelper.setText(textView,"登录授权成功,正在获取登录信息...");
                        //按钮仍然禁止点击,此处不做处理
                        break;
                    case QQStatus.LOGIN_INVALID://未登录或登录过期
                        LoginUIHelper.setText(textView,"尚未登录或已登录过期,请重新登录");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_INFO_CANCEL://拉取用户登录信息已被取消
                        LoginUIHelper.setText(textView,"拉取用户登录信息被取消");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_INFO_FAILED_NULL://拉取用户登录信息失败
                    case QQStatus.LOGIN_INFO_FAILED://拉取用户登录信息失败
                        LoginUIHelper.setText(textView,"获取用户登录信息失败");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_INFO_PARSE_FAILED_NULL://拉取用户登录信息解析失败
                    case QQStatus.LOGIN_INFO_PARSE_FAILED://拉取用户登录信息解析失败
                        LoginUIHelper.setText(textView,"获取用户登录信息解析失败");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    case QQStatus.LOGIN_INFO_SUCCESS://获取登录信息成功(登录成功)
                        LoginUIHelper.setText(textView,"获取登录信息成功");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);

                        //打印用户信息
                        LoginInfo loginInfo = (LoginInfo) obj;
                        ShareLog.i("======loginInfo====" + loginInfo.toString());

//                        //显示打印信息
//                        StringBuffer buffer=new StringBuffer();
//                        buffer.append("获取登录信息成功\n");
//                        buffer.append("昵称:"+loginInfo.getNickname()+"\n");
//                        buffer.append("性别:"+loginInfo.getGender()+"\n");
//                        buffer.append("性别类型:"+loginInfo.getGender_type()+"\n");
//                        buffer.append("省:"+loginInfo.getProvince()+"\n");
//                        buffer.append("市:"+loginInfo.getCity()+"\n");
//                        buffer.append("年:"+loginInfo.getYear()+"\n");
//                        buffer.append("等级:"+loginInfo.getLevel());
//                        LoginUIHelper.setText(textView,buffer.toString());

                        break;
                    case QQStatus.LOGIN_OUT_SUCCESS://退出登录成功
                        LoginUIHelper.setText(textView,"退出登录成功");
                        //按钮恢复点击
                        LoginUIHelper.setBtnEnabled(btnLogin,true);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        QQLoginHelper.getInstance().onActivityResultData(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
