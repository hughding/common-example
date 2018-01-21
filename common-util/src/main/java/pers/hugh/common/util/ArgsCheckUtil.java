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
            throw new IllegalArgumentException(name + " can not be null");
        }
        return argument;
    }

    public static <T extends CharSequence> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " can not be null");
        }
        if (TextUtils.isEmpty(argument)) {
            throw new IllegalArgumentException(name + " can not be empty");
        }
        return argument;
    }

    public static <T extends CharSequence> T notBlank(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " can not be null");
        }
        if (TextUtils.isBlank(argument)) {
            throw new IllegalArgumentException(name + " can not be empty");
        }
        return argument;
    }

    public static <E, T extends Collection<E>> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " can not be null");
        }
        if (argument.isEmpty()) {
            throw new IllegalArgumentException(name + " can not be empty");
        }
        return argument;
    }

    public static <T extends CharSequence> T notTooLong(final T argument, final int maxLength, final String name) {
        notBlank(argument, name);
        positive(maxLength, "inner param maxLength");

        if (argument.length() > maxLength) {
            throw new IllegalArgumentException(name + " must not be grater than " + maxLength);
        }
        return argument;
    }

    public static <T extends CharSequence> T notTooShort(final T argument, final int minLength, final String name) {
        notBlank(argument, name);
        positive(minLength, "inner param minLength");

        if (argument.length() < minLength) {
            throw new IllegalArgumentException(name + " must not be less than " + minLength);
        }
        return argument;
    }

    public static int positive(final int n, final String name) {
        if (n <= 0) {
            throw new IllegalArgumentException(name + " can not be negative or 0");
        }
        return n;
    }

    public static long positive(final long n, final String name) {
        if (n <= 0) {
            throw new IllegalArgumentException(name + " can not be negative or 0");
        }
        return n;
    }

    public static int notNegative(final int n, final String name) {
        if (n < 0) {
            throw new IllegalArgumentException(name + " can not be negative");
        }
        return n;
    }

    public static long notNegative(final long n, final String name) {
        if (n < 0) {
            throw new IllegalArgumentException(name + " can not be negative");
        }
        return n;
    }
}
