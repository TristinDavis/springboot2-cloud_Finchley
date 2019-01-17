package com.aha.tech.model.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: luweihong
 * @Date: 2018/7/27
 */
@Table(name = "t_read")
public class ReadEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
