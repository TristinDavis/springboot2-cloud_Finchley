package com.aha.tech.templateserver.facade.model.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: luweihong
 * @Date: 2018-11-22
 *
 * 用户请求dto对象
 */
public class UserRequestDto implements Serializable {

    @ApiModelProperty(name = "name",required = true)
    private String name;

    @ApiModelProperty(name = "remote_ip",required = false)
    private String remoteIp;

    public UserRequestDto(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "name='" + name + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                '}';
    }
}
