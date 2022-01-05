package org.origami.common.jpa.utils;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import lombok.experimental.UtilityClass;
import org.origami.common.core.condition.Condition;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Objects;

/**
 * @author origami
 * @date 2022/1/2 19:02
 */
@UtilityClass
public class SpecificationUtil {
    
    @SuppressWarnings("rawtypes")
    public Specification getSpecification(Condition condition) {
        if (Objects.isNull(condition)) {
            return null;
        }
        
        Map<String, Object> params = condition.getConditionMap();
        
        PredicateBuilder builder = Specifications.and();
        
        // 添加逻辑删除条件
        builder.eq("deleted", false);
        
        params.forEach((fieldName, value) -> {
            if (value instanceof String) {
                // 只支持最左前缀like查询
                builder.like(fieldName, value + "%");
                return;
            }
            
            builder.eq(fieldName, value);
        });
        
        return builder.build();
    }
}
