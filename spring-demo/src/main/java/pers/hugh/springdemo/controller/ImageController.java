package pers.hugh.springdemo.controller;

import org.apache.commons.io.IOUtils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/19</pre>
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @ResponseBody
    @GetMapping(path = "/case1", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] case1() throws IOException {
        URL url = new URL("http://commons.apache.org/images/commons-logo.png");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream in = conn.getInputStream();
        return IOUtils.toByteArray(in);
    }
}
