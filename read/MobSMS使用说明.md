## MobSMS使用说明

### 简介
`MobSMS`是集成 [**mob**](https://www.mob.com/)的`SMSSDK`模块，然后封装成的短信验证工具类。你可以利用此类迅速实现`短信验证`功能。

### 使用说明
#### 一. 创建应用
在 [**mob**](https://www.mob.com/) 上创建应用，获取`App key`和`App secret`，具体创建可参考  
[Android短信验证再研究](https://www.jianshu.com/p/0524f82bf269)  
目前一般用户的短信使用标准是 单个app`一万条/月`免费短信，若用户量比较大，则需付费使用。
#### 二. 依赖
在你的 `project`对应的`builg.gradle`中添加以下依赖：
```
buildscript {
    repositories {
        // 配置Mob Maven库
        maven {
           url "https://mvn.mob.com/android"
        }
    }
    dependencies {
        classpath "com.mob.sdk:MobSDK:2018.0319.1724"
    }
}
```
在`app_module`对应的`builg.gradle`中添加以下依赖：
```
// 添加插件
apply plugin: 'com.mob.sdk'

// 在MobSDK的扩展中注册SMSSDK的相关信息
MobSDK {
    appKey "替换为mob官方申请的appkey"
    appSecret "替换为mob官方申请的appkey对应的appSecret"
    spEdition "fp" // 设定MobSDK为隐私协议适配版本
    SMSSDK {}
}
```
为了遵守`app`使用规范，你需要在你项目的`《隐私协议》`中追加以下信息：
`我们使用了第三方（上海游昆信息技术有限公司，以下称“MobTech”）MobTech SMSSDK服务为您提供短信验证功能。
为了顺利实现该功能，您需要授权MobTechSDK提供对应的服务;在您授权后，MobTech将收集您相关的个人信息。
关于MobTech所收集的信息种类、用途、个人信息保护的规则及退出机制等，
详见MobTech官网（[www.mob.com](http://www.mob.com/?fileGuid=rp3OVEB0p9TGaYAm)）上的
隐私政策 （[www.mob.com/about/policy](http://www.mob.com/about/policy?fileGuid=rp3OVEB0p9TGaYAm)）条款。
`
然后在点击`同意`《隐私政策》或《用户协议》的时候，调用以下方法：
```
 //提交隐私协议(mobSMS为MobSMS对象)
 mobSMS.submitPolicy();
```
#### 三.MobSMS方法简介
封装类`MobSMS`中的一些主要方法：
```
    //注册短信回调
    public void register(MobSMSListener listener)

    /***
     * 提交隐私协议
     *
     * 注:在点击同意用户协议时调用
     */
    public void submitPolicy()

    /**获取支持的国家代码列表**/
    public void getSupportedCountries()

    /***
     * 请求验证码
     *
     * @param country: 国家代码，如“86”
     * @param phone:手机号码，如“13800138000”
     */
    public void getVerifyCode(String country, String phone)

    /***
     * 请求语音验证码
     *
     * @param country: 国家代码，如“86”
     * @param phone:手机号码，如“13800138000”
     */
    public void getVoiceVerifyCode(String country, String phone)

    /***
     * 提交验证码
     *
     * @param country: 国家代码，如“86”
     * @param phone: 手机号码，如“13800138000”
     * @param code: 验证码,4位或6位的字符串
     */
    public void submitVerifyCode(String country, String phone, String code) 

   /**注销短信监听**/
    public void unRegister() 
```
#### 四.MobSMS在Activity中使用
下面贴出`MobSMS`在`Activity`中使用代码:
```
public class TempActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvTest;
    private Button mBtnTest;
    private Button mBtnTest1;
    private Button mBtnTest2;
    private Button mBtnTest3;
    private Button mBtnTest4;

    private MobSMS mobSMS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        //初始化控件
        initView();
        //初始化数据
        initData();
        //控件监听
        setListener();
    }

    /**
     * 初始化控件
     **/
    private void initView() {
        mTvTest = findViewById(R.id.mTvTest);
        mBtnTest = findViewById(R.id.mBtnTest);
        mBtnTest1 = findViewById(R.id.mBtnTest1);
        mBtnTest2 = findViewById(R.id.mBtnTest2);
        mBtnTest3 = findViewById(R.id.mBtnTest3);
        mBtnTest4 = findViewById(R.id.mBtnTest4);
    }

    private void initData() {
        mTvTest.setText("我是测试啊");

        mobSMS=new MobSMS();
    }

    /**
     * 控件监听
     **/
    private void setListener() {
        //点击事件
        mBtnTest.setOnClickListener(this);
        mBtnTest1.setOnClickListener(this);
        mBtnTest2.setOnClickListener(this);
        mBtnTest3.setOnClickListener(this);
        mBtnTest4.setOnClickListener(this);

        //注册
        mobSMS.register(new MobSMS.MobSMSListener() {

            @Override
            public void getSupportCounties(List<ZoneData> list) {
                LogUtil.i("=====获取国家代码列表成功=====");
                for(ZoneData bean:list){
                    LogUtil.i("======code="+bean.getZone()+"  rule="+bean.getRule());
                }
            }

            @Override
            public void getVerifySuccess() {
                LogUtil.i("=====获取验证短信成功=====");
                ToastUtil.shortShow("获取验证短信成功");
            }

            @Override
            public void getVoiceVerifySuccess() {
                LogUtil.i("=====获取语音验证短信成功=====");
                ToastUtil.shortShow("获取语音验证短信成功");
            }

            @Override
            public void submitVerifySuccess() {
                LogUtil.i("=====提交验证码成功=====");
                ToastUtil.shortShow("提交验证码成功");
            }

            @Override
            public void verifyFailed(String message) {
                LogUtil.i("=====验证短信相关功能失败: message="+message);
                ToastUtil.shortShow(message);
            }
        });

    }

    @Override
    public void onClick(View v) {
         switch (v.getId()) {
             case R.id.mBtnTest:
                 LogUtil.i("======隐私协议======");

                 //提交隐私协议
                 mobSMS.submitPolicy();
                 LogUtil.i("========同意隐私协议=========");

                 break;
             case R.id.mBtnTest1:
                 LogUtil.i("======请求验证码======");

                 // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                 mobSMS.getVerifyCode("86", "1895432113");
                 break;
             case R.id.mBtnTest2:
                 LogUtil.i("======请求语音验证码======");
                 mobSMS.getVoiceVerifyCode("86", "1895432113");
                 break;
             case R.id.mBtnTest3:
                 LogUtil.i("======获取国家代码======");
                 mobSMS.getSupportedCountries();
                 break;
             case R.id.mBtnTest4:
                 LogUtil.i("======提交验证码======");
                 mobSMS.submitVerifyCode("86", "1895432113","1357");
                 break;
             default:
                 break;
         }
    }

    private void test(){


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //注销短信监听
        if(mobSMS!=null){
            mobSMS.unRegister();
        }
    }
}
```

