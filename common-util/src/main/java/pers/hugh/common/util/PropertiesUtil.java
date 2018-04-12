package pers.hugh.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/3/30</pre>
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 按照fileName和key读取配置
     *
     * @param fileName
     * @param key
     * @return
     */
    public static String readProperty(String fileName, String key) {
        Properties properties = getProperties(fileName);
        if (properties == null) {
            throw new RuntimeException(fileName + "不存在");
        }
        return getProperties(fileName).getProperty(key);
    }

    /**
     * 获取整个配置信息
     *
     * @return
     */
    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try (InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            logger.error("PropertiesUtil getProperties error", e);
            return null;
        }
    }

    /**
     * key-value写入配置文件
     *
     * @param fileName
     * @param key
     * @param value
     */
    public static void writeProperty(String fileName, String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            p.load(is);
            os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(fileName).getFile());
            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            os.close();
        } catch (Exception e) {
            logger.error("PropertiesUtil writeProperty error", e);
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error("PropertiesUtil writeProperty error", e);
            }
        }
    }

}