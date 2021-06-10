## CountDownTimerHelper使用说明

### 简介
`CountDownTimerHelper`是一个用于倒计时的工具类，通常用在短信验证倒计时场景。

### 使用说明
```
/**初始化
*
* context 上下文
* button  需要设置倒计时的button
* millisInFuture 倒计时总时间以，单位为毫秒(如要进行10秒倒计时，则为10000)
* countDownInterval 时间间隔，单位毫秒
**/
 mCountDownTimerHelper=new CountDownTimerHelper(context,button,millisInFuture,countDownInterval);

/***
 * 设置按钮点击和非点击时背景色(可选方法)
 *
 * @param enabledColor: 可点击背景色,如: R.color.red 或 R.drawable.bg_identify_code_normal
 * @param unEnabledColor: 不可点击背景色,如: R.color.gray 或 R.drawable.bg_identify_code_press
 * @return
 */
public CountDownTimerHelper setBtnColor(int enabledColor,int unEnabledColor)

//开始倒计时
startTimer();

//取消倒计时,一般在activity的onDestroy()方法中调用
cancelTimer()
```
