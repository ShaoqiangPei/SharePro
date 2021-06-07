## EmailSender使用说明

### 简介
`EmailSender`是一个用于实现自动发邮件的工具类，我们可以使用它来快速实现邮箱验证的功能。  

### 使用说明
#### 一. 邮件发送原理
在`Android`中的邮件发送原理是：根据发件箱的类型来确定邮箱服务器的基本参数设置，然后登录发件箱，获取发件箱`授权码`，
然后设置发件箱`地址`，`昵称`，`标题`，`内容`，`附件`等信息，接着设置`收件箱地址`(为数组对象,意味着可以实现群发)，最后执行发邮件逻辑。
#### 二. 邮箱基本知识
##### 2.1 邮件协议
收发邮件也是一种通讯模式，它拥有自己的邮件协议，常见的协议类型有：`POP3`，`SMTP`，和`IMAP`。
- **POP3:**  `POP3`是`Post Office Protocol 3`的简称，即邮局协议的第`3`个版本,它规定怎样将个人计算机连接到`Internet`的邮件服务器和下载电子邮件的电子协议。它是因特网电子邮件的第一个离线协议标准,`POP3`允许用户从服务器上把邮件存储到本地主机（即自己的计算机）上,同时删除保存在邮件服务器上的邮件，而`POP3`服务器则是遵循`POP3协议`的接收邮件服务器，用来接收电子邮件的。
- **SMTP:**  `SMTP` 的全称是`“Simple Mail Transfer Protocol”`，即简单邮件传输协议。它是一组用于从源地址到目的地址传输邮件的规范，通过它来控制邮件的中转方式。`SMTP` 协议属于 `TCP/IP` 协议簇，它帮助每台计算机在发送或中转信件时找到下一个目的地。`SMTP` 服务器就是遵循 `SMTP` 协议的发送邮件服务器。`SMTP` 认证，简单地说就是要求必须在提供了账户名和密码之后才可以登录 `SMTP` 服务器，这就使得那些垃圾邮件的散播者无可乘之机。增加 `SMTP` 认证的目的是为了使用户避免受到垃圾邮件的侵扰。  
- **IMAP:**  `IMAP`全称是`Internet Mail Access Protocol`，即交互式邮件存取协议，它是跟`POP3`类似邮件访问标准协议之一。不同的是，开启了`IMAP`后，您在电子邮件客户端收取的邮件仍然保留 在服务器上，同时在客户端上的操作都会反馈到服务器上，如：删除邮件，标记已读等，服务器上的邮件也会做相应的动作。所以无论从浏览器登录邮箱或者客户端 软件登录邮箱，看到的邮件以及状态都是一致的。
##### 2.2 邮箱类型
常见的邮箱类型有: `qq`邮箱,`163`邮箱,`126`邮箱,`sohu`邮箱,`sina`邮箱等。
##### 2.3 邮箱服务器
根据不同的邮箱类型及协议，服务器的地址及端口有所不同。下面列出一些常见的邮箱服务器信息
**126邮箱相关服务器服务器信息**
| 服务器名称 | 服务器地址 | SSL协议端口号 | 非SSL协议端口号 |
| --- | --- | --- | --- |
| IMAP | imap.126.com | 993 | 143 |
| SMTP | smtp.126.com | 465/994 | 25 |
| POP3 | pop.126.com | 995 | 110 |
**163邮箱相关服务器服务器信息**
| 服务器名称 | 服务器地址 | SSL协议端口号 | 非SSL协议端口号 |
| --- | --- | --- | --- |
| IMAP | imap.163.com | 993 | 143 |
| SMTP | smtp.163.com | 465/994 | 25 |
| POP3 | pop.163.com | 995 | 110 |  
**qq邮箱相关服务器服务器信息**
| 服务器名称 | 服务器地址 | 端口号 |
| --- | --- | --- |
| SMTP | smtp.qq.com | 465/587 |
| POP3 | pop.qq.com | 995 |
由于不同邮箱及协议的不同，服务器地址和端口会发生很大变化，这里不一 一列举，大家自行百度即可。
如我要搜 sina 邮箱的 smtp协议 服务器地址和端口，你可以直接百度`sina smtp地址`即可，一般相关信息都会给出服务器地址和端口。
#### 三. 邮箱授权码的获取
实现邮件发送功能,需要使用到发件人的`邮箱地址`和`邮箱授权码`，那么如何才能开启并获取发件人的`邮箱授权码`呢？下面以我的`163`邮箱为例。
`登录163邮箱` ----> `设置` ----> `POP3/SMTP/IMAP` ，然后回出现如下界面：
![image.png](https://upload-images.jianshu.io/upload_images/6127340-b8869461ec527f13.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这里只要选择一个`SMTP` 服务即可，我选择开启的是`POP3/SMTP服务`,然后开启以后，界面会弹出二维码，提示你扫码发送短信验证，短信验证ok后，
界面会出现一个`16位长度字符串`,这就是你当前邮箱的`授权码`了，注意保护好此授权码哦，不要泄露。其他邮箱的`授权码`获取方式与此类似，
这里就不赘述了。然后将此授权码运用到你代码中去。
#### 四. 邮件发送封装类EmailSender的使用简介
经过以上准备，接下来就可以愉快的编码了。这里我将`邮件发送`功能封装到了一个工具类`EmailSender`中，接下来简单介绍下`EmailSender`的使用。
`EmailSender`默认采用`163邮箱, SMTP协议`服务器发送短信功能。若你的发件箱也为`163邮箱`,那么你可以向下面这样进行发邮件：
```
        EmailSender.getInstance()
                .setFrom("xxxxx@163.com") //设置发件人邮箱地址
                .setFromNickName("奔跑的佩恩") //设置发件人昵称,不设置时默认昵称为邮箱地址名称
                .setAuthCode("16位邮箱授权码") //设置发件人邮箱授权码
                .setTitle("测试邮件") //设置发送邮件的标题
                .setContent("别打我，我是测试邮件") //设置发送邮件的内容
                .setFilePath(null) //添加附件,设置null时表示不发送附件
                .setTo(new String[]{"xxxxx@xxx.com"}) //设置收件邮箱地址数组
                //发送邮件,传null表示不监听发送邮件的结果
                .sendEmail(new EmailSender.OnSendEmailListener() {
                    @Override
                    public void success() {
                        LogUtil.i("====邮件发送成功======");
                        ToastUtil.shortShow("邮件发送成功");
                    }

                    @Override
                    public void failed(String message) {
                        LogUtil.i("====邮件发送失败=====message="+message);
                        ToastUtil.shortShow("邮件发送失败:"+message);
                    }
                });
```
因为邮箱服务器的设置包含了一系列`key`值,其他发送邮箱我为测试过，不知道如果换了邮箱类型后，服务器属性设置的`key`会不会发生变化。
目前我实现的`163邮箱发送`功能，邮箱服务器属性设置的`key`值如下：
```
        //地址
        this.mProperties.put("mail.smtp.host",mHost);
        //端口号
        this.mProperties.put("mail.smtp.post",mPost);
        //是否验证
        this.mProperties.put("mail.smtp.auth",isAuth);
```
若其他邮箱设置时,以上`key`值不发生变化，变的仅是`地址`,`端口`和`验证`。
那么，你可以像下面这样设置发送信息：
```
        EmailSender.getInstance()
                //默认服务器地址为"smtp.163.com",端口为"25"
                .setAddress("smtp.163.com", "25")
                //协议和验证,默认参数为 "smtp",true
                .setProperty("smtp",true)
                //默认字符集为utf-8
                .setCharsetName(EmailSender.UTF_8)
                .setFrom("xxxxx@163.com") //设置发件人邮箱地址
                .setFromNickName("奔跑的佩恩") //设置发件人昵称,不设置时默认昵称为邮箱地址名称
                .setAuthCode("16位邮箱授权码") //设置发件人邮箱授权码
                .setTitle("测试邮件") //设置发送邮件的标题
                .setContent("别打我，我是测试邮件") //设置发送邮件的内容
                .setFilePath(null) //添加附件,设置null时表示不发送附件
                .setTo(new String[]{"xxxxx@xxx.com"}) //设置收件邮箱地址数组
                //发送邮件,传null表示不监听发送邮件的结果
                .sendEmail(new EmailSender.OnSendEmailListener() {
                    @Override
                    public void success() {
                        LogUtil.i("====邮件发送成功======");
                        ToastUtil.shortShow("邮件发送成功");
                    }

                    @Override
                    public void failed(String message) {
                        LogUtil.i("====邮件发送失败=====message="+message);
                        ToastUtil.shortShow("邮件发送失败:"+message);
                    }
                });
```
ok，如果连服务器属性设置的`key`也发生了变化，那么你可以像下面这样设置邮件的发送:
```
        EmailSender.getInstance()
                .setHost("新地址key","服务器地址")
                .setPost("新端口key","服务器端口")
                .setAuthKey("新验证key")
                //协议和验证,默认参数为 "smtp",true
                .setProperty("新协议名",true)
                //默认字符集为utf-8
                .setCharsetName(EmailSender.UTF_8)
                .setFrom("xxxxx@xxx.com") //设置发件人邮箱地址
                .setFromNickName("奔跑的佩恩") //设置发件人昵称,不设置时默认昵称为邮箱地址名称
                .setAuthCode("16位邮箱授权码") //设置发件人邮箱授权码
                .setTitle("测试邮件") //设置发送邮件的标题
                .setContent("别打我，我是测试邮件") //设置发送邮件的内容
                .setFilePath(null) //添加附件,设置null时表示不发送附件
                .setTo(new String[]{"xxxxx@xxx.com"}) //设置收件邮箱地址数组
                //发送邮件,传null表示不监听发送邮件的结果
                .sendEmail(new EmailSender.OnSendEmailListener() {
                    @Override
                    public void success() {
                        LogUtil.i("====邮件发送成功======");
                        ToastUtil.shortShow("邮件发送成功");
                    }

                    @Override
                    public void failed(String message) {
                        LogUtil.i("====邮件发送失败=====message="+message);
                        ToastUtil.shortShow("邮件发送失败:"+message);
                    }
                });
```

