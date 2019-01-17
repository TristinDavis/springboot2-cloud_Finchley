package com.aha.tech.utils;

import com.aha.tech.commons.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;

/**
 * Created by jeffrey on 10/19/15.
 */
public class IdWorker {
    private final static Logger LOGGER = LoggerFactory.getLogger(IdWorker.class);

    // ==============================Fields===========================================
    /** 开始时间截 */
    private final long twepoch = 1507737600000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    private int shardId = 0;

    //==============================Constructors=====================================

    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        long genId = ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;

        return Long.parseLong(String.valueOf(genId) + String.valueOf(getShardId()));
    }

    /**
     * 末尾用一个分片id,预计10个分片,取余数
     * @return
     */
    private int getShardId() {
        return shardId++ % 10;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 单例获取对象入口
     * @return
     */
    public static IdWorker getInstance() {
        return IdGenHolder.instance;
    }

    /**
     * 静态内部类实现单例模式
     * 通过redis锁 + redis hash结构记录 key:ip,value :工作节点(1-31的数字)
     */
    private static class IdGenHolder {
        private final static String ASSIGN_WORK_ID_KEY = "huijiame:order:assign2workid";
        private final static String ASSIGN_DATACENTER_ID_KEY = "huijiame:order:assign2datacenterid";
        private final static int WORK_ID = assignId(ASSIGN_WORK_ID_KEY);
        private final static int DATA_CENTER_ID = assignId(ASSIGN_DATACENTER_ID_KEY);
        private final static IdWorker instance = new IdWorker(WORK_ID, DATA_CENTER_ID);

        /**
         * 分配工作机器id
         * @return
         */
        public static int assignId(String key) {
            String ip;
            int workId = new Random().nextInt(30) + 1;
            RedisLock redisLock = new RedisLock(key);
            RedisTemplate<String, Serializable> redisTemplate = (RedisTemplate<String, Serializable>) SpringContextUtil.getBean("redisTemplate");
            try {
                ip = IpUtil.getLocalHostAddress();
                redisLock.lock();
                // 插入配置
                Map<Object,Object> assignInfoConfig = redisTemplate.opsForHash().entries(key);
                if(CollectionUtils.isEmpty(assignInfoConfig)){
                    redisTemplate.opsForHash().put(key,ip,String.valueOf(workId));
                    return workId;
                }

                if(assignInfoConfig.containsKey(ip)){
                    return Integer.parseInt(assignInfoConfig.get(ip).toString());
                }

                // 获取可用的workId,不重复的,理论上目前ip是不变化的
                int reAssignWorkId = checkNumberValid(assignInfoConfig,workId);
                redisTemplate.opsForHash().put(key,ip,String.valueOf(reAssignWorkId));
            } catch (Exception e) {
                LOGGER.error("计算ip地址错误");
                LOGGER.error(e.getMessage(), e);
            } finally {
                redisLock.unlock();
            }

            return workId;
        }

        /**
         * 判断workId是否重复,可用则返回
         * @param assignInfoConfig
         * @param workId
         * @return
         */
        private static int checkNumberValid(Map<Object,Object> assignInfoConfig,int workId){
            if(assignInfoConfig.containsValue(String.valueOf(workId))){
                workId = new Random().nextInt(30) + 1;
                checkNumberValid(assignInfoConfig,workId);
            }

            return workId;
        }

    }
}
