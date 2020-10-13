package com.sharelibrary.entity;


import java.io.Serializable;

/**
 * Title:qq登录授权对象
 * description:
 * autor:pei
 * created on 2020/9/7
 */
public class Author implements Serializable {


    /**
     * ret : 0
     * openid : 80AB7BEBDF64FDE2752370E0F1C287BC
     * access_token : 52913A045DAC80F4C7107916B3E733D3
     * pay_token : 6498BA454FD834DBDEAAA50ED684C02E
     * expires_in : 7776000
     * code :
     * proxy_code :
     * proxy_expires_in : 0
     * pf : desktop_m_qq-10000144-android-2002-
     * pfkey : 499bf49cdcfd5d00f1d3083678e7b853
     * msg :
     * login_cost : 313
     * query_authority_cost : -337888
     * authority_cost : 0
     * expires_time : 1607267160929
     */

    private int ret;
    private String openid;
    private String access_token;
    private String pay_token;
    private int expires_in;
    private String code;
    private String proxy_code;
    private int proxy_expires_in;
    private String pf;
    private String pfkey;
    private String msg;
    private int login_cost;
    private int query_authority_cost;
    private int authority_cost;
    private long expires_time;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProxy_code() {
        return proxy_code;
    }

    public void setProxy_code(String proxy_code) {
        this.proxy_code = proxy_code;
    }

    public int getProxy_expires_in() {
        return proxy_expires_in;
    }

    public void setProxy_expires_in(int proxy_expires_in) {
        this.proxy_expires_in = proxy_expires_in;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLogin_cost() {
        return login_cost;
    }

    public void setLogin_cost(int login_cost) {
        this.login_cost = login_cost;
    }

    public int getQuery_authority_cost() {
        return query_authority_cost;
    }

    public void setQuery_authority_cost(int query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }

    public int getAuthority_cost() {
        return authority_cost;
    }

    public void setAuthority_cost(int authority_cost) {
        this.authority_cost = authority_cost;
    }

    public long getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(long expires_time) {
        this.expires_time = expires_time;
    }
}
