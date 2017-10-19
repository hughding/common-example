package pers.hugh.springdemo;

import org.springframework.boot.SpringApplication;

import java.awt.*;
import java.net.URI;

/**
 * Created by xzding on 2017/10/19.
 */
public class SpringDemoStater {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(SpringDemoInitializer.class);

        Desktop.getDesktop().browse(new URI("http://127.0.0.1:8080"));
    }
}
