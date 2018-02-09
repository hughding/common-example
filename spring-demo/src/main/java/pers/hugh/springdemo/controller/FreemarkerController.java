package pers.hugh.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/2/9</pre>
 */
@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

    @GetMapping("/sample")
    public String sample(Model model) {
        List<String> sampleVOList = new ArrayList<>();
        sampleVOList.add("string1");
        sampleVOList.add("string2");
        model.addAttribute("sampleList", sampleVOList);
        model.addAttribute("nowDate", LocalDateTime.now());
        model.addAttribute("testNull", null);
        return "sample";
    }
}
