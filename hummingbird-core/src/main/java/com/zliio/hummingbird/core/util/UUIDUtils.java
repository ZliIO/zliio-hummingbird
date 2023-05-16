package com.zliio.hummingbird.core.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 */
public class UUIDUtils {

    /**
     * 生成uuid，并去除横线
     * @return uuid
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 生成uuid
     * @return uuid
     */
    public static String generateDefaultUUID() {
        return UUID.randomUUID().toString();
    }
}
