package com.ximalaya.per.yan.lock.constant;

import com.ximalaya.per.yan.lock.enums.PunctuationEnum;

import java.text.MessageFormat;

/**
 * @author yan.gao
 * @date 2019-08-29 09:12
 */
public class RedisConstant {

    public static final String APP_PREFIX = "APP";

    public static final String LOCK_PREFIX = "LOCK";

    public static final String LIMITER_PREFIX = "LIMITER";

    public static final String STRING_PREFIX = "STRING";

    public static final String HASH_PREFIX = "HASH";

    public static final String LIST_PREFIX = "LIST";

    public static final String SET_PREFIX = "SET";

    public static final String ZSET_PREFIX = "ZSET";

    private static final String SUFFIX = "{0}";

    private static final String APP_PREFIX_PATTERN = APP_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String LOCK_PREFIX_PATTERN = LOCK_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String LIMITER_PREFIX_PATTERN = LIMITER_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String STRING_PREFIX_PATTERN = STRING_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String HASH_PREFIX_PATTERN = HASH_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String LIST_PREFIX_PATTERN = LIST_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String SET_PREFIX_PATTERN = SET_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;
    private static final String ZSET_PREFIX_PATTERN = ZSET_PREFIX + PunctuationEnum.COLON.getValue() + SUFFIX;

    public static String appendLockPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(LOCK_PREFIX_PATTERN, key));
    }

    public static String appendLimiterPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(LIMITER_PREFIX_PATTERN, key));
    }

    public static String appendStringPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(STRING_PREFIX_PATTERN, key));
    }

    public static String appendHashPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(HASH_PREFIX_PATTERN, key));
    }

    public static String appendListPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(LIST_PREFIX_PATTERN, key));
    }

    public static String appendSetPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(SET_PREFIX_PATTERN, key));
    }

    public static String appendZSETPrefix(String key) {
        return appendAppPrefix(MessageFormat.format(ZSET_PREFIX_PATTERN, key));
    }

    public static String appendAppPrefix(String key) {
        return MessageFormat.format(APP_PREFIX_PATTERN, key);
    }
}
