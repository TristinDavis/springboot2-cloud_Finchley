package com.aha.tech.model.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: luweihong
 * @Date: 2018/9/10
 */
public class DateHelpEntity {

    public Long year;

    public Long month;

    public Long week;

    public Long day;

    public Long hour;

    public Long minute;

    public Long second;

    public Long mills;

    public Long nano;

    public DateHelpEntity() {
        super();
    }

    public DateHelpEntity(Long hour, Long minute, Long second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public DateHelpEntity(Long year, Long month, Long week, Long day) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
    }

    public DateHelpEntity(Long year, Long month, Long week, Long day, Long hour, Long minute, Long second, Long mills, Long nano) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.mills = mills;
        this.nano = nano;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public Long getMinute() {
        return minute;
    }

    public void setMinute(Long minute) {
        this.minute = minute;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public Long getMills() {
        return mills;
    }

    public void setMills(Long mills) {
        this.mills = mills;
    }

    public Long getNano() {
        return nano;
    }

    public void setNano(Long nano) {
        this.nano = nano;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
