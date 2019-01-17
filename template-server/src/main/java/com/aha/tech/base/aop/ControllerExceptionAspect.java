package com.aha.tech.base.aop;

import com.aha.tech.base.commons.constants.ResponseConstants;
import com.aha.tech.base.commons.response.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 接收controller层未捕获的异常
 */
@ControllerAdvice
public class ControllerExceptionAspect {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionAspect.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RpcResponse handle(Exception e) {
        LOG.error(e.getMessage(), e);
        return new RpcResponse(ResponseConstants.ERROR_CODE_4200, e.getMessage());
    }

}
