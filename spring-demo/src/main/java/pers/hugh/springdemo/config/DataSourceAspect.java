package pers.hugh.springdemo.config;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/2/28</pre>
 */
@Aspect
@Component
public class DataSourceAspect {

    private static final String[] READ_PREFIX = {"select", "query", "get", "count"};

    private Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    @Before("execution(* pers.hugh.springdemo.dal.sys.dao..*.*(..))")
    public void chooseDataSource(JoinPoint jp) {
        String method = jp.getSignature().getName();
        boolean isReadMethod = Arrays.stream(READ_PREFIX)
                .filter(readPrefix -> StringUtils.startsWith(method, readPrefix)).count() > 0;
        if (isReadMethod) {
            logger.info("dataSource:switch to read");
            DynamicDataSource.read();
        } else {
            logger.info("dataSource:switch to write");
            DynamicDataSource.write();
        }
    }

}
