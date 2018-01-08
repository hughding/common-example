package pers.hugh.common.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/8</pre>
 */
@Component
public class FreemarkerTpl {

    @Resource
    private Configuration freeMarkerConfiguration;

    public String getTplContent(Object model, String modelName, String tplName)
            throws IOException, TemplateException {
        try {
            Map<String, Object> root = new HashMap<>();
            root.put(modelName, model);
            return getTplContent(root, tplName);
        } catch (IOException ioEx) {
            throw ioEx;
        } catch (TemplateException tplEx) {
            throw tplEx;
        }
    }

    public String getTplContent(Object model, String modelName, String basePackagePath, String tplName)
            throws IOException, TemplateException {
        try {
            Map<String, Object> root = new HashMap<>();
            root.put(modelName, model);

            return getTplContent(root, basePackagePath, tplName);
        } catch (IOException ioEx) {
            throw ioEx;
        } catch (TemplateException tplEx) {
            throw tplEx;
        }
    }

    /**
     * 获取模板数据，模板的默认路径为resources/templates/ 目录下.
     *
     * @param root
     * @param tplName
     * @return
     * @throws IOException
     * @throws TemplateException
     * @throws Exception
     */
    public String getTplContent(Map<String, Object> root, String tplName)
            throws IOException, TemplateException {
        try {
            return getTplContent(root, "/templates/", tplName);
        } catch (IOException ioEx) {
            throw ioEx;
        } catch (TemplateException tplEx) {
            throw tplEx;
        }
    }

    public String getTplContent(Map<String, Object> root, String basePackagePath, String tplName)
            throws IOException, TemplateException {
        try {
            freeMarkerConfiguration.setDefaultEncoding("utf-8");
            freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), basePackagePath);
            freeMarkerConfiguration.setLogTemplateExceptions(false);

            Template template = freeMarkerConfiguration.getTemplate(tplName);
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
            return content;
        } catch (IOException ioEx) {
            throw ioEx;
        } catch (TemplateException tplEx) {
            throw tplEx;
        }
    }
}
