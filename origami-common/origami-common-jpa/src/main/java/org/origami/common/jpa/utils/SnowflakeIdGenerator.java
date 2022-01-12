package org.origami.common.jpa.utils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.origami.common.core.utils.SnowflakeUtil;
import org.springframework.stereotype.Component;

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
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws
                                                                                     HibernateException {
        return SnowflakeUtil.nextId();
    }
}

