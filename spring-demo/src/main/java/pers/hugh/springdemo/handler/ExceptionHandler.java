package pers.hugh.springdemo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/12/1</pre>
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        logger.error("服务器异常:", e);

        ModelAndView mv = new ModelAndView();
        response.setStatus(HttpStatus.OK.value());
        //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //设置ContentType
        response.setCharacterEncoding("UTF-8");
        //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            //TODO 处理异常并返回
            response.getWriter().write("");
        } catch (IOException ex) {
            logger.error("与客户端通讯异常:", ex);
        }
        return mv;
    }
}
