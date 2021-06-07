## QQ登录使用说明

### 简介
封装一个`QQ登录`工具，会涉及到多个类，但用户使用时，只需用到 qq登录帮助类 --- `QQLoginHelper`,此类将`qq登录`功能封装，使开发者能简单快捷的集成`qq登录`功能

### 原理
`QQ登录`功能主要包括两个流程:`QQ授权`和`QQ用户信息获取`。`QQ授权`成功以后，会有`OpenId`及`token`的数据，然后通过这些数据去发起`QQ用户信息获取`流程，当以上两个流程均执行成功后，
则表示登录成功，并获取到用户登录后的基本信息。

### 使用说明
#### 一. 开发者账号及AppId
在集成`QQ登录`功能之前,你需要有一个 [QQ互联官网](https://connect.qq.com/index.html) 开发者账号，具体的申请，大家可以查看官网，此处不做赘述。  
在拥有了 [QQ互联官网](https://connect.qq.com/index.html) 开发者账号后，你要登录该官网，然后在官网首页的`应用管理`---`移动应用` 中创建新应用。
创建完毕后，提交审核，审核成功后，会有一个appId(此处假设appId=123456)

#### 二. 配置及初始化
在`Androidmanifast.xml`中添加QQ登录需要的`Activity`配置,具体如下：
```
<application
        //其他代码省略
        //...... >
          
        <!-- QQ互联第三方登录 -->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="tencentxxxxxx" />
            </intent-filter>
        </activity>

       //其他代码省略
        //......
    </application>
```
**这里需要注意的是，以上配置中`<data android:scheme="tencentxxxxxx" />`的`xxxxxx`为你申请的`AppId`,`scheme`属性写的时候是`tencent+AppId`,例如`appId=123456`,
则`<data android:scheme="tencent123456" />`，一定不要把`tencent`忘记写了.**  
在你自定义的`Application`中添加`QQ登录`功能所需初始化代码，具体如下：
```
/**
 * Title:自定义application
 * description:
 * autor:pei
 * created on 2020/1/9
 */
public class AppContext extends Application {

    private static AppContext instance;

    public static synchronized AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//=============若此初始化已添加则无需再添加============
//        //初始化SharePro
//        ShareConfig.getInstance().init(this)
//                //开启log调试,默认为关闭状态
//                .setDebug(true);
        //初始化登录设置
        QQLoginHelper.getInstance().setAppID("123456") //这里填入你的appId，这里假设是123456
                .setPackageName("com.test") //这里设置你的项目包名全称，这里假设是com.test
                .init();
    }
}
```
**这里需要注意的是`setAppID(String appId)`中填的是你在`QQ互联官网`上申请的你项目的`AppId`,然后`setPackageName(String packageName)`设置的是你项目包名全称。  
当然之前的关于`初始化SharePro`的代码也是要的,若开始引用此项目时已经写过本项目初始化相关代码，则此处可不再重复写**
#### 三. QQLoginHelper简介
`QQ登录`主要涉及到到类`QQLoginHelper`的使用，`QQLoginHelper`为单例类。下面对其几个重要方法做简单介绍。
##### 3.1 配置相关方法
```
setAppID(String appId)  //设置appId
setPackageName(String packageName) //设置包名全称
init() //初始化
```
以上三个方法，主要在全局自定义`Application`的`onCreate()`方法中调用，用于初始化`QQ登录相关配置`.在全局自定义`Application`的`onCreate()`调用如下：
```
        //初始化登录设置
        QQLoginHelper.getInstance().setAppID("123456") //这里填入你的appId，这里假设是123456
                .setPackageName("com.test") //这里设置你的项目包名全称，这里假设是com.test
                .init();
```
当然，在全局自定义`Application`的`onCreate()`方法中调用以上方法之前，你还要在全局自定义`Application`的`onCreate()`方法中初始化本库基本配置:
```
        //初始化SharePro
        ShareConfig.getInstance().init(this)
                //开启log调试,默认为关闭状态
                .setDebug(true);
```
否则,`QQLoginHelper`执行`init()`时会崩溃。  
##### 3.2 登录方法
```
    /**登录**/
    public void login(Context context, OnLoginListener listener)
```
以上为登录方法，在点击登录按钮时调用,`OnLoginListener`类中能返回登录各阶段状态。
##### 3.3 登录反馈方法
```
    /**在Activity的onActivityResult方法中调用**/
    public void onActivityResultData(int requestCode, int resultCode, Intent data) 
```
以上方法在涉及登录界面`Activity`的`onActivityResult`方法中调用，具体调用位置如下：
```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        QQLoginHelper.getInstance().onActivityResultData(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
```
**注意，以上方法是在`Activity`的`onActivityResult`方法中`super.onActivityResult(requestCode, resultCode, data)`之前调用，且此方法必须要调用。若不调用，qq在授权返回时，会无响应**
##### 3.4 退出登录方法
```
    /**退出登录**/
    public void loginOut(Context context)
```
#### 四.QQLoginHelper在Activity中登录使用示例
下面给出`QQLoginHelper`在`Activity`中使用示例:
```
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
```

