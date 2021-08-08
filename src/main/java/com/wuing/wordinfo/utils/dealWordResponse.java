package com.wuing.wordinfo.utils;

import com.wuing.wordinfo.model.Commondic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dealWordResponse {

    //处理返回的json数据
    public static Map parseJsonString(String jsonData, int originalId) {
        Map<Integer, Commondic> commondicMap = new HashMap<>();
        //new CommodicWord 对象,取单词信息
        Commondic CommodicWord = new Commondic();
        Commondic commondic=null;
        Commondic commondic_t =null;
        JSONObject jsonObject = new JSONObject(jsonData);
        String query = null;
        try {
            //得到源单词
            query = jsonObject.get("query").toString();
        } catch (JSONException e) {
            System.out.println("query未发现"+e);
            return null;
        }
        //将源单词给bean
        CommodicWord.setVcVocabulary(query);
        CommodicWord.setOriginalId(originalId);
        //判断源词汇是否为单词，也可能为短语，ture为单词
        Object isWord = jsonObject.get("isWord");

        try{
            //得到词汇的网络释义
            //web字段可能不存在，为使程序呢继续运行，放入 try，catch块中
            JSONArray web = jsonObject.getJSONArray("web");
            for(int i=0;i<web.length();i++){
                if (i == 0){
                    //i等于0为网络解释的第一组，一般为源词汇，下面做判断是不是源词汇
                    String translationNet =null;
                    //网络释义一般为数组且第一个为源单词释义
                    JSONObject webOne = web.getJSONObject(0);
                    //得到数组为0的value
                    JSONArray webOneValue = webOne.getJSONArray("value");
                    //得到数组为0的key
                    String key = webOne.get("key").toString();
                    //判断得到的网络释义的第一条key是否为源词汇，若是则插入原测绘的网络释义，若不是当多新的词条插入
                    if (key.equals(query)|| key.equals(query.toUpperCase())||key.equals(query.toLowerCase())){
                        for(Object item : webOneValue){
                            translationNet = translationNet+","+item.toString();
                        }
                        //处理拼接好的字符串，将字符串中的“null,”替换掉
                        String dealAfterString=translationNet.replaceAll("null,", "").trim();
                        CommodicWord.setVcTranslationNet(dealAfterString);
                    }else {
                        //判断得出非源词汇，则把该词条当新词插入
                        commondic =new Commondic();
                        for(Object item : webOneValue){
                            translationNet = translationNet+","+item.toString();
                        }
                        //处理拼接好的字符串，将字符串中的“null,”替换掉
                        String dealAfterString=translationNet.replaceAll("null,", "").trim();
                        commondic.setVcTranslation(dealAfterString);
                        commondic.setVcVocabulary(key);
                        commondic.setIsword(1);
                        commondicMap.put(web.length(), commondic);
                    }
                }else {
                    //非网络翻译的第一组，一般为词组，当做新词条插入
                    commondic_t =new Commondic();
                    JSONObject webOne = web.getJSONObject(i);
                    JSONArray webOneValue = webOne.getJSONArray("value");
                    Object key = webOne.get("key");
                    String translation =null;
                    for(Object item : webOneValue){
                        translation = translation+","+item.toString();
                    }
                    //处理拼接好的字符串，将字符串中的“,null”替换掉
                    String dealAfterString=translation.replaceAll("null,", "").trim();
                    commondic_t.setVcTranslation(dealAfterString);
                    commondic_t.setVcVocabulary(key.toString());
                    commondic_t.setIsword(1);
                    commondicMap.put(i, commondic_t);
                }
            }
        }catch (Exception e){

        }
        //判断得到的词汇是为单词还是短语
        if (isWord.equals(true)){
            //设置词汇标签为0,0：单词，1：词组
            CommodicWord.setIsword(0);
            JSONObject baseic = jsonObject.getJSONObject("basic");
            //wfs 为时态变换 可能不存在 所以做异常
            try {
                JSONArray wfs = baseic.getJSONArray("wfs");
                String wfsString = null;
                for (int i =0;i<wfs.length();i++){
                    wfsString= wfsString+"/"+ wfs.getJSONObject(i).getJSONObject("wf").get("name")+":"+wfs.getJSONObject(i).getJSONObject("wf").get("value");
                }
                String finalWfsString =wfsString.replaceAll("null/", "").trim();
                CommodicWord.setExchange(finalWfsString);
            } catch (JSONException e) {
                System.out.println("wfs未发现"+e);
            }
            //exam_type 可能不存在，捕捉异常
            try {
                String examTypeString =null;
                String explainString = null;
                //获取词汇标签，如 cet4，cet6，考研等等
                JSONArray exam_type =baseic.getJSONArray("exam_type");
                //获取词汇的准确解释
                JSONArray explainNeedDealArray = baseic.getJSONArray("explains");
                //处理词汇标签,让其成为字符串
                for (Object item : exam_type){
                    examTypeString = examTypeString+"/"+item.toString();
                }
                String examTypeStringTrim = examTypeString.replaceAll("null/", "").trim();
                CommodicWord.setTag(examTypeStringTrim);
                //获取词汇音标
                CommodicWord.setVcSpeechUs("["+baseic.get("us-phonetic").toString()+"]");
                CommodicWord.setVcSpeechUk("["+baseic.get("uk-phonetic").toString()+"]");
                //处理准确解释，让其成为成为String字符串
                for (Object item: explainNeedDealArray){
                    explainString = explainString+","+item.toString();
                }
                String explainDealAfter=explainString.replaceAll("null,", "").trim();
                CommodicWord.setVcTranslation(explainDealAfter);
            }catch (Exception e){
                //若有异常，只取词汇准确解释
                String exStringExplain =null;
                JSONArray explains = baseic.getJSONArray("explains");
                for (Object item :explains){
                    exStringExplain = exStringExplain+","+item.toString();
                }
                String explainDealAfter = exStringExplain.replaceAll("null,", "").trim();
                CommodicWord.setVcTranslation(explainDealAfter);
            }

        }else if (isWord.equals(false)){
            String phaseTrans =null;
            JSONArray phaseTranslation=jsonObject.getJSONArray("translation");
            for (Object item : phaseTranslation){
                phaseTrans = phaseTrans+","+item.toString();
            }
            String tranDealAfter = phaseTrans.replaceAll("null,", "").trim();
            CommodicWord.setVcTranslation(tranDealAfter);
        }
        commondicMap.put(0, CommodicWord);
        return  commondicMap;
    }
}
