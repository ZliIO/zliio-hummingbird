package com.zliio.hummingbird.web.common.lang;

import io.netty.util.AsciiString;

/**
 * HttpMediaType
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class HttpMediaType {

    public static final String CONTENT_TYPE_VALUE = "Content-Type";

    public static final String FAVICON_ICO = "/favicon.ico";
    public static final AsciiString CONNECTION = AsciiString.cached("Connection");
    public static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    public static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    public static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");



    public static final String ALL_VALUE = "*/*";

    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    public static final String APPLICATION_JSON_VALUE = "application/json";

    public static final String APPLICATION_PROTOBUF_BASE64_UTF8_VALUE = "application/x-protobuf-base64;charset=UTF-8";
    public static final String APPLICATION_PROTOBUF_BASE64_VALUE = "application/x-protobuf-base64";

    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";

}
