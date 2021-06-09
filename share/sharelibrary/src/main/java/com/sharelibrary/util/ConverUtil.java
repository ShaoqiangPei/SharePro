package com.sharelibrary.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sharelibrary.entity.SmsError;
import com.sharelibrary.entity.ZoneData;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: 数据转换工具类
 * description:
 * autor:pei
 * created on 2021/6/9
 */
public class ConverUtil {

    public static List<ZoneData> getZoneList(Object data){
        Gson gson=new Gson();
        String json= gson.toJson(data);
        List<ZoneData> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, ZoneData.class));
        }
        return list;
    }

    public static SmsError getSmsError(String json){
        Gson gson=new Gson();
        SmsError smsError = gson.fromJson(json, SmsError.class);
        return smsError;
    }

}
