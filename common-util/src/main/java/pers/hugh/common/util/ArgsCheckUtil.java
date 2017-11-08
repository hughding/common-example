package pers.hugh.common.util;

import org.apache.http.util.TextUtils;

import java.util.Collection;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/11/8</pre>
 */
public class ArgsCheckUtil {

    public static void check(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void check(final boolean expression, final String message, final Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    public static <T> T notNull(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + "不能为空");
        }
        return argument;
    }

    public static <T extends CharSequence> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + "不能为空");
        }
        if (TextUtils.isEmpty(argument)) {
            throw new IllegalArgumentException(name + "不能为空字符串");
        }
        return argument;
    }

    public static <T extends CharSequence> T notBlank(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + "不能为空");
        }
        if (TextUtils.isBlank(argument)) {
            throw new IllegalArgumentException(name + "不能为空字符串");
        }
        return argument;
    }

    public static <E, T extends Collection<E>> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + "不能为空");
        }
        if (argument.isEmpty()) {
            throw new IllegalArgumentException(name + "不能为空集合");
        }
        return argument;
    }

    public static <T extends CharSequence> T notTooLong(final T argument, final int maxLength, final String name) {
        notBlank(argument, name);
        positive(maxLength, "系统内部参数maxLength");

        if (argument.length() > maxLength) {
            throw new IllegalArgumentException(name + "不能超过最大长度" + maxLength);
        }
        return argument;
    }

    public static <T extends CharSequence> T notTooShort(final T argument, final int minLength, final String name) {
        notBlank(argument, name);
        positive(minLength, "系统内部参数minLength");

        if (argument.length() < minLength) {
            throw new IllegalArgumentException(name + "不能小于最小长度" + minLength);
        }
        return argument;
    }

    public static int positive(final int n, final String name) {
        if (n <= 0) {
            throw new IllegalArgumentException(name + "不能小于等于零");
        }
        return n;
    }

    public static long positive(final long n, final String name) {
        if (n <= 0) {
            throw new IllegalArgumentException(name + "不能小于等于零");
        }
        return n;
    }

    public static int notNegative(final int n, final String name) {
        if (n < 0) {
            throw new IllegalArgumentException(name + "不能小于零");
        }
        return n;
    }

    public static long notNegative(final long n, final String name) {
        if (n < 0) {
            throw new IllegalArgumentException(name + "不能小于零");
        }
        return n;
    }
}
