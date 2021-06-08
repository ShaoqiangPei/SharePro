package com.sharelibrary.entity;

import java.io.Serializable;

/**
 * Title: 国家代码
 * description:
 * autor:pei
 * created on 2021/6/7
 */
public class ZoneData implements Serializable {

    private String zone;
    private String rule;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
