package pers.hugh.springdemo.config.properties;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/31</pre>
 */
public abstract class AbstractJdbcProperties extends AbstractProperties {

    protected abstract String getPrefix();

    public String getUrl() {
        return getString(getPrefix() + ".url");
    }

    public String getDriverClassName() {
        return getString(getPrefix() + ".driver-class-name");
    }

    public String getUsername() {
        return getString(getPrefix() + ".username");
    }

    public String getPassword() {
        return getString(getPrefix() + ".password");
    }

    public int getInitialSize() {
        return getInteger(getPrefix() + ".initialSize", 5);
    }

    public int getMinIdle() {
        return getInteger(getPrefix() + ".minIdle", 5);
    }

    public int getMaxActive() {
        return getInteger(getPrefix() + ".maxActive", 20);
    }

    public int getMaxWait() {
        return getInteger(getPrefix() + ".maxWait", 60000);
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return getLong(getPrefix() + ".timeBetweenEvictionRunsMillis", 60000);
    }

    public long getMinEvictableIdleTimeMillis() {
        return getLong(getPrefix() + ".minEvictableIdleTimeMillis", 30000);
    }
}
