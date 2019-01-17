package com.aha.tech.base.constants;

/**
 * @Author: luweihong
 * @Date: 2018/7/25
 * application 级别的应用常量
 */
public class ApplicationConstants {

    public static final String BASE_PACKAGE = "com.aha.tech";

    public static final String SCAN_BASE_PACKAGE_ENTITY_PACKAGE = "com.aha.tech.model.entity";

    public static final String SCAN_BASE_REPOSITORY_DAO_READ_PACKAGE = "com.aha.tech.repository.dao.read";

    public static final String SCAN_BASE_REPOSITORY_DAO_READ_WRITE_PACKAGE = "com.aha.tech.repository.dao.readwrite";

    public static final String SCAN_READWRITE_MAPPER_XML_PATH="classpath*:/mapper/readwrite/*.xml";

    public static final String SCAN_READ_MAPPER_XML_PATH="classpath*:/mapper/read/*.xml";

    public static final String SPRING_PROFILE_DEV = "dev";

    public static final String SPRING_PROFILE_TEST = "test";

    public static final String SPRING_PROFILE_TEST2 = "test2";

    public static final String SPRING_PROFILE_PRODUCTION = "prod";


    public static final String[] AVAILABLE_PROFILES = new String[]{SPRING_PROFILE_TEST,
            SPRING_PROFILE_PRODUCTION, SPRING_PROFILE_TEST2, SPRING_PROFILE_DEV};
}
