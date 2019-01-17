package com.aha.tech.base.commons.constants;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 * 返回值常量
 * code,error message
 */
public class ResponseConstants {

    // 后续用于分布式id分配的参数
    public final static Integer PROJECT_ID = 1000;

    public final static int SUCCESS = 0;

    public final static String SUCCESS_MSG = "ok";

    public final static int FAILURE = 1;

    public final static String FAILURE_MSG = "operation failure";

    // hystrix fallback default message
    public final static String HYSTRIX_FALLBACK_MSG = "接口调用异常,执行默认降级策略";

    /** 业务错误 BusineException 默认错误代码 4200 */
    public final static int ERROR_CODE_4200 = 4200;

    /** 无访问权限异常 SecurityException 4000 */
    public final static int ERROR_CODE_4000 = 4000;

    /** 系统错误 SystemException 默认错误代码 4001 */
    public final static int ERROR_CODE_4001 = 4001;
}
