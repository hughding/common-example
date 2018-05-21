package pers.hugh.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/11/9</pre>
 */
public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static String getCookieValue(String key, HttpServletRequest request) {
        String value = null;
        if (StringUtils.isBlank(key)) {
            return value;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return value;
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), key)) {
                value = cookie.getValue();
            }
        }
        return value;
    }

    public static void setCookieValue(String key, String value, int maxAgeSeconds, HttpServletResponse response) {
        String encodeValue = encode(value, "UTF-8");
        String cookieValue = (encodeValue == null) ? value : encodeValue;
        Cookie cookie = new Cookie(key, cookieValue);
        cookie.setMaxAge(maxAgeSeconds);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String encode(String str, String charset) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return URLEncoder.encode(str, charset);
        } catch (Exception e) {
            logger.error("encode error", e);
        }
        return null;
    }
}
