package com.sharelibrary.entity;

import java.io.Serializable;

/**
 * Title: 短信验证错误的data
 * description:
 * autor:pei
 * created on 2021/6/7
 */
public class SmsError implements Serializable {

    private int httpStatus;
    private String description;
    private String detail;
    private String error;
    private int status;

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
