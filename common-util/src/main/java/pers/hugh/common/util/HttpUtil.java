package pers.hugh.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by xzding on 2017/8/16.
 */
public class HttpUtil {
    private static RequestConfig config = null;

    static {
        HttpHost proxy = new HttpHost("yourproxy", 8080, "http");
        config = RequestConfig.custom().setProxy(proxy).build();
    }

    public static String doGet(String url, boolean useProxy) throws Exception {
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Connection", "keep-alive");
            if (useProxy) {
                httpGet.setConfig(config);
            }
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;

    }

    public static String doPost(String url, String parameters, boolean useProxy) throws Exception {
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("User-Agent", "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36");
            httpPost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
            if (useProxy) {
                httpPost.setConfig(config);
            }
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    public static String doPost(String url, List<BasicNameValuePair> parameters, boolean useProxy) throws Exception {
        String result = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("User-Agent", "User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36");
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8")));
            if (useProxy) {
                httpPost.setConfig(config);
            }
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

}
