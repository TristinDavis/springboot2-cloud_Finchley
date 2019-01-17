package com.aha.tech.base.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * @Author: luweihong
 * @Date: 2018/12/3
 * <p>
 * feign request 拦截器
 * 用于动态传递header信息,如果有需要需要自己定义
 */
public class DefaultFeignRequestInterceptor implements RequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(DefaultFeignRequestInterceptor.class);

    private static final String USERNAME = "visitor";

    private static final String PASSWORD = "28ad87ef9fdce5d12dea093b860e8772";

    private static final String BASE64_ENCODE = new String(Base64.encodeBase64(String.format("%s:%s", USERNAME, PASSWORD).getBytes()));

    private static final String AUTHORIZATION_KEY = "Authorization";

    private static final String BASIC_AUTHORIZATION = String.format("Basic %s", BASE64_ENCODE);

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    public static final String CONNECTION = "Connection";

    private static final String HTTP_HEADER_CONNECTION_VALUE = "keep-alive";

    private static final String HTTP_HEADER_X_REQUESTED_WITH_KEY = "X-Requested-With";

    private static final String HTTP_HEADER_X_REQUESTED_WITH_VALUE = "XMLHttpRequest";

    private static final String HTTP_HEADER_KEEP_ALIVE_KEY = "Keep-Alive";

    private static final String HTTP_HEADER_KEEP_ALIVE_VALUE = "timeout=60";

    public static final String CONTENT_ENCODING = "Content-Encoding";

    public static final String CHARSET_ENCODING = Charset.defaultCharset().name();


    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 如果直接发起请求,request会是null
        HttpServletRequest request = attributes.getRequest();
        if (request != null) {
            // utm source 逻辑
        }
//            requestTemplate.header(AUTHORIZATION_KEY, BASIC_AUTHORIZATION);
//            requestTemplate.header(CONTENT_TYPE, APPLICATION_JSON_UTF8);
//            requestTemplate.header(CONNECTION, HTTP_HEADER_CONNECTION_VALUE);
//            requestTemplate.header(CONNECTION, HTTP_HEADER_CONNECTION_VALUE);
//            requestTemplate.header(HTTP_HEADER_KEEP_ALIVE_KEY, HTTP_HEADER_KEEP_ALIVE_VALUE);
//            requestTemplate.header(HTTP_HEADER_X_REQUESTED_WITH_KEY, HTTP_HEADER_X_REQUESTED_WITH_VALUE);
//            requestTemplate.header(CONTENT_ENCODING, CHARSET_ENCODING);
//        } else {
//            if (request.getHeader(AUTHORIZATION_KEY) == null) {
//                requestTemplate.header(AUTHORIZATION_KEY, BASIC_AUTHORIZATION);
//            }
//
//            if (request.getHeader(CONTENT_TYPE) == null) {
//                requestTemplate.header(CONTENT_TYPE, APPLICATION_JSON_UTF8);
//            }
//
//            if (request.getHeader(CONNECTION) == null) {
//                requestTemplate.header(CONNECTION, HTTP_HEADER_CONNECTION_VALUE);
//            }
//
//            if (request.getHeader(HTTP_HEADER_KEEP_ALIVE_KEY) == null) {
//                requestTemplate.header(HTTP_HEADER_KEEP_ALIVE_KEY, HTTP_HEADER_KEEP_ALIVE_VALUE);
//            }
//
//            if (request.getHeader(HTTP_HEADER_X_REQUESTED_WITH_KEY) == null) {
//                requestTemplate.header(HTTP_HEADER_X_REQUESTED_WITH_KEY, HTTP_HEADER_X_REQUESTED_WITH_VALUE);
//            }
//
//            if (request.getHeader(CONTENT_ENCODING) == null) {
//                requestTemplate.header(CONTENT_ENCODING, CHARSET_ENCODING);
//            }
//        }

        requestTemplate.header(AUTHORIZATION_KEY, BASIC_AUTHORIZATION);
        requestTemplate.header(CONTENT_TYPE, APPLICATION_JSON_UTF8);
        requestTemplate.header(CONNECTION, HTTP_HEADER_CONNECTION_VALUE);
        requestTemplate.header(CONNECTION, HTTP_HEADER_CONNECTION_VALUE);
        requestTemplate.header(HTTP_HEADER_KEEP_ALIVE_KEY, HTTP_HEADER_KEEP_ALIVE_VALUE);
        requestTemplate.header(HTTP_HEADER_X_REQUESTED_WITH_KEY, HTTP_HEADER_X_REQUESTED_WITH_VALUE);
        requestTemplate.header(CONTENT_ENCODING, CHARSET_ENCODING);
        logger.debug("default feign request interceptor set http headers : {} ", requestTemplate.headers().toString());
        logger.error("default feign request url: {} ", requestTemplate.url());
        byte[] body = requestTemplate.body();
        if (body != null) {
            logger.error("default feign request body: {} ", new String(body));
        }
    }
}
