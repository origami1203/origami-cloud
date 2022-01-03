package org.origami.common.jpa.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * 雪花算法生成器
 *
 * @author origami
 * @date 2022/1/1 19:28
 */
@Slf4j
@Component
public class SnowflakeIdGenerator implements IdentifierGenerator {
    
    /**
     * 起始时间戳，2022-01-01 19:28:00
     */
    private final long twepoch = 1641036480000L;
    /**
     * 每个部分所占的比特位数
     */
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long sequenceBits = 12L;
    /**
     * 每一部分的最大值,31,31,4095
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long maxSequence = -1L ^ (-1L << sequenceBits);
    /**
     * 每一部分向左的位移
     */
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;
    
    /**
     * 数据中心ID
     */
    @Value("${snowflake.data-center-id:1}")
    private long datacenterId;
    
    /**
     * 机器id
     */
    @Value("${snowflake.worker-id:0}")
    private long workerId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上次的时间戳
     */
    private long lastTimestamp = -1L;
    
    /**
     * 校验机房id和机器id
     */
    @PostConstruct
    public void init() {
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
    public synchronized long nextId() {
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
    
    private long tilNextMillis() {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    
    private long timeGen() {
        return System.currentTimeMillis();
    }
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws
                                                                                     HibernateException {
        return nextId();
    }
}

