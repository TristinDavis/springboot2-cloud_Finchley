package com.aha.tech.utils;

/**
 * @Author: monkey
 * @Date: 2018/7/28
 *
 * redis key 定义类
 */
public class KeyGenerateUtil {

    // 项目的namespace
    public static final String NAMESPACE = "pay";

    public static final String SEPARATOR = ":";

    /**
     * 定义namespace下不同业务的key
     */
    public static class Task {

        // 定时任务 test用例的 key
        public static String TEST = "test";

        /**
         * 具体生成key的方法
         * @return
         */
        public static String testKey() {
            return NAMESPACE + SEPARATOR + TEST;
        }
    }

    /**
     * 某个具体业务的redis key
     */
    public static class OneBusiness {

        public static String PAY = "pay";

        public static String payKey() {
            return NAMESPACE + SEPARATOR + PAY;
        }
    }
}
