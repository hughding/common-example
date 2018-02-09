package pers.hugh.springdemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/19</pre>
 */
@SpringBootApplication(scanBasePackages = {"pers.hugh.springdemo"})
public class SpringDemoInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringDemoInitializer.class);
    }

}
