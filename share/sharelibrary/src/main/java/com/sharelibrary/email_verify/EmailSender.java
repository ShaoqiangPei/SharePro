package com.sharelibrary.email_verify;

import android.os.Handler;

import com.sharelibrary.util.ShareLog;
import com.sharelibrary.util.StringUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Title: 邮件发送封装类
 *
 * description:
 * autor:pei
 * created on 2021/6/3
 */
public class EmailSender {

    //服务器参数key
    private static final String SERVER_HOST="mail.smtp.host"; //地址
    private static final String SERVER_POST="mail.smtp.post"; //端口号
    private static final String SERVER_AUTH="mail.smtp.auth"; //验证

    //服务器参数value
    private static final String DEFAULT_HOST="smtp.163.com"; // 默认地址
    private static final String DEFAULT_POST="25"; // 默认端口
    private static final boolean DEFAULT_AUTH=true; // 默认验证
    private static final String MIXED="mixed"; //MimeMultipart参数
    private static final String DEFAULT_PROTOCOL="smtp"; //默认使用SMTP协议发送邮件
    private static final int AUTH_CODE_LENGTH=16; //发件人邮箱授权码位数

    //字符集
    public static final String GBK="gbk";
    public static final String UTF_8="UTF-8";

    //发送邮件返回结果
    private static final int SUCCESS_CODE=0x11;
    private static final int FAILED_CODE=0x12;

    private Properties mProperties=new Properties();
    private Session mSession;
    private Message message;
    private MimeMultipart multipart;

    //服务器
    private String mHostKey=SERVER_HOST; //地址key
    private String mPostKey=SERVER_POST; //端口key
    private String mAuthKey=SERVER_AUTH; //验证key

    private String mHost=DEFAULT_HOST; //默认地址
    private String mPost=DEFAULT_POST; //默认端口
    private boolean isAuth=DEFAULT_AUTH; //是否验证,默认为true,即默认验证
    private String mProtocol=DEFAULT_PROTOCOL; //协议(默认使用SMTP协议发送邮件)

    //发件箱
    private String mCharsetName= UTF_8; //默认字符集为 utf-8
    private String mFrom;//发件箱邮箱地址
    private String mFromNickName;//发件箱昵称
    private String mAuthCode; //发件人邮箱授权码(16位字符串)
    private String mTitle; //发邮件标题
    private String mContent; //邮件发送内容
    private String mFilePath; //发送附件文件路径

    //收件箱
    private String mTos[]; //收件箱地址数组

    //发送邮箱结果监听
    private OnSendEmailListener mOnSendEmailListener;

    //处理发送结果
    private EmailHandler mEmailHandler=new EmailHandler(EmailSender.this, new OnEmailHandListener() {
        @Override
        public void handle(Object obj, android.os.Message msg) {
            if(obj instanceof EmailSender){
               switch (msg.what) {
                   case EmailSender.SUCCESS_CODE:
                       mOnSendEmailListener.success();
                       break;
                   case EmailSender.FAILED_CODE:
                       String error=msg.obj.toString();
                       mOnSendEmailListener.failed(error);
                       break;
                   default:
                       break;
               }
            }
        }
    });

    private EmailSender(){}

    private static class Holder {
        private static EmailSender instance = new EmailSender();
    }

    public static EmailSender getInstance() {
        return Holder.instance;
    }

    /**设置地址**/
    public EmailSender setHost(String key,String host){
        this.mHostKey=key;
        this.mHost=host;
        return this;
    }

    /**设置端口**/
    public EmailSender setPost(String key,String port){
        this.mPostKey=key;
        this.mPost=port;
        return this;
    }

    /**设置验证 key**/
    public EmailSender setAuthKey(String key){
        this.mAuthKey=key;
        return this;
    }

    /***
     * 设置邮箱服务器属性
     * (不设置的话默认地址为:"smtp.163.com",默认端口为：25)
     *
     * @param host: 邮箱服务器地址
     * @param port: 邮箱服务器端口
     * @return
     */
    public EmailSender setAddress(String host, String port){
        this.mHost=host;
        this.mPost=port;
        return this;
    }

    /***
     * 设置服务器协议及验证
     *
     * @param protocol:协议,默认 "smtp"
     * @param auth：是否验证, 默认true
     * @return
     */
    public EmailSender setProperty(String protocol,boolean auth){
        this.mProtocol=protocol;
        this.isAuth=auth;
        return this;
    }

    /***
     * 设置邮件字符集(默认字符集为EmailSender2.UTF_8)
     *
     * @param charsetName：EmailSender.GBK 或 EmailSender.UTF_8
     * @return
     */
    public EmailSender setCharsetName(String charsetName){
        this.mCharsetName=charsetName;
        return this;
    }

    /***
     * 设置发件人邮箱地址
     *
     * @param from：如：xxxx@163.com
     * @return
     */
    public EmailSender setFrom(String from){
        this.mFrom=from;
        return this;
    }

