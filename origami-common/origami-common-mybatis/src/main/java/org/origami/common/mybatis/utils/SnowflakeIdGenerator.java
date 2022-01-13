package org.origami.common.mybatis.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.origami.common.core.utils.SnowflakeUtil;
import org.springframework.stereotype.Component;

/**
 * 主键生成策略
 *
 * @author origami
 * @date 2022/1/11 20:02
 */
@Component
public class SnowflakeIdGenerator implements IdentifierGenerator {
    
    @Override
    public Long nextId(Object entity) {
        return SnowflakeUtil.nextId();
    }
}
