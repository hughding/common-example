package pers.hugh.common.util;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/24</pre>
 */
public class AspxUtil {

    private static final String VIEWSTATE_FLAG = "id=\"__VIEWSTATE\" value=\"";
    private static final String VIEWSTATE_GENERATOR_FLAG = "id=\"__VIEWSTATEGENERATOR\" value=\"";
    private static final String EVENTVALIDATION_FLAG = "id=\"__EVENTVALIDATION\" value=\"";
    private static final String END_FLAG = "\"";

    /**
     * 获取Aspx Form表单的"__VIEWSTATE"
     *
     * @param pageStr 页面字符串
     * @return
     */
    public static String getViewState(String pageStr) {
        int len1 = pageStr.indexOf(VIEWSTATE_FLAG) + VIEWSTATE_FLAG.length();
        int len2 = pageStr.indexOf(END_FLAG, len1);
        return pageStr.substring(len1, len2);
    }

    /**
     * 获取Aspx Form表单的"__VIEWSTATEGENERATOR"
     *
     * @param pageStr 页面字符串
     * @return
     */
    public static String getViewStateGenerator(String pageStr) {
        int len1 = pageStr.indexOf(VIEWSTATE_GENERATOR_FLAG) + VIEWSTATE_GENERATOR_FLAG.length();
        int len2 = pageStr.indexOf(END_FLAG, len1);
        return pageStr.substring(len1, len2);
    }

    /**
     * 获取Aspx Form表单的"__EVENTVALIDATION"
     *
     * @param pageStr 页面字符串
     * @return
     */
    public static String getEventValidation(String pageStr) {
        int len1 = pageStr.indexOf(EVENTVALIDATION_FLAG) + EVENTVALIDATION_FLAG.length();
        int len2 = pageStr.indexOf(END_FLAG, len1);
        return pageStr.substring(len1, len2);
    }
}
