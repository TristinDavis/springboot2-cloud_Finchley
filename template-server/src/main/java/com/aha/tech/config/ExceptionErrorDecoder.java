//package com.aha.tech.config;
//
//import com.aha.tech.commons.response.RpcResponse;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.hystrix.exception.HystrixBadRequestException;
//import feign.Response;
//import feign.Util;
//import feign.codec.ErrorDecoder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//
//import javax.ws.rs.WebApplicationException;
//import java.io.IOException;
//
///**
// * @Author: luweihong
// * @Date: 2019/1/2
// */
//@Configuration
//public class ExceptionErrorDecoder implements ErrorDecoder {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private static Logger logger = LoggerFactory.getLogger(ExceptionErrorDecoder.class);
//
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        RpcResponse rpcResponse;
//        Exception exception = null;
//        try {
//            rpcResponse = objectMapper.readValue(Util.toString(response.body().asReader()), RpcResponse.class);
//            if (rpcResponse != null && rpcResponse.getCode() == 4200) {
//                exception = new HystrixBadRequestException("HystrixBadRequestException ===> request 4200", exception);
//            } else {
//                //为了说明我使用的 WebApplicationException 基类，去掉了封装
//                exception = new WebApplicationException(javax.ws.rs.core.Response.status(response.status()).entity(rpcResponse).type(String.valueOf(MediaType.APPLICATION_JSON)).build());
//            }
//
//        } catch (IOException ex) {
//            logger.error(ex.getMessage(), ex);
//        }
//        // 这里只封装4开头的请求异常
////        if (400 < response.status() && response.status() < 500) {
////            exception = new HystrixBadRequestException("HystrixBadRequestException ===> request exception wrapper", exception);
////        } else {
////            logger.error(exception.getMessage(), exception);
////        }
//        return exception;
//    }
//}
