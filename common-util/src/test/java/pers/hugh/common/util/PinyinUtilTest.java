package pers.hugh.common.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static pers.hugh.common.util.PinyinUtil.*;

/**
 * @author xzding
 * @date 2018/5/21
 */
public class PinyinUtilTest {

    @Test
    public void test(){
        System.out.println(getPinyin("汉语与汉字,abc,123"));
        System.out.println(getPinyinHeadChar("汉语与汉字,abc,123"));
    }
}