    /***
     * 设置发件人昵称(不设置时默认昵称为邮箱地址名称)
     *
     * @param fromNickName
     * @return
     */
    public EmailSender setFromNickName(String fromNickName){
        this.mFromNickName=fromNickName;
        return this;
    }

    /***
     * 设置发件人邮箱授权码
     *
     * @param authCode: 16位字符串
     * @return
     */
    public EmailSender setAuthCode(String authCode){
        this.mAuthCode=authCode;
        return this;
    }

    /***
     * 设置发送邮件的标题
     *
     * @param title
     * @return
     */
    public EmailSender setTitle(String title){
        this.mTitle=title;
        return this;
    }

    /***
     * 设置发送邮件的内容
     *
     * @param content
     * @return
     */
    public EmailSender setContent(String content){
        this.mContent=content;
        return this;
    }

    /***
     * 设置发送附件文件地址
     *
     * @param filePath:设置null时表示不发送附件
     *                 要发送附件时则填附件文件路径
     * @return
     */
    public EmailSender setFilePath(String filePath){
        this.mFilePath=filePath;
        return this;
    }

    /***
     * 设置收件邮箱地址数组
     *
     * @param tos：示例 new String[]{"xxxx@xxx.com"}
     * @return
     */
    public EmailSender setTo(String tos[]){
        this.mTos=tos;
        return this;
    }

    /***
     * 发送邮件
     *
     * @param listener 传 null 表示不监听发送邮件的结果
     */
    public void sendEmail(OnSendEmailListener listener) {
        this.mOnSendEmailListener=listener;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //邮件是否发送成功
                boolean flag=false;
                try {
                    //设置服务器地址和端口，可以查询网络
                    setProperties();
                    //分别设置发件人，邮件标题和文本内容
                    setMessage();
                    //设置收件人
                    setReceiver();
                    //添加附件换成你手机里正确的路径(如:mFilePath="/sdcard/emil/emil.txt")
                    if(StringUtil.isNotEmpty(mFilePath)) {
                        addAttachment();
                    }
                    //发送邮件
                    sendTransaction();

                    //发送返回结果
                    flag=true;
                    sendResult(null,flag);
                } catch (MessagingException e) {
                    e.printStackTrace();

                    //发送返回结果
                    flag=false;
                    sendResult(e.getMessage(),flag);
                }

                //打印信息
                printInfo(flag);

            }

