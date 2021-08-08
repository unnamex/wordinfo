package com.wuing.wordinfo.utils;

import com.wuing.wordinfo.model.Commondic;

import java.util.HashMap;
import java.util.Map;

import static com.wuing.wordinfo.utils.dealWordResponse.parseJsonString;
import static com.wuing.wordinfo.utils.getWordResponse.httpPost;

public class exportApi {
    //将类接口暴露出，中转调用请求数据，解析数据
    public static Map<Integer, Commondic> exportApi(String word, int orgianlId, String host, String port){

        Map<Integer,Commondic> map = new HashMap();
        String response = null;
        try {
            //接收返回的数据，等待parseJsonString方法的处理
            response = httpPost(word,host,port);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        try {
            //处理response数据，提取出单词信息组成map返回前台
            map = parseJsonString(response,orgianlId);
            return map;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
