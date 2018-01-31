package pers.hugh.springdemo.config.properties;

import org.springframework.core.env.Environment;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/31</pre>
 */
public abstract class AbstractProperties {

    public AbstractProperties() {
    }

    protected abstract Environment getEnvironment();

    public String getString(String key) {
        if (getEnvironment() == null){
            return null;
        }
        return getEnvironment().getProperty(key);
    }

    public String getString(String key, String defaultVal) {
        String strVal = getString(key);
        return strVal == null ? defaultVal : strVal;
    }

    public int getInteger(String key, int defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        int formatValue = defaultValue;
        try {
            formatValue = Integer.parseInt(value);
        } catch (Exception e) {
//            ClogUtil.error(String.format("%s=%s config error.",key, value), e);
        }
        return formatValue;
    }

    public long getLong(String key, long defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        long formatValue = defaultValue;
        try {
            formatValue = Long.parseLong(value);
        } catch (Exception e) {
//            ClogUtil.error(String.format("%s=%s config error.",key, value), e);
        }
        return formatValue;
    }

    public double getDouble(String key, double defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        double formatValue = defaultValue;
        try {
            formatValue = Double.parseDouble(value);
        } catch (Exception e) {
//            ClogUtil.error(String.format("%s=%s config error.",key, value), e);
        }
        return formatValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }
        boolean formatValue = defaultValue;
        try {
            formatValue = Boolean.parseBoolean(value);
        } catch (Exception e) {
//            ClogUtil.error(String.format("%s=%s config error.",key, value), e);
        }
        return formatValue;
    }
}
