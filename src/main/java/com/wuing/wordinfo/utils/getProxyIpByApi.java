package com.wuing.wordinfo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

public class getProxyIpByApi {
    //通过ip接口获取所能使用的代理IP
    public static String getIPByUrlApi() {
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            public String call() {
                HttpURLConnection conn = null;
                Reader in = null;
                URL url = null;
                StringBuilder sb = null;
                String response = null;
                try {
                    url = new URL("http://http.9vps.com/getip.asp?username=wuming123&pwd=296452367288709ec4f7bc6499966502&geshi=1&fenge=1&fengefu=&getnum=1");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                } catch (IOException e) {
                    System.out.println("调用urlAPI异常" + e);
                    conn.disconnect();
                    return null;
                }
                try {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
                    sb = new StringBuilder();
                    for (int c; (c = in.read()) >= 0; )
                        sb.append((char) c);
                    response = sb.toString();
                    System.out.println("未截取IP为"+response);
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    System.out.println("获取IP时间为："+dateTime.format(formatter));
                    return response;
                } catch (IOException e) {
                    System.out.println("读取ip流错误" + e);
                    return null;
                } finally {
                    try {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    } catch (Exception e) {
                        System.out.println("关闭连接异常" + e);
                        return null;
                    }
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        System.out.println("读取流关闭异常" + e);
                        return null;
                    }
                }
            }
        };
        try {
            Future<String> future = exec.submit(call);
            String response = future.get(1000 * 30, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            // 关闭线程池
            exec.shutdown();
            return response;
        } catch (TimeoutException e) {
            System.out.println("IP处理超时啦...."+e);
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
