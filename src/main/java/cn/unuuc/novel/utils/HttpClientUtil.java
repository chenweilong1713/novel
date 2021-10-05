package cn.unuuc.novel.utils;

import cn.unuuc.novel.entity.Config;
import cn.unuuc.novel.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class HttpClientUtil {

    private static String coverPath;


    @Autowired
    private static ConfigService staticConfigService;

    @Autowired
    ConfigService configService;

    @PostConstruct
    public void init() {
        staticConfigService = configService;
    }


    @Value("${covePath}")
    public void setCoverPath(String path){
        HttpClientUtil.coverPath = path;
    }

    // HttpClient模拟浏览器发起GET请求
    public static String doGet(String url, Map<String,String> headers){
        Config configById = staticConfigService.getConfigById(1);
        long millis = configById.getDelay();
        // 如果为null 重复尝试5次
        for(int i=0;i<configById.getFailTime();i++){
            String html = doGetReal(url, headers,millis);
//            String html = doGetProxy(url,"127.0.0.1",10809,headers,0);
            if(html != null)
                return html;
            millis = millis+configById.getFailDelay();
        }
        return null;
    }

    private static synchronized String doGetReal(String url, Map<String,String> headers,long millis){
        // 延迟几秒
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 模拟浏览器发送
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 Edg/92.0.902.84");
        // 添加自定义header
        if(headers!=null){
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                httpGet.setHeader(next.getKey(),next.getValue());
            }
        }
        // 可关闭的响应
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = aDefault.execute(httpGet);

            // 状态码
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(HttpStatus.SC_OK == statusCode){
                // 获取响应结果
                HttpEntity entity = httpResponse.getEntity();

                // 工具类，对HttpEntity 操作的工具类
                String result = EntityUtils.toString(entity,"utf-8");
                // 确保输入流关闭
                EntityUtils.consume(entity);

                return result;
            }else {
                System.out.println("响应失败:"+statusLine.getStatusCode());
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (aDefault != null){
                try {
                    aDefault.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( httpResponse != null){
                try {
                    httpResponse.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 发起代理请求
    public static String doGetProxy(String url,String ip,Integer port,Map<String,String> headers,long millis){
        CloseableHttpClient aDefault = HttpClients.createDefault();
        // 构造httpGet请求对象
        HttpGet httpGet = new HttpGet(url);

        // 设置代理
        HttpHost proxy = new HttpHost(ip,port);
        RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);

        // 添加自定义header
        if(headers!=null){
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                httpGet.setHeader(next.getKey(),next.getValue());
            }
        }
        // 可关闭的响应
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = aDefault.execute(httpGet);

            // 获取响应结果
            HttpEntity entity = httpResponse.getEntity();

            // 工具类，对HttpEntity 操作的工具类
            String result = EntityUtils.toString(entity,"utf-8");
            // 确保输入流关闭
            EntityUtils.consume(entity);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (aDefault != null){
                try {
                    aDefault.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( httpResponse != null){
                try {
                    httpResponse.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    // 下载图片
    public static String doGetDown(String url,Map<String,String> headers){
        // 可关闭的httpClient客户端，相当于打开的浏览器
        CloseableHttpClient aDefault = HttpClients.createDefault();

        // 构造httpGet请求对象
        HttpGet httpGet = new HttpGet(url);

        if(headers!=null){
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                httpGet.setHeader(next.getKey(),next.getValue());
            }
        }
        // 可关闭的响应
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = aDefault.execute(httpGet);
            // 获取响应结果
            HttpEntity entity = httpResponse.getEntity();

            // 获取响应类型，判断是否为图片  image/jpg    image/png  ......
            String contentType = entity.getContentType().getValue();
            String suffix = ".jpg";
            if(contentType.contains("jpg")||contentType.contains("jpeg")){
                suffix = ".jpg";
            }else if(contentType.contains("bmp")||contentType.contains("bitmap")){
                suffix = ".bmp";
            }else if(contentType.contains("png")){
                suffix = ".png";
            }else if(contentType.contains("gif")){
                suffix = ".gif";
            }

            // 因为图片是二进制文件，需要转为字节数组（不管是文本还是图片都可以使用字节流的方式）
            byte[] bytes = EntityUtils.toByteArray(entity);
            // 定义输出流，输出文件
            UUID uuid = UUID.randomUUID();
            String replace = uuid.toString().replace("-", "");
            String fileUrl = coverPath+ File.separator+replace+suffix;
            FileOutputStream fos = new FileOutputStream(fileUrl);
            fos.write(bytes);
            // 确保输入流关闭
            EntityUtils.consume(entity);
            return replace+suffix;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (aDefault != null){
                try {
                    aDefault.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( httpResponse != null){
                try {
                    httpResponse.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



}
