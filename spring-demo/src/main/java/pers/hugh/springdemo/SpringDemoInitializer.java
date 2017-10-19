package pers.hugh.springdemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by xzding on 2017/10/18.
 */

@SpringBootApplication(scanBasePackages = {"pers.hugh.springdemo"})
@ServletComponentScan
public class SpringDemoInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringDemoInitializer.class);
    }

}
