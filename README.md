## SharePro
a library for login or share

[![](https://jitpack.io/v/ShaoqiangPei/SharePro.svg)](https://jitpack.io/#ShaoqiangPei/SharePro)
### 简介
本项目用于提供 第三方登录，分享工具。目前只具备 QQ登录功能

#### 一.依赖
在你的`project`对应的`build.gradle`中添加：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在 `app_module`对应的`build.gradle`中添加引用(以版本`0.0.1`为例)：
```
  android {

     //此项配置也要添加
      compileOptions {
          sourceCompatibility JavaVersion.VERSION_1_8
          targetCompatibility JavaVersion.VERSION_1_8
      }
  }

	dependencies {
	        implementation 'com.github.ShaoqiangPei:SharePro:0.0.1'
	}
```
#### 二.初始化
在你的项目中自定义一个`Application`,然后在你的自定义`Application`的`onCreate()`中进行初始化,类似如下：
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

        //初始化SharePro
        ShareConfig.getInstance().init(this)
                 //开启log调试,默认为关闭状态
                .setDebug(true);
    }
}
```
在你项目的`mainfast.xml`中声明自己的`application`，类似如下：
```
 <application
        android:name=".AppContext"//声明自己的Application
	//以下省略
        //......
        >
    //此处省略
    //......

  </application>
```
#### 三.使用说明
[QQ登录](https://github.com/ShaoqiangPei/SharePro/blob/main/read/QQ%E7%99%BB%E5%BD%95%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md) ————  qq第三方登录功能 




