package com.zliio.hummingbird.web.serializer;

import com.zliio.hummingbird.core.logging.Logger;
import com.zliio.hummingbird.core.logging.LoggerFactory;

/**
 * Bean 序列化器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public interface Serializer {

    final Logger logger = LoggerFactory.getLogger(Serializer.class);

    /**
     * 将二进制转成对象
     *
     * @param bytes 需要转换的二进制
     * @param clazz 转换成的对象的类
     * @param <T> 转换成的对象的类的类型
     * @return 转换成功后的结果
     */
    <T> T serializeToObject(byte[] bytes,Class<T> clazz);

    /**
     * 将对象转换成二进制数组
     *
     * @param object 需要转换的数据对象
     * @return 转换后的处理结果
     */
    byte[] serializeToByteArray(Object object);
}
