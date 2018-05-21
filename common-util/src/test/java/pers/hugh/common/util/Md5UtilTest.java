package pers.hugh.common.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static pers.hugh.common.util.Md5Util.*;

/**
 * @author xzding
 * @date 2018/5/21
 */
public class Md5UtilTest {

    @Test
    public void test(){
        System.out.println(getMd5("abc"));
    }

}