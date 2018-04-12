package pers.hugh.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/4/12</pre>
 */
public class PropertiesUtilTest {

    private static final String PROPERTY_FILE_NAME = "test.properties";

    @Test
    public void readProperty() {
        assertEquals("value1", PropertiesUtil.readProperty(PROPERTY_FILE_NAME, "key1"));
    }

    @Test
    public void getProperties() {
        assertNotNull(PropertiesUtil.getProperties(PROPERTY_FILE_NAME));
    }

    @Test
    public void writeProperty() {
        //只会在target目录下的文件中添加
        PropertiesUtil.writeProperty(PROPERTY_FILE_NAME, "key2", "value2");
        assertEquals("value2", PropertiesUtil.readProperty(PROPERTY_FILE_NAME, "key2"));
    }
}