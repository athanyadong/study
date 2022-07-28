package com.athan.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @aurhor yyh
 * @description http工具类
 * @date 2022/7/28 11:55
 */
@Slf4j
public class HttpUtil {
    //匹配的正则表达式
    private static Pattern linePattern = Pattern.compile("_(\\w)");//\W(大写)用来匹配非单词字符，它等价于"[^a-zA-Z0-9_]"。

    public static String doGet(String url, Map<String, String> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            //构建  创建uri
            URIBuilder builder = new URIBuilder(url);
            if (!params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    //往uri中添加参数
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            //构建成功
            URI uri = builder.build();
            //创建HTTPGET请求
            HttpGet httpGet = new HttpGet(uri);
            List<NameValuePair> paramList = new ArrayList<>();
            //构建Request
            RequestBuilder requestBuilder = RequestBuilder.get().setUri(new URI(url));
            requestBuilder.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
            //设置请求头
            httpGet.setHeader(new BasicHeader("Content-Type", "application/json;charset=UTF-8"));
            httpGet.setHeader(new BasicHeader("Accept", "*/*;charset=utf-8"));

            //执行请求
            closeableHttpResponse = httpClient.execute(httpGet);
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            log.info("HTTP GET 调用失败");
            e.printStackTrace();
        } finally {
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
            httpClient.close();
        }
        log.info("打印：{}", resultString);
        return resultString;
    }


    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            //创建http post请求
            HttpPost httpPost = new HttpPost(url);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    //添加请求头
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            //创建参数列表
            if (params != null && params.size() > 0) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;application/json;charset=UTF-8");
                httpPost.setHeader(new BasicHeader("Accept", "*/*;charset=utf-8"));
                response = httpClient.execute(httpPost);

                //判断返回状态是否是200
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            }
        } catch (Exception e) {
            log.error("接口调用失败:{}",e);
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        log.info("打印：{}",resultString);
        return resultString;
    }

    /**
     * 转换成驼峰字符串
     * @param data
     * @return
     */
    /**
     * 转换为驼峰json字符串
     * @param data
     * @return
     */
    public static String transformerUnderHumpData(String data) {
        data = data.toLowerCase();
        Matcher matcher = linePattern.matcher(data);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {//符合正则表达式要求的进来
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());//进来之后获取第一个字母进行转换大写
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
