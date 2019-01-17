package com.aha.tech.base.commons.response;

import com.aha.tech.base.commons.constants.ResponseConstants;
import com.aha.tech.base.commons.exception.BaseException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.aha.tech.base.commons.constants.ResponseConstants.*;

/**
 * 说明:响应对象
 *
 */
public class RpcResponse<T> {

    private Integer code = SUCCESS;// 0正常
    private String message = SUCCESS_MSG;

    private T data;

    public RpcResponse() {
        this(SUCCESS, SUCCESS_MSG);
    }

    public RpcResponse(Integer code, String message) {
        this(code, message, null);
    }

    public RpcResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RpcResponse(T t) {
        this();
        this.data = t;
    }

    /**
     * 默认hystrix fallback 返回实体
     * @return
     */
    public static RpcResponse defaultHystrixFallbackResponse(){
        return new RpcResponse( ResponseConstants.FAILURE,ResponseConstants.HYSTRIX_FALLBACK_MSG);
    }

    /**
     * code : 1
     * msg : operation failure
     * @return
     */
    public static RpcResponse defaultFailureResponse(){
        return new RpcResponse(ResponseConstants.FAILURE,ResponseConstants.FAILURE_MSG);
    }

    public RpcResponse(BaseException e) {
        this(ERROR_CODE_4200, e.getMessage());
    }

    public RpcResponse(Exception e) {
        this(ERROR_CODE_4001, e.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
