package com.aha.tech.templateserver.facade.exception;


import com.aha.tech.commons.constants.ResponseConstants;
import com.aha.tech.commons.exception.BaseException;

/**
 * 说明:业务处理异常warn级别
 * 
 * @author huangkeqi date:2016年12月8日
 */
public class TemplateException extends BaseException {

    private static final long serialVersionUID = -2423279393324395996L;

    public TemplateException(String message) {
        super(message, ResponseConstants.ERROR_CODE_4200);
    }

    public TemplateException(int code, String message) {
        super(message, code);
    }

    public TemplateException(int code, String message, Throwable throwable) {
        super(message, code, throwable);
    }
}
