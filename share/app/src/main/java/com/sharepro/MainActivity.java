package com.sharepro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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


            }
        });

        //退出登录
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void shortShow(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
