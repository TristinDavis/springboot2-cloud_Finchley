package com.aha.tech.templateserver.facade.model.enums;

/**
 * @Author: luweihong
 * @Date: 2018/12/6
 */
public enum TemplateResponseCode {
    SYSTEM_ERROR(4200,"系统异常"),USER_NOT_FOUND(10001,"用户不存在");

    public int code;

    public String desc;

    private TemplateResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
