package com.wuing.wordinfo.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.*;

public class getWordResponse {
    //通过代理ip请求单词接口
    public static String httpPost(String word,String host,String port) {
        //添加内部类，设置任务等待时间，若方法执行时间超过10秒，则返回null给调用方法
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            public String call() {
                List<String> userAgent = new ArrayList<>();
                //添加user-agent伪装请求头
                userAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
                userAgent.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv,2.0.1) Gecko/20100101 Firefox/4.0.1");
                userAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18362");
                userAgent.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
                userAgent.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:65.0) Gecko/20100101 Firefox/65.0");
                userAgent.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0.3 Safari/605.1.15");
                userAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
                userAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763");
                userAgent.add("Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) CriOS/31.0.1650.18 Mobile/11B554a Safari/8536.25 ");
                userAgent.add("Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12F70 Safari/600.1.4");
                userAgent.add("Mozilla/5.0 (Linux; Android 4.2.1; M040 Build/JOP40D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36");
                userAgent.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
                userAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36");
                userAgent.add("Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5");
                // 设置代理IP
                Random random = new Random();
                int user_agent = random.nextInt(userAgent.size());
                System.getProperties().setProperty("proxySet", "true");
                System.getProperties().setProperty("proxyHost", host);
                System.getProperties().setProperty("proxyPort", port);
                System.out.println("所用ip为"+host+":"+port);
                Reader in = null;
                HttpURLConnection conn =null;
                URL url = null;
                try {
                    url = new URL("https://aidemo.youdao.com/trans");
                } catch (MalformedURLException e) {
                    System.out.println(e);
                    return null;
                }
                //post参数
                Map<String,Object> params = new LinkedHashMap<>();
                params.put("q",word);
                params.put("from", "en");
                params.put("to", "zh-CHS");

                //开始访问
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String,Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    try {
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        System.out.println(e);
                        return null;
                    }
                }
                //设置写入参数属性
                byte[] postDataBytes = null;
                try {
                    postDataBytes = postData.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    System.out.println(e);
                    return null;
                }
                //设置连接属性
                try {
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                } catch (IOException e) {
                    System.out.println(e);
                    conn.disconnect();
                    return null;
                }
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setRequestProperty("User-Agent",userAgent.get(user_agent));
                System.out.println("所用USERAGENT为"+userAgent.get(user_agent));
                conn.setDoOutput(true);
                try {
                    //将post参数写入流中
                    conn.getOutputStream().write(postDataBytes);
                } catch (IOException e) {
                    System.out.println("写入流异常"+e);
                    conn.disconnect();
                    return null;
                }

                try {
                    //从流中读取数据
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    for (int c; (c = in.read()) >= 0;)
                        sb.append((char)c);
                    String response = sb.toString();
                    return response;
                } catch (IOException e) {
                    System.out.println("读取流异常"+e);
                    return null;
                }finally {
                    try {
                        if (conn !=null){
                            conn.disconnect();
                        }
                    }catch (Exception e){
                        return null;
                    }
                    try {
                        if (in!=null){
                            in.close();
                        }
                    }catch (Exception e){
                        return null;
                    }
                }
            }
        };
        try {
            Future<String> future = exec.submit(call);
            String response = future.get(1000 * 10, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            // 关闭线程池
            exec.shutdown();
            return response;
        } catch (TimeoutException e) {
            System.out.println("单词信息处理超时啦...."+e);
            // 关闭线程池
            exec.shutdown();
            return null;
        } catch (Exception e) {
            System.out.println("处理失败."+e);
            // 关闭线程池
            exec.shutdown();
            return null;
        }
    }
}
