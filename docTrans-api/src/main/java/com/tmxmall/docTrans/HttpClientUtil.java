package com.tmxmall.docTrans;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClientUtil {
    private static final int DEFAULT_SOCKET_TIMEOUT = 5000;

    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;

    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 5000;

    private static RequestConfig defaultRequestConfig;
    static {
        defaultRequestConfig = RequestConfig.custom()
                                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                                .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                                .build();
    }

    public static String doGetStr(String url, Map<String, String> params) throws IOException, URISyntaxException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpUriRequest request = createRequest(params, url, HttpGet.METHOD_NAME);

        return getResponseContent(httpClient.execute(request));
    }

    public static String uploadFileByPost(String url, String filePath, String fileParamKey, Map<String, String> params,
                                          int connectTimout) throws IOException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault();){
            File file = new File(filePath);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                                            .setCharset(StandardCharsets.UTF_8)
                                            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                                            .addBinaryBody(fileParamKey, new FileInputStream(filePath),
                                                    ContentType.MULTIPART_FORM_DATA, file.getName());
            params.forEach(builder::addTextBody);

            HttpPost post = new HttpPost(url);
            post.setEntity(builder.build());
            RequestConfig requestConfig = RequestConfig.custom()
                                         .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                                         .setConnectTimeout(connectTimout)
                                         .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                                         .build();
            post.setConfig(requestConfig);

            return getResponseContent(httpClient.execute(post));
        }
    }

    private static HttpUriRequest createRequest(Map<String, String> paramsMap, String url, String method) throws URISyntaxException {
        List<NameValuePair> params = paramsMap.entrySet()
                                    .stream()
                                    .map(e -> new BasicNameValuePair(e.getKey(), e.getValue()))
                                    .collect(Collectors.toList());
        switch (method){
            case HttpPost.METHOD_NAME:
                HttpPost post = new HttpPost();
                post.setConfig(defaultRequestConfig);
                post.setURI(new URI(url));
                post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

                return  post;
            case HttpGet.METHOD_NAME:
            default:
                URIBuilder uriBuilder = new URIBuilder(url);
                uriBuilder.setParameters(params);
                uriBuilder.setCharset(StandardCharsets.UTF_8);
                HttpGet get = new HttpGet(uriBuilder.build());
                get.setConfig(defaultRequestConfig);
                return get;
        }
    }

    private static String getResponseContent(HttpResponse httpResponse) throws IOException {
        HttpEntity responseEntity =  httpResponse.getEntity();
        if (responseEntity != null) {
            return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8); // 将响应内容转换为字符串
        }

        throw new RuntimeException("请求的内容为空，请检查");
    }

    public static InputStream doGetInputStream(String url, Map<String, String> params) throws IOException, URISyntaxException {
        return doGet(url, params).getEntity().getContent();
    }

    public static HttpResponse doPost(String url, Map<String, String> params) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequest request = createRequest(params, url, HttpPost.METHOD_NAME);
        return client.execute(request); // 执行请求并获取内容
    }

    public static HttpResponse doGet(String url, Map<String, String> params) throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequest request = createRequest(params, url, HttpGet.METHOD_NAME);
        return client.execute(request); // 执行请求并获取内容
    }
}
