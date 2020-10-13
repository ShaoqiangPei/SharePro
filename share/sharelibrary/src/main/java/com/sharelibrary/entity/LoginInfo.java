package com.sharelibrary.entity;

import java.io.Serializable;

/**
 * Title:qq登录成功获取的用户信息
 * description:
 * autor:pei
 * created on 2020/9/7
 */
public class LoginInfo implements Serializable {


    /**
     * ret : 0
     * msg :
     * is_lost : 0
     * nickname : 冰锋饮雪
     * gender : 男
     * gender_type : 1
     * province : 上海
     * city : 徐汇
     * year : 1940
     * constellation :
     * figureurl : http://qzapp.qlogo.cn/qzapp/101899805/80AB7BEBDF64FDE2752370E0F1C287BC/30
     * figureurl_1 : http://qzapp.qlogo.cn/qzapp/101899805/80AB7BEBDF64FDE2752370E0F1C287BC/50
     * figureurl_2 : http://qzapp.qlogo.cn/qzapp/101899805/80AB7BEBDF64FDE2752370E0F1C287BC/100
     * figureurl_qq_1 : http://thirdqq.qlogo.cn/g?b=oidb&k=NdjqAic0uRhnq9QqbicW2YEw&s=40&t=1555532914
     * figureurl_qq_2 : http://thirdqq.qlogo.cn/g?b=oidb&k=NdjqAic0uRhnq9QqbicW2YEw&s=100&t=1555532914
     * figureurl_qq : http://thirdqq.qlogo.cn/g?b=oidb&k=NdjqAic0uRhnq9QqbicW2YEw&s=640&t=1555532914
     * figureurl_type : 1
     * is_yellow_vip : 0
     * vip : 0
     * yellow_vip_level : 0
     * level : 0
     * is_yellow_year_vip : 0
     */

    private String openId;
    private int ret;
    private String msg;
    private int is_lost;
    private String nickname;//qq昵称
    private String gender;//性别
    private int gender_type;
    private String province;
    private String city;
    private String year;
    private String constellation;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;  //qq 头像
    private String figureurl_qq_2;
    private String figureurl_qq;
    private String figureurl_type;
    private String is_yellow_vip;
    private String vip;
    private String yellow_vip_level;
    private String level;
    private String is_yellow_year_vip;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIs_lost() {
        return is_lost;
    }

    public void setIs_lost(int is_lost) {
        this.is_lost = is_lost;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getGender_type() {
        return gender_type;
    }

    public void setGender_type(int gender_type) {
        this.gender_type = gender_type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getFigureurl_qq() {
        return figureurl_qq;
    }

    public void setFigureurl_qq(String figureurl_qq) {
        this.figureurl_qq = figureurl_qq;
    }

    public String getFigureurl_type() {
        return figureurl_type;
    }

    public void setFigureurl_type(String figureurl_type) {
        this.figureurl_type = figureurl_type;
    }

    public String getIs_yellow_vip() {
        return is_yellow_vip;
    }

    public void setIs_yellow_vip(String is_yellow_vip) {
        this.is_yellow_vip = is_yellow_vip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellow_vip_level() {
        return yellow_vip_level;
    }

    public void setYellow_vip_level(String yellow_vip_level) {
        this.yellow_vip_level = yellow_vip_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIs_yellow_year_vip() {
        return is_yellow_year_vip;
    }

    public void setIs_yellow_year_vip(String is_yellow_year_vip) {
        this.is_yellow_year_vip = is_yellow_year_vip;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "openId='" + openId + '\'' +
                ", ret=" + ret +
                ", msg='" + msg + '\'' +
                ", is_lost=" + is_lost +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", gender_type=" + gender_type +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", year='" + year + '\'' +
                ", constellation='" + constellation + '\'' +
                ", figureurl='" + figureurl + '\'' +
                ", figureurl_1='" + figureurl_1 + '\'' +
                ", figureurl_2='" + figureurl_2 + '\'' +
                ", figureurl_qq_1='" + figureurl_qq_1 + '\'' +
                ", figureurl_qq_2='" + figureurl_qq_2 + '\'' +
                ", figureurl_qq='" + figureurl_qq + '\'' +
                ", figureurl_type='" + figureurl_type + '\'' +
                ", is_yellow_vip='" + is_yellow_vip + '\'' +
                ", vip='" + vip + '\'' +
                ", yellow_vip_level='" + yellow_vip_level + '\'' +
                ", level='" + level + '\'' +
                ", is_yellow_year_vip='" + is_yellow_year_vip + '\'' +
                '}';
    }
}
