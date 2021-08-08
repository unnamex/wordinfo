package com.wuing.wordinfo.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.RateLimiter;
import com.wuing.wordinfo.model.Commondic;
import com.wuing.wordinfo.model.VocTb;
import com.wuing.wordinfo.service.CommondicService;
import com.wuing.wordinfo.service.VocTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class runAll {
    @Autowired
    VocTbService vocTbService;

    @Autowired
    CommondicService commondicService;

    private static int j =0;
    public void runableWord(){
        RateLimiter rateLimiter = RateLimiter.create(2);
        QueryWrapper queryVo = new QueryWrapper();
        QueryWrapper vc_vocabulary = queryVo.select("original_id","vc_vocabulary");
        List<VocTb> vocList = vocTbService.list(vc_vocabulary);
        //ip
        String host= null;
        //ip端口
        String port =null;
        String response=null;
        List<Commondic>  wordList =null;
        StringBuilder sb=null;
        int count=0;
        for (int i =0;i<wordList.size();i++){
            //i值为单词顺序，意为第几个单词
            System.out.println("i值为++++++"+i);
            //j值记录每个ip所请求的次数
            j=j+1;
            System.out.println("j值为++++++"+j);
            //规定j等于15或者j等于1的时候向代理ip接口请求新ip，由于被爬接口频率限制，一个ip设置请求次数为14次，且一个ip使用时间为3分钟
            if (j==15||j==1){
                try {
                    response = getProxyIpByApi.getIPByUrlApi();
                    if (response.equals(null)){
                        //返回的数据为空，则对ip重新请求，i值使其不变
                        System.out.println("得到的ip为空");
                        i=i-1;
                        j=0;
                        //count用来记录，连续请求ip的次数，若连续请求ip超过三个，且这三个IP不进行请求单词数据，则为了防止ip浪费，终止程序
                        count++;
                        if (count>3){
                            System.out.println("内部count值为+"+count);
                            //打印获取ip的时间
                            LocalDateTime dateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                            System.out.println("多次获取连续IP导致失败时间为："+dateTime.format(formatter));
                            return;
                        }
                        continue;
                    }
                }catch (Exception e){
                    System.out.println("得到的ip为空"+e);
                    i=i-1;
                    j=0;
                    continue;
                }

                try {
                    //分割获取的ip与端口
                    host = response.substring(0, response.indexOf(":"));
                    port = response.substring(response.indexOf(":")+1);
                } catch (Exception e) {
                    System.out.println("Ip截取失败"+e);
                    i=i-1;
                    j=0;
                    continue;
                }
                j=2;
            }
            count=0;
            //令牌桶算法进行限流，每次获取一个令牌
            Preconditions.checkState(rateLimiter.tryAcquire(1, 6000, TimeUnit.SECONDS));
            wordList = new ArrayList<>();
            Map<Integer,Commondic> wordMap = new HashMap<>();
            try {
                wordMap = exportApi.exportApi(vocList.get(i).getVcVocabulary(),vocList.get(i).getOriginalId(),host,port);

                for (int n =0;n<wordMap.size();n++){
                    wordList.add(wordMap.get(n));
                }
//                commondicService.saveBatch(wordList);
                System.out.println(wordList);
            } catch (Exception e) {
                i=i-1;
                System.out.println("Map为空"+e);
                continue;
            }
        }

    }

    public void runableDownload(){
        //有道api  美式：type=0   英式：type=1
         String baseUrl = "http://dict.youdao.com/dictvoice?type=1&audio=";
         //获取要爬取的单词列表
         QueryWrapper queryWrapper = new QueryWrapper();
         queryWrapper.select("vc_vocabulary");
         List<Commondic> wordList = commondicService.list(queryWrapper);
         //处理后的单词
         String word = null;
         //源单词
         String word_o =null;
         String wordUrl =null;
         for (int i=0;i<wordList.size();i++){
             word_o = wordList.get(i).getVcVocabulary();
             //犹豫有些词组中间会有空格，在请求到url时不能被识别，所以词组中的空格进行转移，%20代表空格
             word = word_o.replaceAll(" ", "%20");
             //单词的url为，接口地址加单词
             wordUrl = baseUrl+ word;
             //调用http方法
             DownloadUtils downloadUtils  = new DownloadUtils(wordUrl, word_o, "mp3","H:\\wordMp3\\words");
             try {
                 downloadUtils.httpDownload();
                 System.out.print("\t \t \t下载成功");
             } catch (Exception e) {
                 System.out.print("\t \t \t下载失败");
                 e.printStackTrace();
             }
         }
    }

}
