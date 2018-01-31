package pers.hugh.common.util;

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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/9</pre>
 */
public class HttpUtil {

    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
    private static final String CONNECTION = "keep-alive";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";

    private static void addHeader(HttpRequestBase request) {
        request.setHeader("Accept", ACCEPT);
        request.setHeader("Connection", CONNECTION);
        request.setHeader("User-Agent", USER_AGENT);
    }

    /**
     * HTTP GET方法
     *
     * @param url
     * @param proxy       若不使用代理，传null
     * @param cookieStore 若不使用CookieStore，传null
     * @return
     */
    public static String doGet(String url, HttpProxy proxy, CookieStore cookieStore) {
        String result = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
            HttpGet httpGet = new HttpGet(url);
            addHeader(httpGet);
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(cookieStore);
            if (proxy != null) {
                HttpHost httpHost = new HttpHost(proxy.getAddress(), proxy.getPort());
                RequestConfig config = RequestConfig.custom().setProxy(httpHost).build();
                httpGet.setConfig(config);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpGet, httpClientContext)) {
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

    /**
     * HTTP POST方法
     *
     * @param url
     * @param jsonStr     请求JSON字符串
     * @param proxy       若不使用代理，传null
     * @param cookieStore 若不使用CookieStore，传null
     * @return
     */
    public static String doPost(String url, String jsonStr, HttpProxy proxy, CookieStore cookieStore) {
        String result = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            addHeader(httpPost);
            httpPost.setEntity(new StringEntity(jsonStr, StandardCharsets.UTF_8));
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(cookieStore);
            if (proxy != null) {
                HttpHost httpHost = new HttpHost(proxy.getAddress(), proxy.getPort());
                RequestConfig config = RequestConfig.custom().setProxy(httpHost).build();
                httpPost.setConfig(config);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpPost, httpClientContext)) {
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

    /**
     * HTTP POST方法
     *
     * @param url
     * @param parameters  FORM表单参数
     * @param proxy       若不使用代理，传null
     * @param cookieStore 若不使用CookieStore，传null
     * @return
     */
    public static String doPost(String url, List<BasicNameValuePair> parameters, HttpProxy proxy, CookieStore cookieStore) {
        String result = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            addHeader(httpPost);
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));
            HttpClientContext httpClientContext = HttpClientContext.create();
            httpClientContext.setCookieStore(cookieStore);
            if (proxy != null) {
                HttpHost httpHost = new HttpHost(proxy.getAddress(), proxy.getPort());
                RequestConfig config = RequestConfig.custom().setProxy(httpHost).build();
                httpPost.setConfig(config);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpPost, httpClientContext)) {
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

    static class HttpProxy {
        private String address;
        private int port;

        public HttpProxy() {
        }

        public HttpProxy(String address, int port) {
            this.address = address;
            this.port = port;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

}
