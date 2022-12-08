package org.origami.common.core.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * 简单雪花算法工具类，默认的数据中心id为1，机器id为1
 *
 * @author origami
 * @date 2022/1/11 19:46
 */
@Slf4j
public abstract class SnowflakeUtil {
    
    /**
     * 起始时间戳，2022-01-01 19:28:00
     */
    private static final long twepoch = 1641036480000L;
    /**
     * 每个部分所占的比特位数
     */
    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long sequenceBits = 12L;
    /**
     * 每一部分的最大值,31,31,4095
     */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private static final long maxSequence = -1L ^ (-1L << sequenceBits);
    /**
     * 每一部分向左的位移
     */
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;
    
    /**
     * 数据中心ID
     */
    private static long datacenterId = 1;
    
    /**
     * 机器id
     */
    private static long workerId = 1;
    /**
     * 序列号
     */
    private static long sequence = 0L;
    /**
     * 上次的时间戳
     */
    private static long lastTimestamp = -1L;
    
    private SnowflakeUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }
    
    /**
     * 校验机房id和机器id
     */
    @PostConstruct
    public static void init() {
        String msg;
        if (workerId > maxWorkerId || workerId < 0) {
            msg = String.format("worker Id can't be greater than %d or less than 0",
                                maxWorkerId);
            log.error(msg);
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            msg = String.format("datacenter Id can't be greater than %d or less than 0",
                                maxDatacenterId);
            log.error(msg);
        }
    }
    
    @SneakyThrows
    public static synchronized long nextId() {
        long timestamp = timeGen();
        // 机器时间回退，报错
        if (timestamp < lastTimestamp) {
            throw new Exception(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }
        if (timestamp == lastTimestamp) {
            // 如果4095+1 = 4096-> 1000000000000，与maxSequence->111111111111 进行与操作，为0，等下一毫秒后再生成
            // 原版算法会在下一毫秒将sequence置0，并发不高时一直出现偶数，基于id分库分表时，大部分分到偶数库，优化为不重置序列
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0L) {
                timestamp = tilNextMillis();
            }
        }
        lastTimestamp = timestamp;
        
        return (timestamp - twepoch) << timestampShift // 时间戳部分
               | datacenterId << datacenterIdShift // 数据中心部分
               | workerId << workerIdShift // 机器标识部分
               | sequence; // 序列号部分
    }
    
    private static long tilNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
