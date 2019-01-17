package com.aha.tech.base.commons.utils;

import cn.hutool.core.bean.copier.CopyOptions;
import com.google.common.collect.Maps;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: luweihong
 * @Date: 2018/7/16 */
public class BeanUtil {

    private static CopyOptions setIgnoreNullValue = CopyOptions.create().setIgnoreNullValue(true);

    /**
     * 说明:bean复制，返回目标对象,模板对象必须存在无参构造函数
     * 
     * @author huangkeqi
     * @param sourceObj 来源对象,如果来源对象是个集合类型，只支持List
     * @param targetClass 目标对象class
     * @param ignoreNullValue true排除null字段不复制
     * @return class T object
     * @date 2018年12月10日
     */
    public static <T> T beanCopy(Object sourceObj, Class<?> targetClass, boolean ignoreNullValue) {
        return ignoreNullValue ? beanCopy(sourceObj, targetClass, setIgnoreNullValue)
                : beanCopy(sourceObj, targetClass, CopyOptions.create().setIgnoreNullValue(false));
    }

    /**
     * 排除null值赋值
     * @param sourceObj
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T beanCopyIgnoreNullValue(Object sourceObj, Class<?> targetClass) {
        return beanCopy(sourceObj, targetClass, Boolean.TRUE);
    }

    public static <T> T beanCopy(Object sourceObj, Class<?> targetClass, CopyOptions options) {
        if (sourceObj == null) return null;
        try {
            if (sourceObj instanceof List) {
                ArrayList<Object> arrayList = new ArrayList();
                ((List<?>) sourceObj).forEach((obj) -> {
                    try {
                        Object instanceCopy = instanceCopy(obj, targetClass, options);
                        arrayList.add(instanceCopy);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return (T) arrayList;
            } else {
                return (T) instanceCopy(sourceObj, targetClass, options);
            }
        } catch (Exception e) {
            throw new RuntimeException("beanCopy ERROR", e);
        }
    }

    private static Object instanceCopy(Object sourceObj, Class<?> targetClass, CopyOptions options) throws Exception {
        Object newInstance = targetClass.newInstance();
        copyProperties(sourceObj, newInstance, options);
        return newInstance;
    }

    /**
     * 说明:对象属性值复制
     * 
     * @author huangkeqi
     * @param sourceObj 源数据据对象
     * @param targetObj 复制给目据对象
     * @date 2018年12月13日
     */
    public static void copyProperties(Object sourceObj, Object targetObj) {
        copyProperties(sourceObj, targetObj, setIgnoreNullValue);
    }

    /**
     * 说明:对象属性值复制
     * 
     * @author huangkeqi
     * @param sourceObj 源数据据对象
     * @param targetObj 复制给目据对象
     * @param options CopyOptions
     * @date 2018年12月13日
     */
    public static void copyProperties(Object sourceObj, Object targetObj, CopyOptions options) {
        cn.hutool.core.bean.BeanUtil.copyProperties(sourceObj, targetObj, options);
    }

    /**
     * 实体类对象转换成Map
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> convertObjToMap(Object obj) {
        Map<String, Object> reMap = Maps.newHashMap();
        if (obj == null) return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }

    /**
     * 实体类对象转换成Map
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> convertObjToMap(Object obj, Boolean transferUnderscore) {
        Map<String, Object> reMap = Maps.newHashMap();
        if (obj == null) return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);

                    // 转驼峰
                    if (transferUnderscore) {
                        reMap.put(underscoreName(fields[i].getName()), o);
                        continue;
                    }

                    reMap.put(fields[i].getName(), o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return reMap;
    }

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * map转bean
     * @param map
     * @param obj
     * @param camelToUnderline
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void transMap2Bean(Map<String, Object> map, Object obj, Boolean camelToUnderline)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            String key;
            if (camelToUnderline) {
                key = underscoreName(property.getName());
            } else {
                key = property.getName();
            }
            if (map.containsKey(key)) {
                Object value = map.get(key);
                // 得到property对应的setter方法
                Method setter = property.getWriteMethod();
                setter.invoke(obj, value);
            }
        }
    }

    /**
     * 转换为驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelToUnderline(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

}
