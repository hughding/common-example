package pers.hugh.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/9</pre>
 */
public class HttpUtil {

    private static CookieStore cookieStore = new BasicCookieStore();

    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String ACCEPT = "application/json";
    private static final String CONNECTION = "keep-alive";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0";

    private static void addHeader(HttpRequestBase request){
        request.addHeader("Content-type", CONTENT_TYPE);
        request.setHeader("Accept", ACCEPT);
        request.setHeader("Connection", CONNECTION);
        request.setHeader("User-Agent", USER_AGENT);
    }

    public static String doGet(String url, String proxyAddr, int proxyPort) {
        String result = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
            HttpGet httpGet = new HttpGet(url);
            addHeader(httpGet);
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(cookieStore);
            if (StringUtils.isNotBlank(proxyAddr)) {
                HttpHost proxy = new HttpHost(proxyAddr, proxyPort);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                httpGet.setConfig(config);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpGet,httpClientContext)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost(String url, String parameters, String proxyAddr, int proxyPort){
        String result = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            addHeader(httpPost);
            httpPost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(cookieStore);
            if (StringUtils.isNotBlank(proxyAddr)) {
                HttpHost proxy = new HttpHost(proxyAddr, proxyPort);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                httpPost.setConfig(config);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpPost,httpClientContext)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost(String url, List<BasicNameValuePair> parameters, String proxyAddr, int proxyPort){
        String result = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            addHeader(httpPost);
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8")));
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(cookieStore);
            if (StringUtils.isNotBlank(proxyAddr)) {
                HttpHost proxy = new HttpHost(proxyAddr, 8080);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                httpPost.setConfig(config);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpPost,httpClientContext)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static CookieStore getCookieStore() {
        return cookieStore;
    }

}
