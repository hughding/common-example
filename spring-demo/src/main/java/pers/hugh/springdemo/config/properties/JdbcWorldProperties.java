package pers.hugh.springdemo.config.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/31</pre>
 */
@Component
@ConfigurationProperties(prefix = "jdbc.world")
@PropertySource(value = {"classpath:jdbc-world.properties"})
public class JdbcWorldProperties extends AbstractJdbcProperties {

    @Autowired
    private Environment environment;

    @Override
    protected String getPrefix() {
        return "jdbc.world";
    }

    @Override
    protected Environment getEnvironment() {
        return environment;
    }
}
