package pers.hugh.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/19</pre>
 */
public class PinyinUtil {

    public static String getPinyin(String str) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            String[] pinyin = null;
            try {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(word, outputFormat);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                //异常处理，一般记录日志
            }
            if (pinyin != null) {
                sb.append(pinyin[0]);
            } else {
                sb.append(word);
            }
        }
        return sb.toString();
    }

    public static String getPinyinHeadChar(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyin != null) {
                sb.append(pinyin[0].charAt(0));
            } else {
                sb.append(word);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getPinyin("汉语与汉字,abc,123"));
        System.out.println(getPinyinHeadChar("汉语与汉字,abc,123"));
    }

}
