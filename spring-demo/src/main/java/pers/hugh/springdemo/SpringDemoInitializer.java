package pers.hugh.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.awt.*;
import java.net.URI;

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

    public static void main(String[] args) throws Exception {
        //Headless模式是系统的一种配置模式。在该模式下，系统缺少了显示设备、键盘或鼠标
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(SpringDemoInitializer.class, args);

        Desktop.getDesktop().browse(new URI("http://127.0.0.1:8080"));
    }

}
