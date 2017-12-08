package pers.hugh.common.util;

import org.apache.commons.codec.Charsets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/12/5</pre>
 */
public class CookieStoreUtil {

    public static String getCookieValue(String key, String domain, CookieStore cookieStore) {
        String value = null;
        if (StringUtils.isBlank(key)) {
            return value;
        }
        List<Cookie> cookies = cookieStore.getCookies();
        if (CollectionUtils.isEmpty(cookies)) {
            return value;
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), key) && StringUtils.equals(cookie.getDomain(),domain)) {
                value = cookie.getValue();
            }
        }
        return value;
    }

    public static void setCookieValue(String key, String value, int maxAgeSeconds, String domain, String path, CookieStore cookieStore) {
        String encodeValue = encode(value, Charsets.UTF_8.name());
        String cookieValue = (encodeValue == null) ? value : encodeValue;
        BasicClientCookie cookie = new BasicClientCookie(key, cookieValue);
        cookie.setExpiryDate(new Date(System.currentTimeMillis() + maxAgeSeconds * 1000));
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookieStore.addCookie(cookie);
    }

    public static String encode(String str, String charset) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException e) {
        } catch (IllegalArgumentException e) {
        }
        return null;
    }
}