            /***
             * 打印信息
             *
             * @param flag 邮件是否发送成功
             *             true：发送成功
             *             false:发送失败
             */
            private void printInfo(boolean flag){
                ShareLog.i("***************发送邮件相关信息打印***************");
                ShareLog.i("=邮箱服务器地址key:"+mHostKey+"  服务器地址:"+mHost);
                ShareLog.i("=邮箱服务器端口key:"+mPostKey+"  服务器端口:"+mPost);
                ShareLog.i("=邮箱服务器验证key:"+mAuthKey);
                ShareLog.i("=邮箱服务器协议:"+mProtocol+"  是否验证:"+isAuth);
                ShareLog.i("=字符集:"+mCharsetName);
                ShareLog.i("=发件箱邮箱地址:"+mFrom);
                ShareLog.i("=发件箱昵称:"+mFromNickName);
                ShareLog.i("=发邮件标题:"+mTitle);
                ShareLog.i("=邮件发送内容:"+mContent);
                ShareLog.i("=发送附件文件路径:"+mFilePath);
                if(mTos!=null&&mTos.length>0) {
                    for(String address:mTos){
                        ShareLog.i("=收件箱邮箱地址:" + address);
                    }
                }
                ShareLog.i("=邮件是否发送成功:"+flag);
            }

        }).start();
    }


    //==========================================================

    /**设置邮箱服务器属性**/
    private void setProperties(){
        //地址key
        if(StringUtil.isEmpty(mHostKey)){
            throw new NullPointerException("====邮箱服务器地址key不能为null=====");
        }
        //端口key
        if(StringUtil.isEmpty(mPostKey)){
            throw new NullPointerException("====邮箱服务器端口key不能为null=====");
        }
        //验证key
        if(StringUtil.isEmpty(mAuthKey)){
            throw new NullPointerException("====邮箱服务器验证key不能为null=====");
        }
        //地址
        if(StringUtil.isEmpty(mHost)){
            throw new NullPointerException("====邮箱服务器地址不能为null=====");
        }
        //端口
        if(StringUtil.isEmpty(mPost)){
            throw new NullPointerException("====邮箱服务器端口不能为null=====");
        }
        //地址
        this.mProperties.put(mHostKey,mHost);
        //端口号
        this.mProperties.put(mPostKey,mPost);
        //是否验证
        this.mProperties.put(mAuthKey,isAuth);
        this.mSession= Session.getInstance(mProperties);
        this.message = new MimeMessage(mSession);
        this.multipart = new MimeMultipart(EmailSender.MIXED);
    }

    /**设置发件人信息**/
    private void setMessage() throws MessagingException {
        //设置发件邮箱地址
        if(StringUtil.isEmpty(mFrom)){
            throw new NullPointerException("====发件人邮箱地址不能为null,参数样式: \"xxxx@163.com\" =====");
        }
        //设置发件标题
        if(StringUtil.isEmpty(mTitle)){
            throw new NullPointerException("====发件人邮箱标题不能为null=====");
        }
        //设置发件内容
        if(StringUtil.isEmpty(mContent)){
            throw new NullPointerException("====发件人邮箱内容不能为null=====");
        }
        //设置字符集
        if(StringUtil.isEmpty(mCharsetName)){
            throw new NullPointerException("====发件字符集不能为null(一般为EmailSender2.GBK 或 EmailSender.UTF_8)=====");
        }
        //设置发件人昵称
        String nick=null;
        if(StringUtil.isNotEmpty(mFromNickName)){
            try {
                nick = javax.mail.internet.MimeUtility.encodeText(mFromNickName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(StringUtil.isNotEmpty(nick)){
            this.message.setFrom(new InternetAddress(nick+"<"+mFrom+">"));
        }else{
            this.message.setFrom(new InternetAddress(mFrom));
        }
        this.message.setSubject(mTitle);
        //纯文本的话用setText()就行，不过有附件就显示不出来内容了
        MimeBodyPart textBody = new MimeBodyPart();
        textBody.setContent(mContent,"text/html;charset="+mCharsetName);
        this.multipart.addBodyPart(textBody);
    }

    /**
     * 添加附件
     * @throws MessagingException
     */
    public void addAttachment() throws MessagingException {
        //mFilePath="/sdcard/emil/emil.txt"
        FileDataSource fileDataSource = new FileDataSource(new File(mFilePath));
        DataHandler dataHandler = new DataHandler(fileDataSource);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setDataHandler(dataHandler);
        mimeBodyPart.setFileName(fileDataSource.getName());
        this.multipart.addBodyPart(mimeBodyPart);
    }

    /**
     * 设置收件人信息
     * @throws MessagingException
     */
    private void setReceiver() throws MessagingException {
        //设置收件箱地址数组
        if(mTos==null||mTos.length==0){
            throw new NullPointerException("====收件人邮箱地址数组不能为null,参数样式: new String[]{\"xxxxx@xx.com\"}=====");
        }
        Address[] address = new InternetAddress[mTos.length];
        for(int i=0;i<mTos.length;i++){
            address[i] = new InternetAddress(mTos[i]);
        }
        this.message.setRecipients(Message.RecipientType.TO, address);
    }

    /**
     * 发送邮件
     *
     * @throws MessagingException
     */
    private void sendTransaction() throws MessagingException {
        if(StringUtil.isEmpty(mAuthCode)){
            throw new NullPointerException("====发件人邮箱授权码不能为null======");
        }else if(mAuthCode.length()!= EmailSender.AUTH_CODE_LENGTH){
            throw new NullPointerException("====发件人邮箱授权码为"+ EmailSender.AUTH_CODE_LENGTH+"字符串======");
        }
        //协议
        if(StringUtil.isEmpty(mProtocol)){
            throw new NullPointerException("====协议不能为null======");
        }
        //发送时间
        this.message.setSentDate(new Date());
        //发送的内容，文本和附件
        this.message.setContent(this.multipart);
        this.message.saveChanges();
        //创建邮件发送对象，并指定其使用SMTP协议发送邮件
        Transport transport=mSession.getTransport(mProtocol);
        //登录邮箱
        transport.connect(mHost,mFrom,mAuthCode);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
    }

    /***
     * 发送结果
     *
     * @param errorMessage 错误信息
     * @param success 是否发送成功
     */
    private void sendResult(String errorMessage,boolean success){
        //发送返回结果
        if(mOnSendEmailListener!=null) {
            android.os.Message handleMessage=null;
            if(success){
                handleMessage= mEmailHandler.obtainMessage(EmailSender.SUCCESS_CODE);
            }else{
                handleMessage= mEmailHandler.obtainMessage(EmailSender.FAILED_CODE, errorMessage);
            }
            mEmailHandler.sendMessage(handleMessage);
        }
    }


    private class EmailHandler<T> extends Handler {

        //弱引用(引用外部类)
        private WeakReference<T> weakReference;
        private OnEmailHandListener mOnEmailHandListener;

        public EmailHandler(Object obj, OnEmailHandListener listener) {
            if(obj==null){
                throw new NullPointerException("=====传入obj不能为空=====");
            }
            //构造弱引用
            weakReference = new WeakReference<>((T) obj);
            this.mOnEmailHandListener=listener;
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            //通过弱引用获取外部类.
            Object obj = weakReference.get();
            //进行非空再操作
            if (obj != null&&mOnEmailHandListener!=null) {
                mOnEmailHandListener.handle(obj,msg);
            }
            //移除消息
            removeMessages(msg.what);
        }
    }

    private interface OnEmailHandListener{
        void handle(Object obj, android.os.Message msg);
    }

    public interface OnSendEmailListener{
        //邮件发送成功
        void success();
        //邮件发送失败
        void failed(String errorMessage);
    }



}
