package com.aha.tech.templateserver.facade.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: luweihong
 * @Date: 2018-11-22
 */
public class UserVo implements Serializable {

    @ApiModelProperty(name = "name",required = true)
    private String name;

    @ApiModelProperty(name = "remote_ip",required = false)
    private Integer age;

    private String remoteIp;

    public UserVo(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", remoteIp='" + remoteIp + '\'' +
                '}';
    }
}
