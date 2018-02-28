package pers.hugh.springdemo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/2/28</pre>
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DatabaseTypeEnum> contextHolder = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }

    public static void read(){
        contextHolder.set(DatabaseTypeEnum.READ);
    }

    public static void write(){
        contextHolder.set(DatabaseTypeEnum.WRITE);
    }

    public enum DatabaseTypeEnum {
        READ, WRITE
    }
}
